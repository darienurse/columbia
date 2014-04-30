package Balloon;
import java.util.Scanner;
public class FactorGenerator {
int number = 0;
int i;
public FactorGenerator(int numberToFactor) //method used to create a Factor Generator
  { 
	Scanner scanner1 = new Scanner(System.in);
	System.out.println("Enter a positive integer to be factored: ");
	numberToFactor = scanner1.nextInt();// sets user input to numberToFactor
	number = numberToFactor; // changes numberToFactor to just number 
	while (number<0) //executes if the user enters a number less than 0
	{
		number=0;
		System.out.println("NO NEGATIVE NUMBERS ALLOWED!");
		System.out.println("\nEnter a positive integer to be factored: ");
		numberToFactor = scanner1.nextInt();// sets user input to numberToFactor
		number = numberToFactor; // changes numberToFactor to just number 
	}
  }
  
public boolean hasMoreFactors(boolean b) //uses a boolean value to determine if a number has more than one factor
{		
	if (number==1) //executes if the user enters 1
	{
		System.out.println("1");
		b=false;
	}
	if (number==0) //executes if the user enters 0
	{
		System.out.println("No factors for 0");
		b=false;
	}
 	int i = 2;
	while(i<=number) //executes if the user enters a number greater than 1
	{
	  int hold = number % i; 
	  if (number == i) //if the number only has one more factor
	  {	  
		  System.out.println(number+" Last Factor");	
		  b=false;
		  number=0;
		  hold=1;
		  System.exit(0);
	  }
	  else if (hold == 0) //if the number has more factors
	  {  
		  b=true;
		  return b;
	  }
	  else if (number!=0) //cycles through the  method to find factors
	  {  
		  i++;
	  }
	}
		return b;
}  


public void nextFactor() //finds the next factor
 { 
	 int i = 2;
	 if (!(number==1))
	 {
	 	while(i<=Math.sqrt(number) && hasMoreFactors(true)) //finds the next factor if hasMoreFactors is true
	 	{	
	 		  int hold = number % i;
			  if (number == i) //if the another factor doesn't exist
			  {
				  i = number + 1;
			  }
			  else if (hold == 0) //prints out the next factor is it exists
			  {
				  System.out.println(i);
				  number=number/i;  
			  }
			  else if (number!=0) //cycles through to find the next factor
			  {  
				  i++;
			  }
		}
	 }
 }
 }