package hw5;
import java.io.IOException;
import java.util.Scanner;
public class KTumorTest {
	static int trial=0;
	static Scanner scanner1 = new Scanner(System.in);
	public static void main(String[] args) throws IOException
	  {
	   KNearest n1 = new KNearest(); //creates a balloon object
	   n1.importData();
	   System.out.println("How many trials? WARNING: Too many trials may take a long time to terminate.");
	   int number = scanner1.nextInt(); //User enters a value
		if (number>100)
		{
		System.out.println("That value is too high. Please choose a value less than 100.");
		number = scanner1.nextInt(); //User enters a value
		}
	   while(trial<number)
	   {
	   n1.createExcludedSet();
	   n1.findKNearestNeighbor();
	   trial++;
	   System.out.println("Trial "+trial);
	   }
	   n1.Results();
	   }
}
