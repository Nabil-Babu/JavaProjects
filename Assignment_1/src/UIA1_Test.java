
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;


public class UIA1_Test {



	public static void main(String[] args) 
	{
		// use look and feel for my system (Win32)
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) 
		{
		}

		UIA1_TestFrame frame = new UIA1_TestFrame();
		frame.setTitle("UIA1_Test");
		frame.pack();
		
		
//		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//		frame.setSize(dim);
		
		frame.setVisible(true);
		
		
		
	}
}

class UIA1_TestFrame extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	private JTextArea inputArea;
	private JTextArea result;
	
	private String text1 = "Enter your 9 digit student number";
	private String text2 = "Enter your 4 digit PIN ";
	private String text3 = "Enter your email address (must include at least one word character, an @ sign and .com)";
	private String text4 = "Vehicle and Insurance Information";
	private String text5 = "Enter your Expiry Date (Month/Year - Must be after 10/2015)";
	
	private ArrayList<String> a1 = new ArrayList<String>();
	private ArrayList<String> textNames = new ArrayList<String>();
	private int count = 0;

	public UIA1_TestFrame() 
	{
		// ----------------------------------
		// construct and configure components
		// ----------------------------------
		textNames.add(text1);
		textNames.add(text2);
		textNames.add(text3);
		textNames.add(text4);
		textNames.add(text5);
		
				
		Color tmp = this.getBackground();

		
		
		inputArea = new JTextArea(1, 40);
		inputArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		inputArea.setBackground(tmp);
		
				
		inputArea.setBorder(new TitledBorder(new EtchedBorder(),
				text1));
		

//		result = new JTextArea("(result appears here)", 1, 30);
//		result.setEditable(false);
//		result.setAlignmentX(Component.LEFT_ALIGNMENT);
//		result.setBackground(tmp);
//		result.setForeground(new Color(204, 0, 0));
//		result.setBorder(new TitledBorder(new EtchedBorder(),
//				"Your input is"));

		JButton findButton = new JButton("Submit");

		// -------------
		// add listeners
		// -------------

		findButton.addActionListener(this);
		this.addWindowListener(new WindowCloser());

		// ------------------
		// arrange components
		// ------------------

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(inputArea);
		p1.add(Box.createRigidArea(new Dimension(0, 10)));
		//p1.add(result)
		p1.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

		JPanel p2 = new JPanel(); // flow layout
		p2.add(findButton);

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(p1, "Center");
		contentPane.add(p2, "South");
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.setContentPane(contentPane);
		
		

	}

	// -------------------------------
	// implement ActionListener method
	// -------------------------------

	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		//double largest = findLargest(inputArea.getText());
		String inputText = inputArea.getText();
		
		//result.setText("" + text);
	//	validateMain(inputText);
		
	a1.add(inputText);
	count++;
	System.out.println("Input [" + count + "] " + "is: " + a1.get(count-1));
	
	if (count == 1){
		validateNumber(inputText);
	}
	else if (count == 2){
		validatePIN(inputText);
	}
	else if (count == 3){
		validateEmail(inputText);
	}
	else if (count == 4){
		System.out.println("Vehicle info: " + inputText);
		inputArea.setText("");		
	}
	else if (count == 5){
		validateDate(inputText);
	}
	else{
		System.out.println("Done count");
	}
	
	
	
/*	if (validateNumber(a1.get(count))== true){
		count++;
	}*/	
		
	try {
	inputArea.setBorder(new TitledBorder(new EtchedBorder(),
			textNames.get(count)));
	}
	catch (IndexOutOfBoundsException e){
		System.out.println("blah");
		System.exit(0);
	}
	}

	// -------------
	// other methods
	// -------------
	
//		public void validateMain(String s){
//		
//		if (s == text1){
//			validateNumber(s);
//		}
//		else if (s == text2){
//			validatePIN(s);
//		}
//		else if (s == text3){
//			validateEmail(s);
//		}
//		else if (s == text4){
//			System.out.println("Vehicle Information. Enter whatever.");
//		}
//		else if (s == text5){
//			validateDate(s);
//		}
//	}
	public boolean validateNumber(String s){
		if (s.matches("[0-9]{9}")){
			System.out.println("Input correct!");
			inputArea.setText("");		
			return true;
		}
		else{
		//	System.out.println("Input incorrect -_-");
			infoBox("Input incorrect. Please enter only 9 Digits ","Input incorrect");
			a1.remove(0);
			count--;
			inputArea.setText("");
			return false;
		}
	}
	
	
	public void validatePIN(String s){
		
		if (s.matches("[0-9]{4}")){
			System.out.println("Input correct!");
			inputArea.setText("");
		}
		else{
		//	System.out.println("Input incorrect -_-");
			infoBox("Input incorrect. Please enter only 4 Digits ","Input incorrect");
			a1.remove(0);
			count--;
			inputArea.setText("");
		}
	}
	public void validateEmail(String s){
		
		if (s.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
			System.out.println("Input correct!");
			inputArea.setText("");
		}
		else{
		//	System.out.println("Input incorrect -_-");
			infoBox("Input incorrect. Must include at least one letter/number, an @ sign and .com ","Input incorrect");
			a1.remove(0);
			count--;
			inputArea.setText("");
		}
	}
public void validateDate(String s){
		
		if (s.matches("0?1[0-9]/2[0-9]{3}")){
			System.out.println("Input correct!");
			inputArea.setText("");
		}
		else{
		//	System.out.println("Input incorrect -_-");
			infoBox("Input incorrect. Please enter the month and year in digits ","Input incorrect");
			a1.remove(0);
			count--;
			inputArea.setText("");
		}
	}
	
	
	 public static void infoBox(String infoMessage, String titleBar)
	    {
	        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	    }
	 
	 
	public static double findLargest(String s) 
	{
		String[] values = s.split("\\s+"); // REGEX: space delimiter (one or more)
		
		double largest = Double.MIN_VALUE;
		for (int i = 0; i < values.length; ++i)
		{
			double value = Double.parseDouble(values[i]);
			if (value > largest)
				largest = value;
		}
		return largest;
	}

	// -----------
	// inner class
	// -----------

	// Note: WindowAdapter implements windowClosing

	private class WindowCloser extends WindowAdapter 
	{
		@Override
		public void windowClosing(WindowEvent event) 
		{
			System.exit(0);
		}
	}
}
