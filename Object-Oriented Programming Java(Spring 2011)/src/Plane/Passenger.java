package Plane;
import java.util.Scanner;


public class Passenger 
{
	  public String name; //creates a variable for the passenger names
	  public int numberOfPassengers;// creates a variable for number of passenger
	  public boolean seating;//creates a boolean variable for determining flight class
	  public int placement;//creates a variable for determining seat placement(aisle, center,window)
	  Scanner scanner1 = new Scanner(System.in);
	
	  public String giveName()//returns passenger name
	  {
		 name = scanner1.nextLine();
		 return name;
	  }
	  
	  public boolean inFirstClass()//returns boolean variable for determining passsenger's class
	  {
		 int choice = scanner1.nextInt();
		 while(choice != 1  && choice != 2)
		 {
		 	System.out.println("Invalid Input! \nWhere would you like to sit?\nEnter 1 for First Class or\nEnter 2 for Economy Class");
		 	choice = scanner1.nextInt();
		 }
		 if(choice== 1)
		 {
			seating=true;
			return seating;
		 }
		 if(choice== 2);
		 {
		    seating=false;
		    return seating;
		 }
	  }
	  
	 public int giveSeatingFC()//returns a variable for determining First Class seating(aisle & window)
	 {
		 placement = scanner1.nextInt();
		 while(placement != 1  && placement != 2)
		 {
		 	System.out.println("Invalid Input! \nWhere would you prefer to sit?\nEnter 1 to sit by window \nEnter 2 to sit by aisle");
		 	placement = scanner1.nextInt();
		 }
		 return placement;
	 }
	 
	 public int giveSeatingE()//returns a variable for determining Economy Class seating(aisle & window)
	 {
		 placement = scanner1.nextInt();
		 while(placement != 1  && placement != 2 && placement !=3)
		 {
		 	System.out.println("Invalid Input! \nWhere would you prefer to sit?\nEnter 1 to sit by window \nEnter 2 to sit in the center \nEnter 3 to sit by the aisle ");
		 	placement = scanner1.nextInt();
		 }
		 return placement;
	 }
}