package hw1;
import java.util.Scanner;
public class prime
{
  public static void main(String[] args)
  {
	Scanner sc = new Scanner(System.in);
	System.out.println(System.getProperty("user.dir"));

	System.out.println("Enter a positive interger: ");
	int number = sc.nextInt();
	
	int i = 2;
	while(i<=number)
	{
		{
	  int hold = number % i;
	  
	  if (number == i)
	  {
	  System.out.println("The number you entered is prime");
	  i = number + 1;
	  }
	  
	  else if (hold == 0)
	  {
	  System.out.println("The number you entered is not prime. Here is a factor: "+number/i);
	  i = number + 1;
	  }
	  
	  else if (number!=0)
		  i++;
	  {
		  
	  }	 
	  }
    }	
  }
}



	 