package hw1;
import java.util.Scanner;
public class X
{
  public static void main(String[] args)
  {
	Scanner sc = new Scanner(System.in);
	
	System.out.println("How many years?: ");
	int years = sc.nextInt();
	
Scanner sc2 = new Scanner(System.in);
	
	System.out.println("How many days?: ");
	int days = sc2.nextInt();
	
Scanner sc3 = new Scanner(System.in);
	
	System.out.println("How many hours?: ");
	int hours = sc3.nextInt();
	
	  int c = (31556926*years)+(86400*days)+(3600*hours);

	  System.out.println("Your answer is: "+c);
    }	
  }