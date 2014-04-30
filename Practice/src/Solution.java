import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    /* Head ends here */
	public HashMap<String, String> noStar = new HashMap<String, String>();
	public HashMap<String, String> star = new HashMap<String, String>();
	
    public boolean checkForValid(String path, String address) {
    	String starCheck =null;
    	for(String x : star.keySet())  //check if a star address exists for this line
    		if(x.equals(path.substring(0, x.length()-1)))
    			starCheck = star.get(x);
    	if(starCheck != null){
    			if(starCheck.equals(address)) return true;
    			else return false;
    	}
    	starCheck = noStar.get(path);
    	if(starCheck!= null){ 			//check if a non-star address exists for this line
			if(starCheck.equals(address)) return true;
			else return false;
    	}
    	else{                       //this path is unmatched to any line encountered so far
    		if(path.lastIndexOf('*') == path.length()-1)
    			star.put(path.substring(0, path.length()-1), address);
    		else
    			star.put(path, address);
    		return true;
    	}
    	//return false;
    }
    

    /* Tail starts here */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = Integer.parseInt(in.nextLine());
        Solution validate = new Solution();
        for (int i = 0; i<num; i++) {
            String request = in.nextLine();
            String[] split = request.split("\\s+");
            if(!validate.checkForValid(split[0], split[1])){
            	System.out.print("Error: invalid configuration.");
            	i =num;
            }
        }
        System.out.print("Valid configuration.");
    }
}