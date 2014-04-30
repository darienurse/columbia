package Plane;
import java.util.ArrayList;
public class Airplane 
{
	int numberOfFirstSeats=20;// integer value for the number of First Class Seats
	int numberOfEconomySeats=180;// integer value for the number of Economy Class Seats
	ArrayList<Seat> fcSeats = new ArrayList<Seat>();// array list for  First Class Seats 
	ArrayList<Seat> eSeats = new ArrayList<Seat>();// array list for  Economy Class Seats
	ArrayList<String> fcNames = new ArrayList<String>();// array list for  First Class Names
	ArrayList<String> eNames = new ArrayList<String>();// array list for  Economy Class Names
	String name;// string variable used to hold passenger names
	boolean seating;// boolean used to determine placement 
	boolean more=true;// boolean used to 
	
	public int register()// creates a seat object and sorts it into the proper array
	{
		for (int y = 0; y<numberOfFirstSeats; y++)// fills the First class arrays with empty seats and names
		{
			fcSeats.add(null);
			fcNames.add("EMPTY");
		}
		for (int y = 0; y<numberOfEconomySeats; y++)// fills the Economy class arrays with empty seats and names
		{
			eSeats.add(null);
			eNames.add("EMPTY");
		}
		Seat s = new Seat();// creates seat object
		int i=0;
		int contin;
		System.out.println("What is your name?");
		name=s.askName();// asks for passenger's name
		System.out.println("What class will you be in?\nEnter 1 for First Class \nEnter 2 for Economy Class");
		seating=s.askClass();//ask's for passenger's class
		if(seating==true)// if the passenger is in first class
		{
			System.out.println("Where would you prefer to sit?\nEnter 1 to sit by the window \nEnter 2 to sit by the aisle");
			int choice = s.askPlacementFC();//ask's for passenger's placement
			if (choice==1)// if the passenger wants a window seat, the program tries to find an empty seat
			{
				int q=0;
				int vary=0;
				while(i<numberOfFirstSeats)
				{
					if (fcSeats.get(i)!= null && q==0)
					{
						i=i+vary;
						if (i%2!=0)
							vary=1;
						if (i%2==0)
							vary=3;
					}
					if (fcSeats.get(i)== null)
					{
						fcSeats.add(i,s);
						fcNames.add(i,name+" Placement-First Class window seat");
						q=1;
						i=numberOfFirstSeats;
					}
				}
				if(q==0)//if no match is found
				{
					System.out.println("Sorry, but there are no more First Class window seats avaliable. Please try again.");	
				}
			}
			if (choice==2)// if the passenger wants a aisle seat, the program tries to find an empty seat
			{
				i++;
				int q=0;
				int vary=0;
				while(i<numberOfFirstSeats)
				{
					if (fcSeats.get(i)!= null && q==0)
					{
						i=i+vary;
						if (i%2!=0)
							vary=1;
						if (i%2==0)
							vary=3;
					}
					if (fcSeats.get(i)== null)
					{
						fcSeats.add(i,s);
						fcNames.add(i,name+" Placement-First Class aisle seat");
						q=1;
						i=numberOfFirstSeats;
					}
					
				}
				if(q==0)// if not match is found
				{
					System.out.println("Sorry, but there are no more First Class aisle seats avaliable. Please try again.");	
				}
			}
		}
		
		if(seating==false)
		{
			System.out.println("Where would you prefer to sit?\nEnter 1 to sit by the window \nEnter 2 to sit in the center \nEnter 3 to sit by the aisle");
			int choice = s.askPlacementE();
			System.out.println(choice);
			if (choice==1)// if the passenger wants a window seat, the program tries to find an empty seat
			{
				int q=0;
				int vary=0;
				while(i<numberOfEconomySeats)
				{
					if (eSeats.get(i)!= null && q==0)
					{
						i=i+vary;
						if (i%2!=0)
							vary=1;
						if (i%2==0)
							vary=5;
					}
					if (eSeats.get(i)== null)
					{
						eSeats.add(i,s);
						eNames.add(i,name+" Placement-Economy Class window seat");
						q=1;
						i=numberOfEconomySeats;
					}
				}
				if(q==0) //if not match is found
				{
					System.out.println("Sorry, but there are no more Economy Class window seats avaliable. Please try again.");	
				}
					
			}
			if (choice==2)// // if the passenger wants a center seat, the program tries to find an empty seat
			{
				int q=0;
				i++;
				while(i<numberOfEconomySeats)
				{
					if (eSeats.get(i)!= null && q==0)
					{
						i=i+3;
					}
					if (eSeats.get(i)== null)
					{
						eSeats.add(i,s);
						eNames.add(i,name+" Placement-Economy Class center seat");
						q=1;
						i=numberOfEconomySeats;
					}
				}
				if(q==0)// if not match is found
				{
					System.out.println("Sorry, but there are no more Economy Class center seats avaliable. Please try again.");	
				}
			}
			if (choice==3)// if the passenger wants a aisle seat, the program tries to find an empty seat
			{
				i=i+2;
				int q=0;
				int vary=0;
				while(i<numberOfEconomySeats)
				{
					if (eSeats.get(i)!= null && q==0)
					{
						i=i+vary;
						if (i%2!=0)
							vary=5;
						if (i%2==0)
							vary=1;
					}
					if (eSeats.get(i)== null)
					{
						eSeats.add(i,s);
						eNames.add(i,name+" Placement-Economy Class aisle seat");
						q=1;
						i=numberOfEconomySeats;
					}
					
				}
				if(q==0)// if no match is found
				{
					System.out.println("Sorry, but there are no more Economy Class aisle seats avaliable. Please try again.");	
				}
			}
		}
		System.out.println("Is there anyone else traveling with you?\nEnter 1 if yes\nEnter 2 if no");
		contin=s.haveMorePassengers();
		return contin;
	}

	
	public void print()// prints out where all the passengers are on the plane
	{
		System.out.println("In First Class");	
		for(int current=0; current<numberOfFirstSeats; current++)
		{
			int number=current+1;
			System.out.println("Seat#-" + number +" Name-"+fcNames.get(current));
		}
		System.out.println("In Economy Class");	
		for(int current=0; current<numberOfEconomySeats; current++)
		{
			int number=current+1;
			System.out.println("Seat#-" + number +" Name-"+eNames.get(current));
		}
	}
}
