package hw5;
//********************************************************************
//  Talk.java       Author: Darien Nurse
//
//  Represents a collection of talks.
//********************************************************************

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
public class Tumor
{
	final static int ROWS = 569;//the number of rows in the data
	final static int COLUMNS = 32;//the number of columns
	public static String[][] tumor = new String[ROWS][COLUMNS]; // creates a two dimensional array
	String record;
	
	public void inputData() throws IOException//method for inputing data
	{
		FileReader data1 = new FileReader("wdbcdata.txt");//reads the text file
		BufferedReader data = new BufferedReader(data1);
		int i=0;
		int j=0;
		while (j<=ROWS-1 && (record = data.readLine()) != null )//this loop will take the data from the text file and input it into the array
		{
			StringTokenizer st = new StringTokenizer(record,",");//the tokenizer will separate every number by ",".
			while (i<=31) 
			{
				tumor[j][i]=st.nextToken();
				i++;
				if(j<=ROWS-1 && i==32)
				{
					j++;
				}
			}
				i=0;
		}
	}
	
	public String[][] getTumor()//returns tumor array
    {
         return tumor;    
    }
	
	public int getRows()//returns ROWS variable
    {
         return ROWS;    
    }
	
	public int getColumns()//returns COLUMNS variable
    {
         return COLUMNS;    
    }
	
}