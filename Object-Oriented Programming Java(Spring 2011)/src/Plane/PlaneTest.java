package Plane;
import java.util.Scanner;


public class PlaneTest 
{
	static Airplane p = new Airplane();// create an airplane object
	static Scanner scanner2 = new Scanner(System.in);
	static int more=1;// an integer used to determine if there are additional passengers
	public static void main(String[] args)
	 {
		while (more==1)// if more passengers need to be added
		{
			int i=2;
			System.out.println("Hello! Welcome to Darien Airlines!");
			while(i>0)
			{
				i=p.register();// adds passengers
			}
			System.out.println("Is anyone else flying today?\nEnter 1 if yes\nEnter 2 if no");
			more = scanner2.nextInt();
			while(more != 1  && more != 2)
			{
				System.out.println("Invalid Input! \nIs anyone else flying today?\nEnter 1 if yes\nEnter 2 if no");
				more = scanner2.nextInt();
			}
			if (more==2)// prints airplane seating
			{
				p.print();
			}
		}
		  
		   
	   }
}
