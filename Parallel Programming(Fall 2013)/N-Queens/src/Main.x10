import x10.util.Timer;
import x10.io.File;
import x10.util.ArrayList;
import x10.lang.Exception;
import x10.io.FileNotFoundException;
import x10.array.Array;
/**
 * Class with the main method.
 *
 * The main method will call the provided Solver.solve() method
 * for each of the benchmark inputs.  The value returned from solve()
 * will be compared against the expected value.
 *
 * If the result is correct, the solve() method will be called three
 * times for each benchmark input and the median time will be stored
 */
public class Main
{
    /**
     * This struct holds one benchmark input, along with the expected value
     * for that input and the benchmark time (in milliseconds).
     */
    static struct Input
    {
        val size         : int;                     // Size of the board along one side (board is a square)
        val pawns        : ArrayList[Point{rank==2}];  // Array of pawns.  The array can be of zero length
        val solutions    : int;                     // Expected/correct number of solutions

        def this(size: int, pawns: ArrayList[Point{rank==2}], solutions: int)
        {
            this.size          = size;
            this.pawns         = pawns;
            this.solutions     = solutions;
        }
        // Returns a string representation of the input problem
        public def toString(): String
        {

            return ( this.size + "-Queens with "+this.pawns.size() +" pawns");

        }
    }

    static val INPUT_FILE = "configurations";
    static val CONF_PAWN_OFFSET = 3;               // Offset of the first pawn in a conf string (if any)

    public static def main(args:Rail[String])
    {
        if (args.size != 1){
            Console.ERR.println("Usage: ./Nqueens <configuration file>");
            throw new IllegalArgumentException("Incorrect number of Arguments");
        }
        var F:File;
        try{
			val filename = args(0);
            F  = new File(filename); //open the file containing board configurations
        }
        catch(FileNotFoundException){
            Console.ERR.println(args(0) + " is not a valid file");
            throw new IllegalArgumentException(args(0) + " is not a valid file");
        }
        for (line in F.lines()){

        	if(line.length() == 0n) {
        		continue;
        	}
        	if( line.charAt(0n) == '/') {
        		Console.OUT.println(line);
        		continue;
        	}
        	
        	val v = line.split(";");

            val size = Int.parse(v(1).trim());          //get the dimension of the matrix
            val solutions = Int.parse(v(2).trim());     //get the expected number of solutions
            val pawns = new ArrayList[Point{rank==2}]();
            for ( i in CONF_PAWN_OFFSET..(v.size-1) ){     //parse pawns which are every element after the first three
                val coords = v(i).split(",");
                val point = Point.make( Int.parse(coords(0).trim()) , Int.parse(coords(1).trim()) ); 
                pawns.add(point);
            }
            val input = Input(size, pawns, solutions);  //construct the input object
            if ( Int.parse( v(0n) ) == 0n ){
                Console.OUT.print("Test case: "+input.toString()+".....");
                run_one_test(input);
            }else{
                Console.OUT.println("Benchmark case: "+input.toString());
                val time = take_median(input);   //take median prints out the results
            }
        }
    }


    /**
     * Helper function that runs once a benchmark test, validate the
     * answer, return the times spent.
     */
    static def run_one_test(input: Input) : long
    {
    	val start     = Timer.milliTime();
        var solver:Solver = new Solver();
        val solutions = solver.solve(input.size, input.pawns);
        val end       = Timer.milliTime();
        var time_in_millis: long = end - start;

        if (solutions != input.solutions)
        {
            Console.OUT.println("Incorrect answer given:"+solutions+". Correct Answer: "+input.solutions);
        } else {
        	Console.OUT.println("Correct Answer");
        }
	
        // Sanity Check
        // We will ensure that the times are non-zero.
        if (time_in_millis == 0L)
            time_in_millis = 1L;
	
        return time_in_millis;      // Return the median speedup
    }

    /**
     * Helper function that runs three times a benchmark test and return the median
     */
    static def take_median(input: Input) : double
    {
        val times = new Rail[long](3);             // Run three times ... we'll take the median value

        for (index in 0..(times.size-1))
        {
        	Console.OUT.print("Subtest #"+(index+1)+": ");
        	times(index) = run_one_test(input);  // Run the test
        }

        val med = median(times(0), times(1), times(2));  //compute the median

		Console.OUT.println("    Times (in milliseconds): [" + times(0) + ", "
                                                        + times(1) + ", "
                                                        + times(2) + "] ... median: "
                                                        + med+"\n");
		
        return med;      // Return the median speedup
    }


    /**
     * Return the median of three values.
     */
    static def median(v1: long, v2: long, v3: long) : long
    {
    	if (v1 > v2)
        {
            if (v3 > v1)            // v3 > v1 > v2
                return v1;
            else if (v3 > v2)       // v1 > v2 > v1
                return v3;
            else                    // v1 > v2 > v1
                return v2;
        }
        else                        // v1 <= v2
        {
            if (v3 > v2)            // v3 > v2 > v1
                return v2;
            else if (v3 > v1)       // v2 > v3 > v1
                return v3;
            else                    // v2 > v1 > v3
                return v1;
        }
    }
}
