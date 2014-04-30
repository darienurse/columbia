import java.util.Random;

public class Random1 {

	Random generator= new Random(); //allocates instance of Java class Random()
	public void random1(int n)
	{
		int[] rand = new int[n];
		int x = generator.nextInt(n);
		rand[0] = x;
		for (int i = 1; i < n; i++)
		{
			x = generator.nextInt(n);
			for (int j = 0; j < rand.length; j++) 
			{ 
			    int var = rand[j];
			    if (var == x)
			    {
			    	x = generator.nextInt(n);
			    	j = 0;
			    }
			}
			rand[i] = x;
		}
	}
}
	