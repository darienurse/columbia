import x10.util.Timer;
import x10.io.File;
import x10.util.ArrayList;
import x10.lang.Exception;
import x10.io.FileNotFoundException;
import x10.util.HashMap;
import x10.util.Box;
import x10.util.Random;
import x10.regionarray.*;
import x10.util.OptionsParser;
import x10.util.Option;
/**
 * Class with the main method.
 *
 * The main method will call the Hash.put() and Hash.get() methods that have to be modified.
 *
 */
public class Main
{

	//DEFAULT CONFIGURATION VALUES
    static private val WORKERS = 8;
	static private val INS_PER_THREAD = 1000;
	static private val KEY_LIMIT = 100;
	static private val VALUE_LIMIT = 100;
	static private val RATIO = 0.8;

	static val defaultValue = 0;  //NOT CONFIGURABLE


	// Struct used to record write and reads to the shared data hash tablex
    static struct LogEntry
    {
        val direction    : Boolean;  //if it is a get or a put
 		val order        : long;     //at which point this operation is in the linearized order 
        val key          : long;
        val value        : long; 

        def this(direction: Boolean , order:long , key: long, value: long)
        {
            this.direction          = direction;
			this.order    = order;
            this.key         = key;
            this.value     = value;
        }
    }


    public static def main(args:Rail[String])
    {
    	//try{
		//START PARSING OPTIONS
        val opts = new OptionsParser(args, [
											Option("h","help","this information"),
											Option("c","check","perform correctness check")
											], [
												Option("i","insertions","number of insertions per thread"),
												Option("r","ratio","percentage of get w.r.t. total operations"),
												Option("t","threads","number of threads"),
												Option("k","key-limit","maximum value of a key"),
												Option("v","value-limit","maximum value of a value")
												]);
        if (opts.filteredArgs().size!=0) {  
            Console.ERR.println("Unexpected arguments: "+opts.filteredArgs());
            Console.ERR.println("Use -h or --help.");
            System.setExitCode(1n);
            return;
        }
        if (opts("-h")) {
            Console.OUT.println(opts.usage(""));
            return;
        }

		val workers = opts("-t", WORKERS);
		assert(workers > 0);
		val ratio = opts("-r",RATIO);
		assert(ratio > 0.0 && ratio < 1.0);
		val ins_per_thread = opts("-i",INS_PER_THREAD);
		assert(ins_per_thread > 0);
		val check = opts("-c");
		val key_limit = opts("-k",KEY_LIMIT);
		assert(key_limit > 0);
		val value_limit = opts("-v",VALUE_LIMIT);
		assert(value_limit > 0);
		//FINISHED PARSING

		Console.OUT.println(workers+" Workers\t"+ins_per_thread+" <key,value> pairs per thread\t"+ratio+" ratio of get w.r.t. total number of operations");
	   
		val h = new Hash(defaultValue , workers , ratio , ins_per_thread , key_limit , value_limit); //INSTANCE OF THE DATA STRUCTURE YOU HAVE TO IMPLEMENT

		val log = new Array[ ArrayList[ LogEntry ] ](workers, (long) => new ArrayList[ LogEntry ]() );  //ARRAY WHERE THE HISTORY IS SAVED
		//val betterLog = new ArrayList[String](10000);

		val start = Timer.milliTime();

		finish for (i in 0..(workers-1)) async {
				val rand = new Random(System.nanoTime());  //PRIVATE RANDOM NUMBER GENERATOR FOR EVERY ASYNC
				for (var j:long = 0 ; j < ins_per_thread ; j++){

					val d = rand.nextDouble();
					val key = rand.nextLong(key_limit);
					var value:long = 0;
					var order:long = 0;

					val direction = (d >= ratio);
					if (direction){
						value = rand.nextLong(value_limit);
						order = h.put(key,value);
						//betterLog.add("Inserted: <"+key+","+value+"> = "+order);
					}else{
						//Console.OUT.print("Fetching "+key+"....");
						val orderValue = h.get(key);  //this is a tuple < order , value > 
						order = orderValue.first;
						value = orderValue.second;
						//betterLog.add("Thread "+i+" Fetched <"+key+","+value+"> = "+order);
					}
					log(i).add( new LogEntry( direction , order, key, value) );
				}
		}
		val end = Timer.milliTime();
		Console.OUT.println("\nIt took: "+(end-start)+"ms");
		

		//HERE IS THE CORRECTNESS CHECK
		if (check){
			/*Console.OUT.println("all the penises");
			for (str in betterLog){
				Console.OUT.println(str);
			}*/
			
			Console.OUT.print("Checking Correctness.....");
			val ch = new HashMap[Long,Long]();
			val indexes = new Array[long](workers,(long)=>0);

			for (i in 0..((workers * ins_per_thread)-1)) {
				for ( var j:long = 0; j < workers ; j++){
					if( !log(j).isEmpty() ){
						val v = log(j).getFirst();
						if( v.order == i ){
							log(j).removeFirst();
							if(v.direction){
								ch.put(v.key,v.value);
								//Console.OUT.println("check order " + v.order + " insert (" + v.key + "," + v.value + ")");
							}else{
								val boxedValue = ch.get(v.key);
								var value: Long;
								try{
									value = boxedValue();
								}catch(Exception){
									value = defaultValue;
								}
								//Console.OUT.println("check order " + v.order + " get (" + v.key + "," + value + ")");
								assert(value == v.value);
							}
						}
					}
				}
			}
			Console.OUT.println("OK");
		}
    	//} catch(penis:Exception){Console.OUT.println("Matt is a whore."); penis.printStackTrace(); System.killHere();}
    }
}
