package hw2;
public class FactorPrinter {
	public static void main(String[] args)
	 {
	   {
		   FactorGenerator FG1 = new FactorGenerator(0);	
		   while (FG1.hasMoreFactors(true))
		   {
		   FG1.nextFactor();
		   FG1.hasMoreFactors(false);
		   }
	   }   
	}
	
	}
