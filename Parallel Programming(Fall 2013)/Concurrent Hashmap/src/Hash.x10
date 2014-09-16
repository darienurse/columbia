import x10.util.Timer;
import x10.util.ArrayList;
import x10.util.HashMap;
import x10.util.Pair;
import x10.array.Array_2;
import x10.util.concurrent.*;
import x10.lang.Cell;

/**
 * This is the class that provides the HashMap functionalities.
 *
 * The assignment is to replace the content of this class with code that exhibit
 * a better scalability.
 */
public class Hash
{
	private var pairs : Rail[Pair[AtomicLong, Cell[Long]]];
	private var counter : Long;
	private var defaultValue : Long; // a default value is returned when an element with a given key is not present in the dict.
	private var lock: Lock;
	

	public def this(defV : long , workers : long , ratio : double , ins_per_thread : long , key_limit : long , value_limit : long){
		this.counter = 0;
		// if more insertions than keys, size of key limit works because no possible collisions
		// otherwise, multiply by 2 to assure lots of room for probing
		val length = ins_per_thread * workers < key_limit ? 2 * ins_per_thread * workers : key_limit;
		this.pairs = new Rail[Pair[AtomicLong, Cell[Long]]](length, (i: long) =>
			{return new Pair[AtomicLong, Cell[Long]](new AtomicLong(Long.MIN_VALUE), new Cell[Long](defV));});
		this.lock = new Lock();
		this.defaultValue = defV;
	}

    /**
     * Insert the pair <key,value> in the hash table
     *     'key'
     *     'value' 
     *
     * This function return the unique order id of the operation in the linearized history.
     */
    public def put(key: long, value: long) : long
    {
    	var order: Long;
    	var pair: Pair[AtomicLong, Cell[Long]];
		var K : AtomicLong; // atomic key holder
		var V: Cell[Long]; // value holder
		var k_num: Long; // "snapshot" of key at this time
		var index: Long = key % this.pairs.size;
		
		while( true ){
			// obtain holders and value
			pair = pairs(index);
			V = pair.second;
			K = pair.first;
			k_num = K.get();
			
			if (k_num == key) { // updating existing value, just lock and update
				this.lock.lock();
				order = ++this.counter;
				V(value);
				this.lock.unlock();
				return order;
			}
			if(k_num==Long.MIN_VALUE){ // key has not been used or matches key
				if(K.compareAndSet(k_num, key)){ // attempt to put our key in this bin
					this.lock.lock();
					order = ++this.counter;
					V(value);
					this.lock.unlock();
					return order;
				}
				
				K = pairs(index).first; // someone beat us to claiming the key space, shouldn't be empty anymore
				assert K.get() != Long.MIN_VALUE;
			}
			
			index = this.probe(index); // try next spot
		}
    }

    /**
     * get the value associated to the input key
     *     'key'
     *
     * This function return the pair composed by
	 *     'first'    unique order id of the operation in the linearized history.
	 *     'second'   values associated to the input pair (defaultValue if there is no value associated to the input key)
     */
    public def get(key: long) : Pair[long,long]
    {
    	var pair: Pair[AtomicLong, Cell[Long]];
    	var k_num: Long; // "snapshot" of key at this time
    	var index: Long = key % this.pairs.size;
    	while(true){
    		pair = pairs(index);
    		k_num = pair.first.get();
    		if(k_num == key || k_num == Long.MIN_VALUE){ // works for empty also because values are preloaded with default
    			this.lock.lock();
    			val count = ++this.counter;
    			val returnPair = new Pair[long,long](count, pair.second());
    			this.lock.unlock();
    			return returnPair;
    		}
    		index = probe(index);
    	}
    }
    
    private def probe(index: Long) : Long {
    	// linear probe
    	if (index == this.pairs.size - 1) {
    		return 0; 
    	}
    	else {
    		return index + 1;
    	}
    }
}