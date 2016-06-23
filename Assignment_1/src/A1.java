import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Assignment 1:  York University Parking Permit Kiosk
 * 
 * @author Nabil Babu, Stefano Onorati, Victor Zohni
 *
 */
public class A1 
{
    public static void main(String[] args) 
    {
        A1Frame frame = new A1Frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Parking Permit Kiosk");
        frame.getContentPane().setBackground(new Color(0xf41720));
        frame.setUndecorated(false);
        frame.pack();
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(dim);
        frame.setMinimumSize(dim);
        
        frame.setVisible(true);
    }
}

class A1Frame extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	
    private JPanel parent = new JPanel();
    private JPanel[] panel;
    private JPanel labelPanel;
    private JPanel textPanel;
    private JButton[][] button;
    private JLabel textField;
    private JLabel info;
    private JComboBox<String> insurance;
    private JComboBox<String> month;
    private JComboBox<String> year;
    private String input = "";
    private String[] lineSplit;
    private ArrayList<String> companies;
    private ImageIcon york;
    private static final String[][] key = {
        {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "@", "-", "\u2190"}, 
        	{"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "_"}, 
            	{"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            		{"Z", "X", "C", "V", "B", "N", "M", ",", "."},
            			{"GO BACK", "SPACE", "ENTER"}
   };

	private ArrayList<String> a1 = new ArrayList<String>();
	private int count = 0;
	
	//used for changing the information label in our kiosk system whenever user goes to next step
	private static final String STATEMENT_SID = "Please enter your 9-digit Student ID.";
	private static final String STATEMENT_PIN = "Please enter your 4-digit PIN.";
	private static final String STATEMENT_EM = "Please enter your Email (optional)";
	private static final String STATEMENT_VH = "Please select your Insurance Company.";
	private static final String STATEMENT_PN = "Please enter your Policy Number.";
	private static final String STATEMENT_LP = "Please enter your License Plate.";
	private static final String STATEMENT_CM = "Please enter your car's Brand.";
	private static final String STATEMENT_CD = "Please enter your car's Model.";
	private static final String STATEMENT_CY = "Please enter your car's Manufactured Year.";
	private static final String STATEMENT_ED = "Please select your policy's Expiry Date.";
	
	//used for step-by-step user input in our kiosk system
	private static final int SID_COUNT = 1;
	private static final int PIN_COUNT = 2;
	private static final int EMAIL_COUNT = 3;
	private static final int INSURANCE_COUNT = 4;
	private static final int POLICY_COUNT = 5;
	private static final int LICENSE_COUNT = 6;
	private static final int BRAND_COUNT = 7;
	private static final int MODEL_COUNT = 8;
	private static final int YEAR_COUNT = 9;
	private static final int EXPIRY_COUNT = 10;
	

    public A1Frame() 
    {
    	parent.setLayout(new BoxLayout(parent, BoxLayout.Y_AXIS));
        panel = new JPanel[5];
        
        JPanel headerPanel = new JPanel();
        this.addWindowListener(new WindowCloser());
        
        JLabel yorkUnivParking = new JLabel("YORK UNIVERSITY PARKING");
        yorkUnivParking.setFont(new Font("ARIAL", Font.PLAIN, 48));
        
        york = new ImageIcon("york.png");
        Image img = york.getImage();
        Image newImgYork = img.getScaledInstance(Toolkit.getDefaultToolkit().getScreenSize().width/15, Toolkit.getDefaultToolkit().getScreenSize().height/10, java.awt.Image.SCALE_SMOOTH);
        ImageIcon yorkIcon2 = new ImageIcon(newImgYork);
        JLabel yorkUnivImage = new JLabel(yorkIcon2);  
        yorkUnivImage.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
        headerPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        headerPanel.add(yorkUnivParking);
        headerPanel.add(yorkUnivImage);
        headerPanel.setBackground(new Color(0xf41720));
        parent.add(headerPanel);
        
        labelPanel = new JPanel();
        info = new JLabel("Welcome! Please enter your 9-digit Student ID.");        
        info.setFont(new Font("ARIAL", Font.PLAIN, 36));
        
        labelPanel.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));
        labelPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        labelPanel.setBackground(Color.WHITE);
        labelPanel.add(info);
        parent.add(labelPanel);
        
        textPanel = new JPanel();
        textField = new JLabel("");
        textField.setFont(new Font("ARIAL", Font.PLAIN, 36));
        textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(20,20,20,20)));
        textField.setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width/3)*2, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        
        textPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        textPanel.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, (Toolkit.getDefaultToolkit().getScreenSize().height/10)*2));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(textField);
        parent.add(textPanel);
        
        //nested for loop to populate panel and button arrays for kiosk's keyboard
        for (int row = 0; row < key.length; row++) 
        {
            panel[row] = new JPanel();
            panel[row].setLayout(new GridLayout(1, 13));
            panel[row].setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height/10));
            
            button = new JButton[key.length][key[row].length];
            for (int column = 0; column < key[row].length; column++) 
            {
                button[row][column] = new JButton(key[row][column]);
                button[row][column].setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().height/10,Toolkit.getDefaultToolkit().getScreenSize().height/10));
                button[row][column].putClientProperty("key", key[row][column]);
                button[row][column].addActionListener(this);
                button[row][column].setForeground(new Color(244,23,32));
                button[row][column].setFont(new Font("ARIAL", Font.PLAIN, 24));
                panel[row].add(button[row][column]);
            }
            panel[row].setBackground(Color.WHITE);
            parent.add(panel[row]);
        }
        
        this.setContentPane(parent);
    }


	public void actionPerformed(ActionEvent ae) 
	{
		JButton btn = (JButton) ae.getSource();

		String keyPressed = (String) btn.getClientProperty("key");

		if (keyPressed.equals("ENTER")) 
		{
			count++;

			issuePermit(count);
		} 
		else if (keyPressed.equals("SPACE")) 
		{
			String newInput = input + " ";
			input = newInput;
			textField.setText(input);
		} 
		else if (keyPressed.equals("\u2190")) 
		{
			if (input.length() > 0) {
				String newInput = input.substring(0, input.length() - 1);
				input = newInput;
				textField.setText(input);
			}
		} 
		else if (keyPressed.equals("GO BACK"))
		{
			goBack(count);
		}
		else 
		{
			String newInput = input + keyPressed;
			input = newInput;
			textField.setText(input);
		}
	}
	
	/**
	 * Takes the step-by-step approach of user input and asks for a single piece
	 * of information to be filled in.  Within each if statement is a validate
	 * method for each unique piece of user input in our kiosk system.
	 */
	private void issuePermit(int c)
	{
		if (count == SID_COUNT) 
		{
			try {
				validateNumber(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else if (count == PIN_COUNT) 
		{
			validatePIN(input);
		} 
		else if (count == EMAIL_COUNT) 
		{
			try {
				validateEmail(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		else if (count == INSURANCE_COUNT) 
		{
			try {
				input = insurance.getSelectedItem().toString();
				validateInsurance(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (count == POLICY_COUNT)
		{
			validatePolicyNo(input);
		}
		else if (count == LICENSE_COUNT)
		{
			validateLicense(input);
		}
		else if (count == BRAND_COUNT)
		{
			validateCarBrand(input);
		}
		else if (count == MODEL_COUNT)
		{
			validateCarModel(input);
		}
		else if (count == YEAR_COUNT)
		{
			validateCarYear(input);
		}
		else if (count == EXPIRY_COUNT) 
		{
			input = month.getSelectedItem().toString() + " "
					+ year.getSelectedItem().toString();
			validateExpiryDate(input);
		}
	}
	
	/**
	 * This method uses the text file companies.txt and populates each non-empty line
	 * into a JComboBox containing the choices the user can make when selecting their
	 * insurance company.  This method is far more efficient as opposed to having users
	 * manually input their insurance company by the keyboard.
	 */
	private void setUpInsuranceBoxes() throws IOException
	{
		String fileName = "companies.txt";
		String lineStream;
		companies = new ArrayList<String>();
		companies.add("SELECT INSURANCE COMPANY");
		
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		lineStream = bufferedReader.readLine();
		
	    while (lineStream != null)
	    {
	    	if (!lineStream.isEmpty())
	    	{
	    		companies.add(lineStream);
	    		lineStream = bufferedReader.readLine();
	    	}
	    	else
	    	{
	    		break;
	    	}
		}
	    
	    bufferedReader.close();
	    
		insurance = new JComboBox<String>();
		for (int i = 0; i < companies.size(); i++)
		{
			insurance.addItem(companies.get(i));
		}
	    insurance.setVisible(true);
	    insurance.setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width/3)*2, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        insurance.setBackground(Color.WHITE);
        insurance.setForeground(Color.RED);
        insurance.setFont(new Font("ARIAL", Font.PLAIN, 36));
        insurance.setMaximumRowCount(companies.size());
        textPanel.add(insurance);

	}
	
	//Takes the JComboBox containing companies and removing it for space needed with label.
	private void removeInsuranceBoxes()
	{
		insurance.setVisible(false);		
		parent.remove(insurance);
		parent.revalidate();
		parent.repaint();
	}
    
	/**
	 * Sets up combo boxes of month and year, pertaining to the user's expiry date on their
	 * insurance policy.  Giving them the choice to choose as opposed to manually inputting
	 * it via keyboard allows for more efficient and quicker user input.
	 */
    private void setUpComboBoxes() 
    {
    	String[] months = {"Select Month", "January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"};
        month = new JComboBox<String>(months);
        month.setVisible(true);
        month.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/10));
        month.setBackground(Color.WHITE);
        month.setForeground(Color.RED);
        month.setFont(new Font("ARIAL", Font.PLAIN, 36));
        month.setMaximumRowCount(months.length);
        textPanel.add(month);
        
        String[] years = {"Select Year", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
        year = new JComboBox<String>(years);
        month.setVisible(true);
        year.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/3, Toolkit.getDefaultToolkit().getScreenSize().height/10));
		year.setBackground(Color.WHITE);
		year.setForeground(Color.RED);
		year.setFont(new Font("ARIAL", Font.PLAIN, 36));
		year.setMaximumRowCount(years.length);
        textPanel.add(year);	
    }
    
    //Removes JComboBoxes for month and year in the Expiry Date stage.
    private void removeComboBoxes()
    {
    	month.setVisible(false);
		year.setVisible(false);
		
		parent.remove(month);
		parent.revalidate();
		parent.repaint();
		
		parent.remove(year);
		parent.revalidate();
		parent.repaint();
    }

    /**
     * Compares the String s to a database of student IDs in students.txt.
     * If the input is validated to the database, then we proceed to the next level.
     * Otherwise, if the input isn't found in the database, we ask for new user input.
     */
    private void validateNumber(String s) throws IOException
    {
    	if (s.matches("^[0-9]{9}$"))
    	{
    		String fileName = "students.txt";
    		boolean foundFlag = false; 
    		
    		
    		FileReader fileReader = new FileReader(fileName);
    		BufferedReader bufferedReader = new BufferedReader(fileReader);
    		String line = bufferedReader.readLine();
    		
    		//splitting each field in the text field such that we can easily get the first
    		//item (Student ID) to compare to user input
    	    while(line != null){
    		    lineSplit = line.split(",");
    	     if(s.equalsIgnoreCase(lineSplit[0].trim())){
    	    	foundFlag = true; 
    	    	bufferedReader.close();
    	    	break;
    	     } else {
    	    	line = bufferedReader.readLine();
    	     }
    	    }
    	    
    	    if (foundFlag)
    	    {
    	    	correctInformation(s, STATEMENT_PIN);
    	    }
    	    else
    	    {
    	    	invalidInformation(STATEMENT_SID);
    	    }
    	}
    	else
    	{
    		wrongInformation(STATEMENT_SID);
    	}
    }

    /**
     * Validates user input for PIN relating to previous student ID.
     * Also checks if the student has significant fines once PIN is
     * validated to database of student's records.
     */
    private void validatePIN(String s)
    {
    	if (s.matches("^[0-9]{4}$"))
    	{
    		if (s.equals(lineSplit[1].trim()))
    		{
    			if (lineSplit[lineSplit.length-1].contains("arrears"))
    			{
    				defaultInformation(STATEMENT_SID);
    				significantFinesWarning();
    			}
    			else
    			{
    				correctInformation(s, STATEMENT_EM);
    			}
       		}
    		else
    		{
    			invalidInformation(STATEMENT_PIN);
    		}
    	}
    	else
    	{
    		wrongInformation(STATEMENT_PIN);
    	}
    }
    
    //Checks if user input is blank or matches E-mail regex, since E-mail is optional input.
    private void validateEmail(String s) throws IOException
    {
    	if (s.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") || s.matches(""))
    	{
    		correctInformation(s, STATEMENT_VH);
    		textField.setVisible(false);
    		setUpInsuranceBoxes();
    	}
    	else
    	{
    		wrongInformation(STATEMENT_EM);
    	}	
    }
    
    private void validateInsurance(String s) throws IOException
    {   	
    	//since the only choice we cannot accept is the default value in JComboBox,
    	//we check if the input is equal to it
    	if (!s.equals("SELECT INSURANCE COMPANY"))
    	{
    	    correctInformation(s, STATEMENT_PN);
    	    removeInsuranceBoxes();
    	    textField.setVisible(true);
    	}
	    else
	    {
	    	wrongInformation(STATEMENT_VH);
	    }
    }
    
    /**
     * Following methods only check if input isn't blank, since we assume each
     * piece of information is valid and correct and doesn't need to be validated
     * against an existing database in our system.
     * 
     * Only License Plate requires any pattern matching since it contains
     * only letters and numbers.
     */
    
    private void validatePolicyNo(String s)
    {
    	if (!s.trim().isEmpty())
    	{
    		correctInformation(s, STATEMENT_LP);
    	}
    	else
    	{
    		wrongInformation(STATEMENT_PN);
    	}
    }
    
    private void validateLicense(String s)
    {
    	if (s.matches("^[A-Z0-9]{2,8}$"))
    	{
    		correctInformation(s, STATEMENT_CM);
    	}
    	else
    	{
    		wrongInformation(STATEMENT_LP);
    	}
    }
    
    private void validateCarBrand(String s)
    {
    	if (!s.trim().isEmpty())
    	{
    		correctInformation(s, STATEMENT_CD);
    	}
    	else
    	{
    		wrongInformation(STATEMENT_CM);
    	}
    }
    
    private void validateCarModel(String s)
    {
    	if (!s.trim().isEmpty())
    	{
    		correctInformation(s, STATEMENT_CY);
    	}
    	else
    	{
    		wrongInformation(STATEMENT_CM);
    	}
    }
    
    /**
     * Validates car year and validates it to regex.  It is also compared
     * to the current year and next to satisfy the condition that users can
     * have new car models from the following year or before, not after.
     */
    private void validateCarYear(String s)
    {
    	Calendar calendar = Calendar.getInstance();
    	int yearNow = calendar.get(Calendar.YEAR) + 1;
    	
    	if (s.matches("^19[0-9]{2}$") || s.matches("^20[0-9]{2}$"))
    	{
    		int inputYear = Integer.parseInt(s);
    		if (inputYear <= yearNow)
    		{
    			correctInformation(s, STATEMENT_ED);
    			textField.setVisible(false);
    			setUpComboBoxes();
    		}
    		else
    		{
    			wrongInformation(STATEMENT_CY);
    		}
    	}
    	else
    	{
    		wrongInformation(STATEMENT_CY);
    	}
    }
    /**
     * Checks if the user inputs valid month and year for expiry date.  Will not work
     * with default values in either JComboBox.  Once it is validated, we reset the kiosk
     * system for a new user and a pop up message containing the user's parking permit is issued.
     */
    private void validateExpiryDate(String s)
    {    	
    	if (!s.matches(".*(^Select|Year$).*"))
    	{
    		if (getParkingFee(month.getSelectedIndex(), Integer.parseInt((String) year.getSelectedItem())) < 0)
    		{
    			wrongInformation(STATEMENT_ED);
    			month.setSelectedIndex(0);
    			year.setSelectedIndex(0);
    		}
    		else
    		{
    			count = 0;
    			textField.setVisible(true);
    			correctInformation(s, STATEMENT_SID);
    			debugArray();
    			removeComboBoxes();
    			popupMessageSetup();
    			a1.clear();    			
    		}
    	}
    	else
    	{
    		wrongInformation(STATEMENT_ED);
    	}
    }
    
    /**
     * This is handed out in the case a user contains 'arrears' in the database, which
     * means they own significant fines.  Will only pop up after PIN is inputted correctly.
     */
    private void significantFinesWarning()
    {
    	UIManager.put("OptionPane.background", Color.WHITE);
    	UIManager.put("OptionPane.messageForeground", Color.RED);
    	UIManager.put("OptionPane.messageFont", new Font("ARIAL", Font.PLAIN, 36));
    	UIManager.put("Panel.background", Color.WHITE);
    	String message = "Unfortunately, due to significant fines on your account,\nwe cannot process your Parking Permit.\n"
    			+ "Please contact Parking Services for\nmore information on this matter.\n\nThank you.";
    	JOptionPane.showMessageDialog(parent, message, "SIGNIFICANT FINES", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * Parking Permit message that pops up once user successfully inputs all necessary information
     * into kiosk system.  Message will contain Student ID, Car Information, and Policy Information
     * among other data, but hides certain information such as PIN and E-mail.
     */
    private void popupMessageSetup()
    {
    	UIManager.put("OptionPane.background", Color.WHITE);
    	UIManager.put("OptionPane.messageForeground", Color.RED);
    	UIManager.put("OptionPane.messageFont", new Font("ARIAL", Font.PLAIN, 36));
    	UIManager.put("Panel.background", Color.WHITE);
    	String format = String.format("THANK YOU! HERE IS YOUR PARKING PERMIT:\n\nStudent Name: %s, %s\nStudent ID: %9s\n"
    			+ "Insurance: %s\nPolicy No.: %s\nCar: %s %s %s\nLicense Plate: %s\nInsurance Policy's Expiry Date: %s\n\n"
    			+ "Your total parking permit fee will be: $%.2f", 
    			lineSplit[2].trim(), lineSplit[3].trim(), a1.get(0), a1.get(3), a1.get(4), a1.get(8), 
    				a1.get(6).substring(0, 1) + a1.get(6).substring(1).toLowerCase(),
    					a1.get(7).substring(0, 1) + a1.get(7).substring(1).toLowerCase(), a1.get(5), a1.get(9),
    					getParkingFee(month.getSelectedIndex(), Integer.parseInt((String) year.getSelectedItem())));
    	JOptionPane.showMessageDialog(parent, format.toString(), "YOUR PARKING PERMIT", JOptionPane.PLAIN_MESSAGE);
    }
    
    /**
     * This method calculates the current month and year and compares it to the month and year
     * of the expiry date from user input in kiosk system.  Once we figure out how many days
     * differ from the current date to expiry date, we calculate the parking fee using the standard
     * fee of $3.50/day until the expiry date has passed.  Also included on parking permit message.
     */
    private float getParkingFee(int m, int y)
    {

		//getInstance gets the current time
		Calendar myCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();		
		
		//sets myCalender to the entered values
		myCalendar.set(y, m - 1, 1, 0, 0);  
		
		//sets the hours,mins,seconds,millis to 0 to make comparison easy
		myCalendar.set(Calendar.HOUR_OF_DAY, 0);
		myCalendar.set(Calendar.MINUTE, 0);
		myCalendar.set(Calendar.SECOND, 0);
		myCalendar.set(Calendar.MILLISECOND, 0);
				
		//sets the hours,mins,seconds,millis to 0 to make comparison easy
		currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.MILLISECOND, 0);		
				
		//gets the millis of the two dates
		long different = myCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
		
		//equation to get days from millis 
		final int DAY_EQUATION = 24 * 60 * 60 * 1000;		
		
		//System.out.println("\ndifference: " + different );
		
		long elapsedDays = different / DAY_EQUATION;		
		
		final double RATE = 3.5;
		
		float totalfee = (float) (elapsedDays * RATE);
		
		return totalfee;
    }
    
    /**
     * Following methods are used for user input.  Usually, we change the count,
     * add user data into an array for storage, and changing labels to indicate the
     * user has done something in our kiosk system.
     */
    
    private void correctInformation(String s, String statement)
    {
    	a1.add(s);
		info.setText("Thank you. " + statement);
		input = "";
		textField.setText(input);
    }
    
    private void wrongInformation(String statement)
    {
		count--;
		info.setText("Input incorrect. " + statement);
		input = "";
		textField.setText(input);
    }
    
    private void invalidInformation(String statement)
    {
    	count--;
    	info.setText("Input not found. " + statement);
    	input = "";
		textField.setText(input);
    }
    
    private void defaultInformation(String statement)
    {
    	count = 0;
    	a1.removeAll(a1);
    	input = "";
    	textField.setText(input);
    	info.setText("Welcome! " + statement);
    }
    
    /**
     * This method is used in case the user makes an error with any data
     * they had inputted previously.  It allows them to fix these problems
     * without restarting the entire process for a new permit.
     */
    private void goBack(int i){
        if (i == EXPIRY_COUNT){
            textField.setVisible(false);
            setLabels(STATEMENT_ED);
            setUpComboBoxes();
            count--;
            deleteLastItem();  
        } 
        else if (i == YEAR_COUNT) {
            removeComboBoxes();
            textField.setVisible(true);
            setLabels(STATEMENT_CY);
            count--;
            deleteLastItem();   
        }
        else if ( i == MODEL_COUNT) {
            setLabels(STATEMENT_CD);
            count--;
            deleteLastItem();  
        }
        else if ( i == BRAND_COUNT) {
            setLabels(STATEMENT_CM);
            count--;
            deleteLastItem();  
        }
        else if ( i == LICENSE_COUNT){
            setLabels(STATEMENT_LP);
            count--;
            deleteLastItem(); 
        }
        else if (i == POLICY_COUNT){
            setLabels(STATEMENT_PN);
            count--;
            deleteLastItem(); 
        }
        else if (i == INSURANCE_COUNT){
            setLabels(STATEMENT_VH);
            textField.setVisible(false);
            try {
                setUpInsuranceBoxes();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            count--;
            deleteLastItem(); 
        }
        else if (i == EMAIL_COUNT){
            removeInsuranceBoxes();
            textField.setVisible(true);
            setLabels(STATEMENT_EM);
            count--;
            deleteLastItem();  
        }
        else if (i == SID_COUNT){
            info.setText("Welcome! " + STATEMENT_SID);
            input = "";
            textField.setText(input);
            count = 0; 
            a1.removeAll(a1); 
        }
        else {
            this.getToolkit().beep(); 
        }
    }

    private void setLabels(String statement)
    {   
        info.setText("Welcome back. " + statement);
        input = "";
        textField.setText(input);
    }

    private void debugArray()
    {   
        for(int i = 0; i <= a1.size()-1; i++){
            System.out.println(a1.get(i)); 
        }
    }

    private void deleteLastItem()
    {
        int x = a1.size() - 1;
        a1.remove(x); 
    }
    
    private class WindowCloser extends WindowAdapter 
    {
    	
    	@Override
    	public void windowClosing(WindowEvent we) 
    	{
    		System.exit(0);
    	}
    }
}