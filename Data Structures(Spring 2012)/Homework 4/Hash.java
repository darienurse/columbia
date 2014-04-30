import java.io.*;
import java.util.*;
import java.text.*;

public class Hash {
	private final static int tableSize = 46; //first prime after 25143
	int index,collisions;
	int[] hashtable = new int[tableSize];
	
	
	public int performTest(String fileWords, int i) throws Exception {
	for(int index=0;index<tableSize; index++)
		collisions=0;
		int[] hashtable = new int[tableSize];
		hashtable[index]=0;
		BufferedReader diskInput;
		String key;
		diskInput = new BufferedReader (new InputStreamReader (
			new FileInputStream (
			new File (fileWords))));
		key = diskInput.readLine(); //read next word
		while (key != null) {
			index= (int) this.hash(key,tableSize,i);
			if(hashtable[index]>0) { // is it a collision?
				collisions++;
				//System.out.println("word=" + key + " index=" + index +" collisions=" + collisions);
			}
			else
				//System.out.println("word=" + key + " index=" + index);
			hashtable[index]++;
			key = diskInput.readLine();
		}
		return collisions;
	}
	
	public int performCase(String fileWords, int i) throws Exception {
		for(int index=0;index<tableSize; index++)
			collisions=0;
			int[] hashtable = new int[tableSize];
			hashtable[index]=0;
			BufferedReader diskInput;
			String key;
			diskInput = new BufferedReader (new InputStreamReader (
				new FileInputStream (
				new File (fileWords))));
			key = diskInput.readLine(); //read next word
			while (key != null) {
				index= (int) this.hash(key,tableSize,i);
				if(hashtable[index]>0) { // is it a collision?
					collisions++;
					System.out.println("word=" + key + " index=" + index +" collisions=" + collisions);
				}
				else
					//System.out.println("word=" + key + " index=" + index);
				hashtable[index]++;
				key = diskInput.readLine();
			}
			return collisions;
		}
	
	public int hash(String key, int tableSize, int i2){
		int hashVal = 0; //uses Horner’s method to evaluate a polynomial
		for( int i = 0; i < key.length( ); i++ )
			hashVal = i2 * hashVal + key.charAt( i );
		hashVal %= tableSize;
		if( hashVal < 0 )
			hashVal += tableSize; //needed if hashVal is negative
		return hashVal;
	}
	
	public static void main(String[] args) throws Exception {
		Hash app = new Hash();
		boolean testing = false;
		if (testing){
			int collisionsSoFar = tableSize;
			for(int i=1; i<999999999;i++){
				int c = app.performTest(args[0],i);
				if(c<collisionsSoFar){
					collisionsSoFar=c;
					System.out.println("Collisions so far "+collisionsSoFar+". Multipler:" + i);
				}
				if(i%1000000 == 0){
					System.out.println(i + " Numbers checked");
				}
			}
		}
		else
			app.performCase(args[0],34381225);
	}
}