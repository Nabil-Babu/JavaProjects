import java.io.*;
import java.util.*;

public class FileReadTest {
	public static void main(String[] args) throws IOException {
		
		String fileName = "students.txt";
		String companyList = "companies.txt";
		String studentNumber, firstName = "", lastName = "", pinNumber = "", status = ""; 
		String line, lineStream = null;
		String[] lineSplit = null;
		ArrayList<String> Insurancelist = new ArrayList<String>();
		boolean foundFlag = false; 
		
		
		FileReader fileReader = new FileReader(fileName);
		FileReader companyFile = new FileReader(companyList);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter your student number: ");
		studentNumber = userInput.next();
		line = bufferedReader.readLine();
	    while(line != null){
		    lineSplit = line.split(",");
	     if(studentNumber.trim().equalsIgnoreCase(lineSplit[0].trim())){
	    	foundFlag = true; 
	    	break;
	     } else {
	    	line = bufferedReader.readLine();
	     }
	    }
	    if(foundFlag){
	    	firstName = lineSplit[3].trim();
	    	lastName = lineSplit[2].trim();
	    	pinNumber = lineSplit[1].trim();
	    	status = lineSplit[4].trim();
	    	String format = String.format("%s %s %s %s", firstName, lastName, pinNumber, status);
	    	System.out.println(format);
	    } else {
	    	System.out.println("Sorry your student number was not found"); 
	    }
	    bufferedReader.close();  
	    
	    BufferedReader in = new BufferedReader(companyFile);
	    
	    
	    while((lineStream = in.readLine()) != null){
	    	Insurancelist.add(lineStream.trim());
	    }
	    
	    System.out.println(Insurancelist.get(0));
	    
	    in.close();
	           


	}
}