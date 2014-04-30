package hw5;
import java.io.IOException;
import java.util.Scanner;


public class KNearest 
{	
	static String[][] tumor;
	static int ROWS;
	static int COLUMNS;
	static int set;
	static int[] exclude;
	static String[] K;
	static int correctMatch=0;
	static int incorrectMatch=0;
	Scanner scanner1 = new Scanner(System.in);
	int k;
	
	public void importData() throws IOException//retrieves all the information from the Talk class 
	{
		Tumor t1 = new Tumor(); //creates a DeleteRepeat object
		t1.inputData();
		tumor=t1.getTumor();
		ROWS=t1.getRows();
		COLUMNS=t1.getColumns();
		set =(int) (ROWS*0.2);
		exclude = new int[set];
		System.out.println("There are currently " + set + " tumors without a benign/malignant assignment");
		System.out.println("Please choose a value for k.");
		k = scanner1.nextInt(); //User enters a value
		if (k>ROWS)
		{
		System.out.println("That value is too high. Please choose a value for k less than" +ROWS);
		k = scanner1.nextInt(); //User enters a value
		}
		K = new String[k];
	}
	
	public void createExcludedSet()//creates a data set with 113(20% of total) random tumors
	{
		int i=0; 
		int y=0;	
		while(i<=set-1)
		{
			exclude[i]= (int) ((ROWS-1)*Math.random() + 1);
			while(y<i)
			{
				if(exclude[i]==exclude[y])
				{
					i--;
					y=i;
				}
				y++;
			}
			y=0;
			i++;
		}
	}
	
	public void findKNearestNeighbor()//this method finds the nearest neighbor for each uncharted tumor then determines benign or malignant
	{
		int d=0;
		int e=0;
		int g=2;
		double h = 0;
		int kk=0;
		int benignCount = 0;
		int malignantCount = 0;
		double lowest=Double.POSITIVE_INFINITY;
		double distance = 0;
		String status = null;
		
		while(d<=ROWS*0.2-1)//for each uncharted tumor...
		{
			while(e<=ROWS-1)//compare it to every other tumor...
			{
				while(g<=COLUMNS-1)//by finding the distance between each uncharted tumor and every charted tumor
				{
					h =  Math.pow((Double.parseDouble(tumor[exclude[d]][g]) - Double.parseDouble(tumor[e][g])),2) + h;
					g++;
				}
				if (h!=0)
				{
					distance = Math.sqrt(h);
				}
				if(distance<lowest)//when the lowest tumor so far is found...
				{
					lowest=distance;
					if (kk==k)//the K array becomes full then this loop will reset kk
					{
						kk=0;
						if(K[kk].equals("B"))
						{
							benignCount--;
						}
						else
						{
							malignantCount--;
						}
					}
					K[kk]= tumor[e][1];//the lowest values found so far are stored in the K array
					if(K[kk].equals("B"))
					{
						malignantCount++;
					}
					else
					{
						benignCount++;
					}
					if(benignCount>malignantCount)
					{
						status= "B";//If there are more benign tumors than malignant in the K array, then the uncharted tumor willl be marked benign
					}
					else
					{
						status="M";//else it will be marked malignant
					}
					kk++;
				}
				e++;
				h=0;
				g=2;
			}
			if (status.equals(tumor[exclude[d]][1]))//the program then determines whether the tumors are correctly matched
			{
				correctMatch++;
			}
			else
			{
				incorrectMatch++;
			}
			e=0;
			d++;
			lowest=Double.POSITIVE_INFINITY;
		}
	}
	
	public void Results()//the method prints the results of the tests
	{   
		int add=correctMatch+incorrectMatch;
		int result= (correctMatch*100)/add;
		System.out.println("After testing " +set+ " number of tumors over the course of 100 trials, the results are" );
		System.out.println("Correct matches-"+correctMatch+" Incorrect matches-"+incorrectMatch);
		System.out.println("The program is correct " +result+ "% of the time.");
	}
}