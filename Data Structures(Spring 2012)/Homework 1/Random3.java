import java.util.Random;


public class Random3 {
	
Random generator= new Random(); //allocates instance of Java class Random()
	
	public void random3(int n)
	{
		int[] rand = new int[n];
		int randHold = 0;
		for (int i = 0; i < n; i++)
		{
			rand[i] = i + 1;
		}
		for (int i =1; i < n; i++)
			swapReferences(randHold ,rand[i], rand [generator.nextInt(n)]);
	}
	
	public static final void swapReferences( int a, int value1, int value2 ) {
        int hold = value1;
        value1 = value2;
        value2 = hold;
    }

}
