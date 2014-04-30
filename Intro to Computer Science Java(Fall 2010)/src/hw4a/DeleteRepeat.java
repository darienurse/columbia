package hw4a;
import java.util.Scanner;
public class DeleteRepeat
{

	final int SET_LENGTH = 1000; //the length of the array
	double [] set = new double[SET_LENGTH];
	int setSize;//keeps track of the size of an array
	Scanner scanner1 = new Scanner(System.in);
	public  DeleteRepeat() //creates a DeleteRepeat object
	{
		setSize=0;
	}
	
	public void inputDigits() //method for adding digits
	{
		int i=0;
		System.out.println("Please enter numbers for this array. \nEnter a positive number: ");
		int number = scanner1.nextInt(); //User enters a number
		while (number<=0) //If the user enters an invalid number, the program will ask again.
		{
			System.out.println("That is not a positive number!");
			System.out.println("Please enter numbers for this array. \nEnter zero or a positive number: ");
			number = scanner1.nextInt();
		}
		while (number > 0)
		{
			if (setSize < set.length)// the user enters more numbers
			{
				set[setSize]=number;
				setSize++;
				System.out.println("Would you like you enter another number?\nIf so, positive number: \nIf not, enter 0.");
				number = scanner1.nextInt(); //User enters a one digit number
			}	
			else // if the set size is over 1000, the program will not accept any more numbers
			{
				number=-1;
				System.out.println("No more numbers can fit in this array.");
			}	
		}
		System.out.println("Your array:"); //prints the current array
		for (i=0; i<setSize; i++)
		{
			System.out.println(set[i]);
		}
	}
	public void removal()//removes duplicate numbers from an array
	{
		int i; // used to keep tract of which element the program is on in the array
		int q; //used to compare all other element to the current value of i
		for (i=0; i<setSize;i++)
		{
			q=i;
			double element= set[i];
			while (q<setSize)
			{	
				if(element==set[q+1] && q<setSize) // if two elements are the same, this if statement will delete the duplicate
				{
					for (int y= q+1; y<setSize;y++)
					{
						set[y]=set[y+1];
					}
					setSize--;
					q--;
				}
				q++;
			}	
		}
		System.out.println("Your array with all copies removed."); // prints the new set of array values
		for (i=0; i<setSize; i++)
		{
			System.out.println(set[i]);
		}
	}
	
}