package hw4b;
//********************************************************************
//  Talk.java       Author: Darien Nurse
//
//  Represents a collection of talks.
//********************************************************************

import java.util.Arrays;
import java.util.Scanner;
public class Talk
{
	public static int numberOfTalks = 0; //the length of the array
	public static String [] talks = new String[numberOfTalks];//Constructs talks array
	public static int [] starts = new int[numberOfTalks];//Constructs starts array
	public static int [] ends = new int[numberOfTalks];//Constructs ends array
	boolean[] schedule = new boolean[numberOfTalks];//Constructs schedule array
	boolean moreTalks=true; //determines if there are more talks to input
	static public int talkSize;//keeps track of the size of an array
	Scanner scanner1 = new Scanner(System.in);
	public  Talk() //creates a Talk object
	{
		talkSize=0;
	}
	
	public void inputTalks() //method for adding talks
	{
		String no="no";
		System.out.println("Welcome to the talk scheduler. These \ntalks will take place from 8:00AM to 6:00PM\nor from 8:00 to 18:00 in military time. ");//
		System.out.println("Please enter the name of this talk: ");
		String name = scanner1.nextLine(); //User enters a name
		System.out.println("When does the talk begin (In military time without ':') Ex. 9:00 should be 900?");
		int start = scanner1.nextInt(); //User enters a start time
		System.out.println("When does the talk end (In military time)?");
		int end = scanner1.nextInt(); //User enters an end time
			while (moreTalks=true)// the user enters more talks if necessary
			{
				if (talkSize == talks.length) //if the array is too small for new inputs
				{
					talks = Arrays.copyOf(talks, talks.length +1);
					starts = Arrays.copyOf(starts, talks.length +1);
					ends = Arrays.copyOf(ends, talks.length +1);
					schedule = Arrays.copyOf(schedule, talks.length +1);
				}
				talks[talkSize]=name;
				starts[talkSize]=start;
				ends[talkSize]=end;
				schedule[talkSize]=true;
				talkSize++;
				System.out.println("Would you like to schedule another talk?\nIf so, enter the name of this talk. If not, enter 'no'.");
				name = scanner1.nextLine();
				name = scanner1.nextLine();
				if (no.equals(name))//if the user has no more talks to input
					{
						moreTalks=false;
						break;
					}
				System.out.println("When does the talk begin (In military time)?");
				start = scanner1.nextInt(); //User enters a start time
				System.out.println("When does the talk end (In military time)?");
				end = scanner1.nextInt(); //User enters an end time
			}
		System.out.println("Your talks"); //prints the current array
		for (int i=0; i<talkSize; i++)
		{
			System.out.println(talks[i] + " from " + starts[i] + " to " + ends[i]);
		}
	}
	
	public static void sortTalks()//sorts the arrays by start time
	{
        int min;
        String talksTemp;
        int startsTemp;
        int endsTemp;
        for(int i=0; i<talkSize-1;i++)//These series of loops rearrange all arrays according to starts time in acending order
        {
           min=i;
           for(int j=i+1;j<talkSize;j++)
           {
              if(Integer.valueOf(starts[j]).compareTo(Integer.valueOf(starts[min]))<0)
              {	  
              min=j;
              }
           } // end inner for loop
           talksTemp=talks[min];
           startsTemp=starts[min];
           endsTemp=ends[min];
           talks[min]=talks[i];
           starts[min]=starts[i];
           ends[min]=ends[i];
           talks[i]=talksTemp;
           starts[i]=startsTemp;
           ends[i]=endsTemp;
        } // end outer for loop
        System.out.println("\nYour schedule sorted by start time:"); //prints the sorted arrays
		for (int i=0; i<talkSize; i++)
		{
			System.out.println(talks[i] + " from " + starts[i] + " to " + ends[i]);
		}
	}
	public String[] getTalks()//returns talks
    {
         return talks;    
    }
	public int[] getStarts()//returns starts
    {
         return starts;    
    }
	public int[] getEnds()//returns ends
    {
         return ends;    
    }
	public boolean[] getSchedule()//returns schedule
    {
         return schedule;    
    }
	public int getTalkSize()//returns TalkSize
    {
         return talkSize;    
    }	
}		