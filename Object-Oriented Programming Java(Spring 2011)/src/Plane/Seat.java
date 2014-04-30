package Plane;
import java.util.Scanner;


public class Seat 
{
	Passenger pass = new Passenger();//creates a passenger object
	public boolean morePassengers;//a boolean for determining if there are more passengers to be added
	Scanner scanner1 = new Scanner(System.in);
	Scanner scanner2 = new Scanner(System.in);
	String name;//creates a variable for passengers' names
	public Boolean firstClass;//creates a variable for determining passengers' class
	int placement;//creates a variable for determining placement(aisle, window,center)

	public String askName()//returns passenger's name
	{
		name=pass.giveName();
		return name;
	}
	
	public Boolean askClass()//returns passenger's name
	{
		firstClass=pass.inFirstClass();
		return firstClass;
	}
	
	public int askPlacementFC()//returns First Class placement
	{
		placement=pass.giveSeatingFC();
		return placement;
	}
	
	public int askPlacementE()//returns Economy Class placement
	{
		placement=pass.giveSeatingE();
		return placement;
	}
	
	public int haveMorePassengers()//returns how many more passengers to be added
	 {
		 int choice;//an integer used to determine if there are addional passengers
		 int numberOfPassengers = 0;//integer value for number of additional passengers 
		 boolean in=firstClass;//a boolean for determining which class a passenger is in
		 if(in==true)//determines how many more passengers are flying if the first passenger is in First Class 
		 {
			 choice = scanner1.nextInt();
			 while(choice != 1  && choice != 2)
			 {
			 	System.out.println("Invalid Input! \nIs there anyone else traveling with you?\nEnter 1 if yes\nEnter 2 if no");
			 	choice = scanner1.nextInt();
			 }
			 if(choice==1)//if there are additional passengers, the program asks how many more
			 {
				morePassengers=true;
				System.out.println("How many more people are you traveling with?\n1 or 2?");
				numberOfPassengers = scanner2.nextInt();
				while(numberOfPassengers != 1  && numberOfPassengers != 2)
				{
					System.out.println("Invalid Input! \nHow many more people are you traveling with?\n1 or 2?");
					numberOfPassengers = scanner2.nextInt();
				}
			 if(choice==2)//if there no additional
			    morePassengers=false;
			 }
		 }
		if(in==false)
		 {
			 choice = scanner1.nextInt();
			 while(choice != 1  && choice != 2)
			 {
			 	System.out.println("Invalid Input! \nIs there anyone else traveling with you?\nEnter 1 if yes\nEnter 2 if no");
			 	choice = scanner1.nextInt();
			 }
			 if(choice== 1)//if there are additional passengers, the program asks how many more
			 {
				morePassengers=true;
				System.out.println("How many more people are you traveling with?\n1, 2, or 3?");
				numberOfPassengers = scanner2.nextInt();
				while(numberOfPassengers != 1  && numberOfPassengers != 2 && numberOfPassengers !=3)
				{
					System.out.println("Invalid Input! \nHow many more people are you traveling with?\n1, 2, or 3?");
					numberOfPassengers = scanner2.nextInt();
				}
			 }
			 if(choice==2)//if there no additional
			    morePassengers=false;
		 }
		return numberOfPassengers;
	 }
}
