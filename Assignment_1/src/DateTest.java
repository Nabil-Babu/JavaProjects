import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class DateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//input stuff
		System.out.println("Enter your month number (of the expiry date): ");
		Scanner input = new Scanner(System.in);
		
		//get input and make it an int
		String month = input.nextLine();
		int intMonth = Integer.parseInt(month);
		//System.out.println(intMonth);
		
		//get input and make it an int
		System.out.println("Enter your year (of the expiry date): ");
		String year = input.nextLine();
		int intYear = Integer.parseInt(year);
		//System.out.println(intYear);
			
		//getInstance gets the current time
		Calendar myCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();		
		
		//sets myCalender to the entered values
		myCalendar.set(intYear, intMonth - 1, 1, 0, 0);  
		
		//sets the hours,mins,seconds,millis to 0 to make comparison easy
		myCalendar.set(Calendar.HOUR_OF_DAY, 0);
		myCalendar.set(Calendar.MINUTE, 0);
		myCalendar.set(Calendar.SECOND, 0);
		myCalendar.set(Calendar.MILLISECOND, 0);
		int calMonth = myCalendar.get(Calendar.MONTH )+ 1;
		int calYear = myCalendar.get(Calendar.YEAR);
		
		System.out.println("Expiry date: Month: " + calMonth + " " + "Year: " + calYear );  		
		
		//sets the hours,mins,seconds,millis to 0 to make comparison easy
		currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.MILLISECOND, 0);
		
		int curMonth = currentCalendar.get(Calendar.MONTH )+ 1;
		int curYear = currentCalendar.get(Calendar.YEAR);
		int curDay = currentCalendar.get(Calendar.DAY_OF_MONTH);		
		
		System.out.println("Current Date: Month: " + curMonth + " " + "Year: " +curYear +" " +  "Day: " +curDay );  
		
		//gets the millis of the two dates
		long different = myCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();
		
		//equation to get days from millis 
		final int DAY_EQUATION = 24 * 60 * 60 * 1000;		
		
		//System.out.println("\ndifference: " + different );
		
		long elapsedDays = different / DAY_EQUATION;
		System.out.println("\ndays passed: " + elapsedDays );  
		
		
		final double RATE = 3.5;
		
		float totalfee = (float) (elapsedDays * RATE);
				
		System.out.printf("Billing for the permit is $3.50 per day.\nYour fee is: $%.2f",totalfee);  

		
	}

}
