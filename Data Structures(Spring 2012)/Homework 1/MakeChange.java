import java.util.ArrayList;
import java.util.Scanner;

public class MakeChange {
	
	Scanner moneyScanner = new Scanner(System.in);
	
	public int quarters;
	public int qBreakCounter = 0;
	public int q;
	public int dimes;
	public int dBreakCounter = 0;
	public int d;
	public int nickles;
	public int n;
	public int maxNickles;
	public int recursionsCounter = 0;
	public boolean allNickles = false;
	String value;
	ArrayList<String> holder = new ArrayList<String>();
	 
	public int getMoney()
    {
	   System.out.println("Input a value in cents");
       value = moneyScanner.nextLine();
       int money = Integer.parseInt(value);
       maxNickles = money/5;
       return money;
    }

	public boolean changeable(int money) //Determines if the value is changeable
	{
		if (money % 5 != 0)
		{
			System.out.println( money + " can't be changed");
			return false;
		}
		else
		{
			while(money >= 25 && money != 0)
			{
				quarters++;
				money = money - 25;
			}
			
			while(money >= 10 && money != 0)
			{
				dimes++;
				money = money - 10;
			}
			
			while(money >= 5 && money != 0)
			{
				nickles++;
				money = money - 5;
			}
			q = quarters;
			d = dimes;
			n = nickles;
			return true;
		}
	}
	
	public void makeChange() //Uses Recursion to a certain combination of coins
	{
		if (q > 0)
		{
			holder.add("25");
			q--;
		}
		
		else if (d > 0)
		{
			holder.add("10");
			d--;
		}
		
		else if (n > 0)
		{
			holder.add("5");
			n--;
		}
		
		if ( q != 0 || n != 0 || d!= 0)
		{
			recursionsCounter++;
			makeChange();
		}
	}
	
	public void recalculate()
	{
		holder.clear();
		q = quarters - qBreakCounter ;
		d = dimes - (dBreakCounter) + (2 * qBreakCounter) ;
		n = nickles + (2 * dBreakCounter) + (qBreakCounter);
		if (d != 0)
		{
			dBreakCounter++;
		}
		
		if (q != 0 && d==0)
		{
			qBreakCounter++;
			dBreakCounter = 0;
		}
		q = quarters - qBreakCounter ;
		d = dimes - (dBreakCounter) + (2 * qBreakCounter) ;
		n = nickles + (2 * dBreakCounter) + (qBreakCounter);
		recursionsCounter = 0;
	}
	
	public boolean print()
	{
		if (maxNickles == recursionsCounter + 1 || maxNickles == 0)
		{
			allNickles = true;
		}
		
		if (allNickles)
		{
			System.out.println("END Change for " + value + " = " + holder);
			return allNickles;
		}
		
		else 
		{
			System.out.println("Change for " + value + " = " + holder);
			recalculate();
			return allNickles;
		}
	}
}
