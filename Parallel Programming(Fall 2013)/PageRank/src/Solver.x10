import x10.util.Timer;
import x10.util.ArrayList;
import x10.util.Team;
import x10.regionarray.*;
import x10.util.concurrent.AtomicDouble;

/**
 * This is the class that provides the solve() method.
 *
 * The assignment is to replace the contents of the solve() method
 * below with code that actually works :-)
 */
public class Solver
{
	private static val NUM_OF_THREADS = 8n;
	
	private var matrix: DistArray[Double];
	private var vector: DistArray[Double];
	private var sums: DistArray[AtomicDouble];
	
	/**
	* solve(webGraph: Rail[WebNode], dampingFactor: double,epsilon:Double)
	* 
 	* Returns an approximation of the page rank of the given web graph
 	*/
	public def solve(webGraph: Rail[WebNode], dampingFactor: Double, epsilon:Double) : Rail[Double] {
		this.initialize(webGraph, dampingFactor);
		val solutions = this.calculate(epsilon);
		Console.OUT.print("final answer " + solutions);
    	return solutions;
	}
	
	private def calculate(epsilon: Double) : Rail[Double] {
		val matrix = this.matrix;
		val vector = this.vector;
		val sums = this.sums;
		val vectorTeam = new Team(Place.places());
		val multTeam = new Team(Place.places());
		val normTeam = new Team(Place.places());
		
		finish for (p in Place.places()) async at(p) { // start calculation at each place
			val localVector = new Rail[Double](vector.region().size()); // local copy of the distributed array vector
			val prevLocalVector = new Rail[Double](vector.region().size(), 0.0);
			
			var i: long = 0;
			for (;;) { // do calculation nine times
				++i;
				Console.OUT.println("iteration started");
				finish for (place in Place.places()) async { // go to every place to get its section of vector
					val regionAtPlace = vector.dist.get(place);
					
					// copy part of vector stored at place into appropriate part of localVector
					Rail.copy((at(place) vector.raw()), 0, localVector, regionAtPlace.min(0), regionAtPlace.size());
				}
				
				vectorTeam.barrier();
				localVectorMatrixMultiply(sums, vector, localVector, matrix);
				multTeam.barrier();
				
				// collect sums from all places
				var sum: Double = 0.0;
				for (place in Place.places()) {
					finish {
						sum += (at(place) sums(place.id).get());
					}
				}
				
				// normalize
				val dif = (1.0 - sum) / (vector.region().size() as double);
				val localRegion = vector.dist.get(here);
				finish for (j in 0..(NUM_OF_THREADS-1)) async { // loop over workers
					for (var k: long = j /*+ localRegion.min(0)*/; k < localRegion.size(); k += NUM_OF_THREADS) {
						vector(k) += dif;
					}
				}
				printArray(vector.getLocalPortion());
				
				var squareSum: Double = 0.0;
				for (j in 0..(localVector.size-1)) {
					squareSum += ((localVector(j)-prevLocalVector(j))*(localVector(j)-prevLocalVector(j)));
				}
				
				//Console.OUT.println("i: "+i+ "sqrt " + Math.sqrt(squareSum));
				if (Math.sqrt(squareSum) < epsilon || i > 150) {
					
					break;
				}
				Rail.copy(localVector, prevLocalVector);
				normTeam.barrier();
				sums(here.id).set(0.0);
			}
 		}
		
		val finalVector = new Rail[Double](vector.region().size());
		finish for (place in Place.places()) async { // go to every place to get its section of vector
			val regionAtPlace = vector.dist.get(place);
			
			// copy part of vector stored at place into appropriate part of localVector
			Rail.copy((at(place) vector.raw()), 0, finalVector, regionAtPlace.min(0), regionAtPlace.size());
		}
		
		return finalVector;
	}
	
	private static def localVectorMatrixMultiply(sums: DistArray[AtomicDouble]{rank==1}, vector: DistArray[Double]{rank==1},
			localVector: Rail[Double], matrix: DistArray[Double]{rank==2}) {
		val localRegion = matrix.dist.get(here);
		
		finish for (i in 0..(NUM_OF_THREADS-1)) async { // loop over workers
			for (var x: Long = i /*+ localRegion.min(0)*/; x <= localRegion.max(0); x += NUM_OF_THREADS) { // loop over columns
				var sum: Double = 0;
				for (y in 0..(localVector.size-1)) { // column addition
					sum += (localVector(y) * matrix(x, y));
				}
				
				Console.OUT.println("sum " + sum+" vector value "+vector(x));
				Console.OUT.println("sq adding " + (sum-vector(x))*(sum-vector(x)));
				sums(here.id).getAndAdd(sum);
				vector(x) = sum;
			}
		}
	}
    
    private def initialize(webGraph: Rail[WebNode], dampingFactor: Double) {
    	/*
    	 * create the matrix initialized with (1 - damping factor) / N
    	 */
    	val matrixInit = (1.0 - dampingFactor) / webGraph.size;
    	val distMatrix = Dist.makeBlock(Region.make(0..(webGraph.size - 1), 0..(webGraph.size - 1)));
    	this.matrix = DistArray.make[Double](distMatrix, matrixInit);
    	//printArray(matrix.getLocalPortion());
    	
    	finish {
    		/*
    		 * add traversal probabilities to each element in the matrix
    		 */
    		val matrix = this.matrix; // avoid capturing this in at
			for (p in Place.places()) async at(p) { // distribute work across all places
				localFillMatrix(webGraph, matrix, dampingFactor);
				//matrix(0,0) = 1;
			}
			
			/*
			 * initialize u vector with 1/N
			 */
			val vectorInit = 1.0 / (webGraph.size as Double);
			val distVector = Dist.makeBlock(Region.make(0..(webGraph.size - 1)));
			this.vector = DistArray.make[Double](distVector, vectorInit);
			//printArray(this.vector.getLocalPortion());
			
			this.sums = DistArray.make[AtomicDouble](Dist.makeUnique(), ((Point)=>new AtomicDouble(0.0)));
    	}
    	Console.OUT.println("initialized matrix");
    	//printArray(matrix.getLocalPortion());
    }
    
    private static def localFillMatrix(webGraph: Rail[WebNode], matrix: DistArray[Double]{rank==2}, dampingFactor: Double)
    {
    	val region = matrix.dist.get(here); // region of matrix stored at this place
    	val webGraphSize = webGraph.size;
    	
    	for (i in 0..(NUM_OF_THREADS - 1)) async { // loop through workers
    		
    		// iteration strategy meant to maximize spread of work for both random and sorted arrays
    		for (var j: long = i; j < webGraphSize; j += NUM_OF_THREADS) // loop through nodes
    		{
    			val node = webGraph(j);
    			val x = node.id - 1; // data structures are zero based but node id's are 1 based
    			
    			if (node.links.size() > 0 && region.contains(Point.make(x, 0)))
    			{
    				val prob = dampingFactor / (node.links.size() as Double); // loop through nodes this node points to
    				for (otherNode in node.links) {
    					matrix(otherNode.id - 1, x) += prob;
    				}
    			}
    		}
    	}
    }
    
    private static def printArray(array: Array[Double])
    {
    	for (point in array) {
    		val value = array(point);
    		Console.OUT.print(value + " ");
    	}
    	Console.OUT.println();
    }
}
