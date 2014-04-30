package hw4b;
import java.util.ArrayList;
import java.util.Scanner;


public class Appointment 
{
		ArrayList<String> ap = new ArrayList<String>();//creates an appointment array
		ArrayList<Integer> starts = new ArrayList<Integer>();//creates a starts array
		ArrayList<Integer> ends = new ArrayList<Integer>();//creates an ends array
		ArrayList<Integer> days = new ArrayList<Integer>();//creates a day arra
		public int numberOfAp;//keeps track of the size of an array
		Scanner scanner1 = new Scanner(System.in);
		public  Appointment() //creates an Appointment object
		{
		}
		
		public void inputAp() //method for adding the first appointment
		{
			System.out.println("Welcome to the appointment book. \nThe appointments will start on day 1 and end on day 365");//
			System.out.println("Please enter the name of this Appointment: ");
			String name = scanner1.nextLine(); //User enters a name
			System.out.println("What day does this Appointment take place?");
			int day = scanner1.nextInt(); //User enters a start time
			System.out.println("When does the Appointment begin (In military time without ':') Ex. 9:00 should be 900?");
			int start = scanner1.nextInt(); //User enters a start time
			System.out.println("When does the Appointment end (In military time)?");
			int end = scanner1.nextInt(); //User enters an end time
			//all of the previous inputs will be set to variables
			ap.add(name);
			days.add(day);
			starts.add(start);
			ends.add(end);
			System.out.println("Day"+days.get(0)+" - "+ap.get(0)+ " from " + starts.get(0) + " to " + ends.get(0)+" is scheduled.");
		}
		
		public void addAp() //method for adding more appointments and checks to see if there're any conflicts
		{		
			System.out.println("Please enter the name of this Appointment: ");
			String name = scanner1.nextLine();
			name = scanner1.nextLine();
			System.out.println("What day does this Appointment take place?");
			int day = scanner1.nextInt(); //User enters a start time
			System.out.println("When does the Appointment begin (In military time without ':') Ex. 9:00 should be 900?");
			int start = scanner1.nextInt(); //User enters a start time
			System.out.println("When does the Appointment end (In military time)?");
			int end = scanner1.nextInt(); //User enters an end time
	        ap.add(name);
			days.add(day);
			starts.add(start);
			ends.add(end);
			//the following loops resolve conflicts before they're permanently scheduled
			int i=ap.size()-1;
			if (i==ap.size()-1)
		        {
		           for(int j=0;j<ap.size()-1;j++)
		           {
		              if(starts.get(i) > starts.get(j)&& starts.get(i)<ends.get(j)&& days.get(i)==days.get(j))
		              {
		            		System.out.println(ap.get(i)+ " cannot be sceduled because it conflicts with " + ap.get(j));
			      			days.remove(ap.size()-1);
			      			starts.remove(ap.size()-1);
			      			ends.remove(ap.size()-1); 
			      			ap.remove(ap.size()-1);
			            	break;
		              }
		           }
		        }  	
		}
			
		public void sortAp()//this sort method will sort the arrays by start times and then by days
		{
	        int min;
	        String namesTemp;
	        int startsTemp;
	        int endsTemp;
	        int daysTemp;
	        for(int i=0; i<ap.size()-1;i++)//sorts arrays by start times
	        {
	           min=i;
	           for(int j=i+1;j<ap.size();j++)
	           {
	              if(Integer.valueOf(starts.get(j)).compareTo(Integer.valueOf(starts.get(min)))<0)
	              {	  
	              System.out.println(starts.get(j)+ " is less than " +starts.get(min));  
	              min=j;
	              }
	           } // end inner for loop
	           namesTemp = (String)ap.get(min); 
	           startsTemp = (Integer)starts.get(min);
	           endsTemp = (Integer)ends.get(min);
	           daysTemp = (Integer)days.get(min);
	           ap.set(min, ap.get(i));
	           starts.set(min, starts.get(i));
	           ends.set(min, ends.get(min));
	           days.set(min, days.get(i));
	           ap.set(i,namesTemp);
	           starts.set(i,startsTemp);
	           ends.set(i,endsTemp);  
	           days.set(i,daysTemp);
	        } // end outer for loop
	        
	        for(int i=0; i<ap.size()-1;i++)//sorts arrays by days
	        {
	           min=i;
	           for(int j=i+1;j<ap.size();j++)
	           {
	              if(Integer.valueOf(days.get(j)).compareTo(Integer.valueOf(days.get(min)))<0)
	              {	  
	              System.out.println(days.get(j)+ " is less than " +days.get(min));  
	              min=j;
	              }
	           } // end inner for loop
	           namesTemp = (String)ap.get(min); 
	           startsTemp = (Integer)starts.get(min);
	           endsTemp = (Integer)ends.get(min);
	           daysTemp = (Integer)days.get(min);
	           ap.set(min, ap.get(i));
	           starts.set(min, starts.get(i));
	           ends.set(min, ends.get(min));
	           days.set(min, days.get(i));
	           ap.set(i,namesTemp);
	           starts.set(i,startsTemp);
	           ends.set(i,endsTemp);  
	           days.set(i,daysTemp);
	        } // end outer for loop
	        
		}
		
		public void today() //displays events for a specific day
		{
			System.out.println("\nWhich day's schedule would you like to see?"); //prints the current array
			int today = scanner1.nextInt();
			for (int i=0; i<ap.size(); i++)
			{
				if (today==days.get(i))
				{
					System.out.println("Day"+days.get(i)+"-"+ap.get(i)+ " from " + starts.get(i) + " to " + ends.get(i)+" is scheduled.");
				}	
			}
         }	
		
		public void summary()//prints the final schedule
		{
			System.out.println("\nYour final schedule:\n"); //prints the current array
			for (int i=0; i<ap.size(); i++)
			{
					System.out.println("Day"+days.get(i)+"-"+ap.get(i)+ " from " + starts.get(i) + " to " + ends.get(i)+" is scheduled.");
			}	
		}
		
		public void print()//prints a sorted schedule
		{
			System.out.println("\nYour schedule sorted by day and start time:"); //prints the current array
			for (int i=0; i<ap.size(); i++)
			{
				System.out.println("Day"+days.get(i)+"-"+ap.get(i)+ " from " + starts.get(i) + " to " + ends.get(i)+" is scheduled.");
			}
         }	
}
