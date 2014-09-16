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
 * will be compared against the expected value and the time required
 * by solve() will be compared to a baseline time.  The final score
 * will be the geometric mean of the speedups for each benchmark input.
 *
 * As an implementation detail, the solve() method will be called three
 * times for each benchmark input and the median time will be used when
 * computing the geometric mean speedup.
 */
public class Main {
    /**
     * This struct holds one benchmark input, along with the expected values
     * for that input and the benchmark time (in milliseconds).
     */
    static struct ChallengeInput {
        val webGraph     : Rail[WebNode];                // Graph of the web
        val dampingFactor: double;                  // 1 - p(jumping to a random page)
        val epsilon: Double;
        val solutions    : ArrayList[Double];       // Expected/correct number of solutions
        val baseline_time: double;                  // Time in milliseconds

        // Time should not be less than one ...
        def this(webGraph: Rail[WebNode], dampingFactor: Double, epsilon: Double, solutions: ArrayList[Double], time: double) {
            this.webGraph      = webGraph;
            this.dampingFactor = dampingFactor;
            this.epsilon = epsilon;
            this.solutions     = solutions;
            this.baseline_time = time;
        }
    }
    
    public static def main(args:Rail[String]) {
        //Read configuration file
        var F:File;
        if(args.size!=1) {
        	Console.ERR.println(args(0) + " is not a valid file");
        	throw new IllegalArgumentException(args(0) + " is not a valid file");
        }
        val filename = args(0n);
        F  = new File(filename); //open the file containing board configurations

        val challengeInputs = new ArrayList[ChallengeInput]();

        //iterate through every line in the configuration file
        var lineCount:Int = 0n;
        var n:Int = 0n;
        var thisChallengeInput:ChallengeInput = new ChallengeInput(new Rail[WebNode](0), 1.0, 0n, new ArrayList[Double](), 0);
        for (line in F.lines()) {
            // skip empty lines
            if(line.trim().length() == 0n) {
                continue;
            }
            // print out and skip comments
            if( line.charAt(0n) == '/') {
                Console.OUT.println(line);
                continue;
            }

            if(lineCount==0n) {
                // first line of an input, which has the following format
                // <n (# on nodes)> <d (damping factor)> <T (baseline time in miliseconds)>
                // separate into tokens
                val splitFirstLine = line.split(" ");
                // get n (# of web pages)
                n = Int.parse(splitFirstLine(0));
                // get d (damping factor)
                val dampingFactor = Double.parse(splitFirstLine(1));
                // get powerMethodIterations (number of power method iterations)
                val epsilon = Double.parse(splitFirstLine(2));
                // get T (baseline time in milliseconds)
                val baseline_time = Double.parse(splitFirstLine(3));
                // initialize challenge input. This will hold the damping factor along with the list of web pages, the solutions, and the baseline time
                thisChallengeInput = new ChallengeInput(new Rail[WebNode](n, (i:Long)=>new WebNode((i+1) as Int, new ArrayList[WebNode]())), dampingFactor, epsilon, new ArrayList[Double](), baseline_time);
                lineCount++;
            } else if(lineCount <= n) {
            	//Console.OUT.println("Line "+lineCount);
                // one of lines 1...n of the input, which have the following format
                // <pagerank solution> <node number>: <link1> <link2> ...
                // separate into left of colon and right of colon
                val splitContinuingLine = line.split(":");
                val splitContinuingLineLeft  = splitContinuingLine(0).trim().split(" ");
                val splitContinuingLineRight = (splitContinuingLine.size>1)?(splitContinuingLine(1).trim().split(" ")):(new Rail[String](0));
                // get pagerank solution for this web page
                val pagerankSolution = Double.parse(splitContinuingLineLeft(0));
                // get the website's id (we enforce that websites are labeled 1, 2, 3, ...)
                val nodeNumber = Int.parse(splitContinuingLineLeft(1));
                if(nodeNumber!=lineCount) {
                	throw new Exception("Websites must be labeled 1, 2, 3, ..., n");
                }
                // retrieve the list of outgoing links
                for(var i:Int = 0n; i<splitContinuingLineRight.size; i++) {
                	val linkIndex = Int.parse(splitContinuingLineRight(i))-1;
                	if(linkIndex >= 0 && linkIndex<n) {
                		// only add links that aren't broken
                		thisChallengeInput.webGraph(lineCount-1).links.add(thisChallengeInput.webGraph(linkIndex));
                	}
                }
                // populate ChallengeInput with this website's info (pagerank solution and web node)
                thisChallengeInput.solutions.add(pagerankSolution);
                // if this is the last website of this input, add this input to the input list
                if(lineCount<n) {
                    lineCount++;
                } else {
                    // lineCount is n
                    challengeInputs.add(thisChallengeInput);
                    lineCount = 0n;
                }
            }
        }

        Console.OUT.println("");
        try {
            var prod: double = 1.0;
            for (input in challengeInputs)
                prod *= run_one_test(input);
            val geom_mean = Math.pow(prod, 1.0/challengeInputs.size());

            val geom_mean_String: String = String.format("%.2f", new Rail[Any](1, geom_mean));
            Console.OUT.println();
            Console.OUT.println("Geometric Mean Speedup: " + geom_mean_String);
        } catch (e: Exception) {
            Console.OUT.println();
            Console.OUT.println("Geometric Mean Speedup: " + "None ... answer was wrong.");
            e.printStackTrace();
        }
    }


