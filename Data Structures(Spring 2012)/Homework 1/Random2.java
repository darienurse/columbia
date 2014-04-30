import java.util.Random;


public class Random2 {

	Random generator= new Random(); //allocates instance of Java class Random()
	
	public void random2(int n)
	{
		int[] rand = new int[n];
		boolean[] used = new boolean[n];
		int x = generator.nextInt(n);
		for (int i = 0; i < n; i++)
		{
			x = generator.nextInt(n);
			while (used[x] == true)
			{ 
			    x = generator.nextInt(n);
			}
			rand[i] = x;
			used[x] = true;
		}
	}
}