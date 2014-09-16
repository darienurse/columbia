import x10.util.Random;
import x10.util.Timer;


public class Tester {
    public static def main(args: Rail[String]) {
    	var size: long;
    	var maxValue: long;
    	try {
	    	size = Long.parse(args(0));
	    	maxValue = Long.parse(args(1));
    	}
    	catch (NumberFormatException) {
    		Console.OUT.println("invoke as: <program name> <dataset size> <max value of numbers>");
    		return;
    	}
    	catch (IndexOutOfBoundsException) {
    		Console.OUT.println("invoke as: <program name> <dataset size> <max value of numbers>");
    		return;
    	}
    	val threadsString = System.getenv("X10_NTHREADS");
    	var nthreads: long;
    	try {
    		if (threadsString != null) {
    			nthreads = Long.parse(threadsString);
    		}
    		else {
    			nthreads = 24;
    		}
    	}
    	catch (NumberFormatException) {
    		nthreads = 24;
    	}
    	
		val rail = new Rail[long](size);
		val rand = new Random();
		var results: long = 0;
    	for (i in 0..(rail.size-1)) {
    		rail(i) = rand.nextLong(maxValue) + 1;
    	}

    	val start = Timer.milliTime();
    	val mode = new Mode(rail.size);
    	val median = new Median(rail.size, nthreads, mode);
    	val med = median.getMedian(rail);
    	val modes = mode.getMode();
    	val mean = mode.getMean();
    	val stop = Timer.milliTime();
    	
		Console.OUT.println("time (ms): " + (stop - start));
		Console.OUT.print("median: " + med + " mean: " + mean + " mode: ");
		for (m in modes) {
			Console.OUT.print(m + ", ");
		}
		Console.OUT.println();
    }
}