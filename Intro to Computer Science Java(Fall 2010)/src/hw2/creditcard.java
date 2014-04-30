package hw2;
import java.util.Scanner;
public class creditcard
{
  public static void main(String[] args)
  {
	Scanner scanner1 = new Scanner(System.in);
	
	System.out.println("Enter a positive eight digit number: ");
	int number = scanner1.nextInt(); //User enters an eight digit number
	while (number>99999999 || number<0) //If the user enters an invalid number, the program will ask again.
	{
		System.out.println("That is not a postive eight digit number!");
		System.out.println("Enter an eight digit number: ");
		number = scanner1.nextInt();
	}
	//The following integers represent each digit in the credit card number
	int first = number % 10;  
	int second = (number % 100)/10; 
	int third = (number % 1000)/100;
	int fourth = (number % 10000)/1000;
	int fifth = (number % 100000)/10000;
	int sixth = (number % 1000000)/100000;
	int seventh = (number % 10000000)/1000000;
	int eighth = (number % 1000000000)/10000000;
	
	int sum1 = first+third+fifth+seventh; //Adds up the first,third,fifth, and seventh digits
	
	//The following integers represent the individual digits of the second, fourth, sixth, and eighth multiplied by two
	int secondDoubledDigit1 = (second*2) % 10;
	int secondDoubledDigit2 = ((second*2) % 100)/10;
	int fourthDoubleDigit1 = (fourth*2) % 10;
	int fourthDoubleDigit2 = ((fourth*2) % 100)/10;
	int sixthDoubleDigit1 = (sixth*2) % 10;
	int sixthDoubleDigit2 = ((sixth*2) % 100)/10;
	int eighthDoubleDigit1 = (eighth*2) % 10;
	int eighthDoubleDigit2 = ((eighth*2) % 100)/10;
	
	int sum2 = secondDoubledDigit1+secondDoubledDigit2+fourthDoubleDigit1+fourthDoubleDigit2+sixthDoubleDigit1+sixthDoubleDigit2+eighthDoubleDigit1+eighthDoubleDigit2; //Sum of the double numbers' integers
	
	int check = sum1 +sum2; //Adds up the two sums
	  if (check%10==0) //Happens if the card is valid
	  {
	  System.out.println("This credit card number is valid!");
	  }
	  else if (!(check%10 ==0)) //Happens if the card is invalid
	  {
	  System.out.println("This credit card number is invalid. Check(first) digit should be: ");
	  while (!(check%10 ==0)) //Cycles through values of the first digit until a valid card number is found
	  {
		  	if (first==0)
			{	
				number = number + 10;
			}	
		  	number--;
		  	first = number % 10;  
			second = (number % 100)/10; 
			third = (number % 1000)/100;
			fourth = (number % 10000)/1000;
			fifth = (number % 100000)/10000;
			sixth = (number % 1000000)/100000;
			seventh = (number % 10000000)/1000000;
			eighth = (number % 1000000000)/10000000;
			
			sum1 = first+third+fifth+seventh;
			
			secondDoubledDigit1 = (second*2) % 10;
			secondDoubledDigit2 = ((second*2) % 100)/10;
			fourthDoubleDigit1 = (fourth*2) % 10;
			fourthDoubleDigit2 = ((fourth*2) % 100)/10;
			sixthDoubleDigit1 = (sixth*2) % 10;
			sixthDoubleDigit2 = ((sixth*2) % 100)/10;
			eighthDoubleDigit1 = (eighth*2) % 10;
			eighthDoubleDigit2 = ((eighth*2) % 100)/10;
			
			sum2 = secondDoubledDigit1+secondDoubledDigit2+fourthDoubleDigit1+fourthDoubleDigit2+sixthDoubleDigit1+sixthDoubleDigit2+eighthDoubleDigit1+eighthDoubleDigit2;
			
			check = sum1 +sum2;
	  }
	  System.out.println(first);
	  }
  }
}
		  



	 