package hw5;
import java.io.IOException;


public class Nearest 
{	
	static String[][] tumor;
	static int ROWS;
	static int COLUMNS;
	static int set;
	static int[] exclude;
	static int correctMatch=0;
	static int incorrectMatch=0;
	
	public void importData() throws IOException//retrieves all the information from the Tumor class 
	{
		Tumor t1 = new Tumor(); //creates a Tumor object
		t1.inputData();
		tumor=t1.getTumor();
		ROWS=t1.getRows();
		COLUMNS=t1.getColumns();
		set =(int) (ROWS*0.2); //this variable represents the number of uncharted tumors. 20% of the total
		exclude = new int[set];
		System.out.println("There are currently " + set + " tumors without a benign/malignant assignment");
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
	
	public void findNearestNeighbor()//this method finds the nearest neighbor for each uncharted tumor then determines benign or malignant
	{
		int d=0;
		int e=0;
		int g=2;
		double h = 0;
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
					status= tumor[e][1];//the program will estimate whether the tumor is benign or malignant
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
		int result=correctMatch/set;
		System.out.println("After testing " +set+ " number of tumors over the course of 100 trials, the results are" );
		System.out.println("Correct matches-"+correctMatch+" Incorrect matches-"+incorrectMatch);
		System.out.println("The program is correct " +result+ "% of the time.");
	}
}