    /**
     * Helper function to run one benchmark test, validate the
     * answer, measure the times and select the median run time
     * for this one benchmark test.
     */
    static def run_one_test(input: ChallengeInput) : double {
        val speedups = new Rail[Double](3);             // Run three times ... we'll take the median value

        Console.OUT.println("Baseline time: " + input.baseline_time);
        for (var index:Int=0n; index<speedups.size; index++) {
            val start     = Timer.milliTime();
            // Make a new Solver to prevent caching
            var solver: Solver = new Solver();
            
            val solutions = solver.solve(input.webGraph, input.dampingFactor, input.epsilon);
            val end       = Timer.milliTime();
            var time_in_millis: long = end - start;
            
            if (input.solutions.size()!=solutions.size) {
            	Console.OUT.println("Wrong output size");
            	throw new Exception("Wrong answer");
            }
            var deltaSquared:Double = 0;
            var isWrong:Boolean = false;
            for(var i:Int = 0n; i<input.solutions.size(); i++) {
            	deltaSquared += (solutions(i)-input.solutions.get(i))*(solutions(i)-input.solutions.get(i));
            	val givenAnswer = solutions(i).toString();
                //Console.OUT.println("\tExpected:"+input.solutions.get(i)+". Given:"+givenAnswer);
            }
            if(Math.sqrt(deltaSquared)>input.epsilon) {
            	Console.OUT.println("Error was too large. Delta ("+Math.sqrt(deltaSquared)+") was greater than epsilon ("+input.epsilon+")");
            	throw new Exception("Wrong answer");
            }
            Console.OUT.println("Correct!");
            

            // We don't want to blow up for super-fast times.
            // We will thus ensure that the times are non-zero.
            //
            if (time_in_millis == 0L) {
                time_in_millis = 1L;
            }

            speedups(index) = input.baseline_time/time_in_millis;
            Console.OUT.println("Time taken: " + time_in_millis);
        }

        val med = median(speedups(0), speedups(1), speedups(2));

        val speedup_0_String: String = String.format("%.2f", new Rail[Any](1, speedups(0)));
        val speedup_1_String: String = String.format("%.2f", new Rail[Any](1, speedups(1)));
        val speedup_2_String: String = String.format("%.2f", new Rail[Any](1, speedups(2)));
        val median_speedup_String: String = String.format("%.2f", new Rail[Any](1, med));

        // We don't need to do this, but it is nice to be able to see the times
        // of each of the three runs.  If something is super-weird, a human might
        // notice ...
        Console.OUT.println("    Speedups: [" + speedup_0_String + ", "
                                              + speedup_1_String + ", "
                                              + speedup_2_String + "] ... median: "
                                              + median_speedup_String+"\n");
        return med;      // Return the median speedup
    }

    /**
     * Return the median of three values.
     */
    static def median(v1: double, v2: double, v3: double)
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
