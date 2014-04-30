package hw4b;
import java.util.Scanner;


public class AppointmentTest //used to test objects 
{
	static boolean moreAp=true;
	static Scanner scanner1 = new Scanner(System.in);	
	public static void main(String[] args)
	{
	   Appointment A1 = new Appointment();
	   A1.inputAp();
	   while (moreAp=true)
		   {
		   System.out.println("\nWhat action would you like to perform next?");
		   System.out.println("Enter 1 to add another appointment. Enter 2 to see the appointments for a specific day. Enter 3 to see your final scedule.");
		   int number = scanner1.nextInt(); //User enters a number
		   while (number < 1 && number > 3) //If the user enters an invalid number, the program will ask again.
		   {
				System.out.println("INVALID INPUT");
				System.out.println("Enter 1 to add another appointment. Enter 2 to see the appointments for a specific day. Enter 3 to see your final schedule.");
				number = scanner1.nextInt();
		   }
		   //the number inputs allows the user to execute multiple commands
		   if (number==1)
		    A1.addAp();
		   	A1.sortAp();
		   	A1.print();
		   if (number==2)
			A1.today();
		   if (number==3)
			A1.summary();
			moreAp=false;
		   }
	}
}
