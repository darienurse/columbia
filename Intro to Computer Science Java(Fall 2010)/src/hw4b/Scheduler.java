package hw4b;
import java.util.Scanner;

public class Scheduler 
{
	static String[] talks;
	static int[] starts;
	static int[] ends;
	static boolean[] schedule;
	static int talkSize;
	static Scanner scanner1 = new Scanner(System.in);
	
	public void importTalks()//retrieves all the information from the Talk class 
	{
		Talk talkSeries = new Talk(); //creates a DeleteRepeat object
		talkSeries.inputTalks();
		Talk.sortTalks();
		talks=talkSeries.getTalks();
		ends=talkSeries.getEnds();
		starts=talkSeries.getStarts();
		schedule=talkSeries.getSchedule();
		talkSize=talkSeries.getTalkSize();
	}
	
	public static void conflict()//searches for conflicts within the talks and asks the user which ones to keep
	{
        for(int i=0; i<talkSize-1;i++)
        {
           for(int j=i+1;j<talkSize;j++)
           {
        	   //the following comparable checks to see any end times overlap start times
              if(Integer.valueOf(ends[i]).compareTo(Integer.valueOf(starts[j]))>0 && schedule[j]==true && schedule[i]==true  )
              {  
            	System.out.println("\nThere appears to be a conflict between\n" + talks[i] + " from " + starts[i] + " to " + ends[i] + " and " + talks[j] + " from " + starts[j] + " to " + ends[j]);
            	System.out.println("Please choose which talk you WOULD LIKE TO KEEP.\nEnter 1 to keep " + talks[i] + " or Enter 2 to keep " + talks[j]);
            	int number = scanner1.nextInt(); //User enters a number
        		while (number < 1 && number > 2) //If the user enters an invalid number, the program will ask again.
        		{
        			System.out.println("INVALID INPUT");
        			System.out.println("Please choose which talk you WOULD LIKE TO KEEP.\n Enter 1 to keep" + talks[i] + "and Enter 2 to keep" + talks[j]);
        			number = scanner1.nextInt();
        		}
        		//the user can choose to keep one talk over another depending on their input
        		if (number==1)
        			schedule[i]=false;
        		if (number==2)
            		schedule[j]=false;	
              } 
           } 
        }  
	}

	public static void optimum()//searches for conflicts and removes conflicts to create an optimum schedule
	{
        for(int i=0; i<talkSize-1;i++)
        {
           for(int j=i+1;j<talkSize;j++)
           {
        	  //if a talk over laps more than two talks, it will be canceled 
              if(Integer.valueOf(ends[i]).compareTo (Integer.valueOf(starts[j]))>0 && Integer.valueOf(ends[i]).compareTo(Integer.valueOf(starts[j+1]))>0 && schedule[j]==true && schedule[i]==true && schedule[j+1]==true)
              {  
        			schedule[i]=false;
              } 
              //if two talks are interfering with one another, the seconds one will be canceled
              if(Integer.valueOf(ends[i]).compareTo(Integer.valueOf(starts[j]))>0 && schedule[j]==true && schedule[i]==true  )
              {  
        			schedule[j]=false;
              } 
           } 
        }  
	}
	
	public void finalSchedule()//prints the final schedule
	{
		System.out.println("Your schedule without conflicts:");
		for (int i=0; i<talkSize; i++)
		{
			if(schedule[i]==true)
				System.out.println(talks[i] + " from " + starts[i] + " to " + ends[i]);
		}
	}
}
