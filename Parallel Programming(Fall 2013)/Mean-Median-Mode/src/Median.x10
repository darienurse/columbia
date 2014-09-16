import x10.util.concurrent.AtomicLong;
import x10.util.Timer;


public class Median {
	private var tempArray: Rail[long];
	private var threads: long;
	val granularity = 200;
	var mode: Mode;
	
	public def this(size: long, threads: long, mode: Mode) {
		this.threads = threads;
		this.tempArray = new Rail[long](size);
		this.mode = mode;
	}
	
	public def getMedian(a: Rail[long]): long {
		if (this.threads > 1) {
			val b = new Rail[long](a.size);
			val maxValues = new Rail[long](this.threads);
			finish for (t in 0..(this.threads-1)) async {
				var max: long = 0;
				for (var i: long = t; i < a.size; i+=this.threads) {
					b(i) = a(i);
					if (a(i) > max) {
						max = a(i);
					}
				}
				maxValues(t) = max;
			}
			var max: long = 0;
			for (m in maxValues) {
				if (m > max) {
					max = m;
				}
			}
			
			this.threads /= 2;
			this.mode.run(b, max, this.threads);
		}
		else {
			this.mode.serialRun(a);
		}
		val right = a.size - 1;
		val medianIndex = this.quickSelect(a, 0, right, right / 2);
		return a(medianIndex);
	}
	
	private def quickSelect(a: Rail[long], left: long, right: long, place: long) :long {
		if(left == right)
			return left;
		
		if (right-left < 20) {
			Median.sort(a, left, right);
			return place;
		}
		
		// get pivot from m of m's
		// divide around pivot
		var pivotIndex:long = partition(a, left, right, medianOfMedians(a, left, right));
		if (place == pivotIndex){
			return place;
		}
		else if (place < pivotIndex)
			return quickSelect(a, left, pivotIndex-1, place);
		else
			return quickSelect(a, pivotIndex +1, right, place);
	}
	
	private def partition(a: Rail[long], left: long, right: long, pivot:long) : long{
		var pivotValue:long = a(pivot);
		swap(a, pivot, right);
		var storeIndex:long = left;
		for(i in left..(right-1)){
			if(a(i) <= pivotValue){
				swap(a, storeIndex, i);
				storeIndex++;
			}
		}
		swap(a, right, storeIndex);
		return storeIndex;
	}
	
	private def medianOfMedians(a: Rail[long], left: long, right: long) : long {
		// split into groups of five
		val mediansRightIndex = (right - left) / 5;
		
		val criticalCount = mediansRightIndex/5;
		val criticalTasks = mediansRightIndex/this.threads < granularity ? mediansRightIndex/granularity : this.threads;
		// sort each group, put median in collection
		if (criticalTasks >= 4){
			finish for (t in 0..criticalTasks) async{
				for (var i: long = t; i <= criticalCount; i+=criticalTasks) {
					val sublistLeft = left + i*5;
					val sublistRight = (sublistLeft + 4) > right ? right : sublistLeft + 4;
					
					Median.sort(a, sublistLeft, sublistRight);
					
					val medianIndex = sublistLeft + (sublistRight - sublistLeft) / 2;
					this.tempArray(i) = medianIndex;
				}
			}
		}
		else {
			for (i in 0..criticalCount) {
				val sublistLeft = left + i*5;
				val sublistRight = (sublistLeft + 4) > right ? right : sublistLeft + 4;
				
				Median.sort(a, sublistLeft, sublistRight);
				
				val medianIndex = sublistLeft + (sublistRight - sublistLeft) / 2;
				this.tempArray(i) = medianIndex;
			}
		}
		
		val tasks = (4*mediansRightIndex)/this.threads < granularity ? (4*mediansRightIndex)/granularity : this.threads;
		if (tasks > criticalCount && tasks >= 4) {
			finish for (t in 0..tasks) async{
				for (var i: long = t; i <= criticalCount; i+=tasks) {
					val indexToSwitch = this.tempArray(i);
					swap(a, i+left, indexToSwitch);
				}
				
				for (var i: long = t + criticalCount + 1; i <= mediansRightIndex; i+=tasks) {
					val sublistLeft = left + i*5;
					val sublistRight = (sublistLeft + 4) > right ? right : sublistLeft + 4;
					
					Median.sort(a, sublistLeft, sublistRight);
					
					val medianIndex = sublistLeft + (sublistRight - sublistLeft) / 2;
					swap(a, medianIndex, i+left);
				}
			}
		}
		else {
		
			// wait for all threads to complete
			for (i in 0..criticalCount) {
				val indexToSwitch = this.tempArray(i);
				swap(a, i+left, indexToSwitch);
			}
			
			for (i in (criticalCount+1)..mediansRightIndex) {
				val sublistLeft = left + i*5;
				val sublistRight = (sublistLeft + 4) > right ? right : sublistLeft + 4;
				
				Median.sort(a, sublistLeft, sublistRight);
				
				val medianIndex = sublistLeft + (sublistRight - sublistLeft) / 2;
				swap(a, medianIndex, i+left);
			}
		}
		
		return this.quickSelect(a, left, left+mediansRightIndex, left+(mediansRightIndex/2));
	}
	
	private static def swap(a: Rail[long], indexA: long, indexB: long) {
		val temp = a(indexA);
		a(indexA) = a(indexB);
		a(indexB) = temp;
	}
	
	public static def sort(a: Rail[long], left: long, right: long) {
		// no need to process last element, at that point list of size one, no swapping
		for (j in left..(right-1)) { // leftmost element in unsorted section
			var iMin: long = j; // smallest element in unsorted section
			for (i in (j+1)..right) {
				if (a(i) < a(iMin)) {
					iMin = i;
				}
			}
			
			if (iMin != j) { // swap j with smallest element
				swap(a, iMin, j);
			}
		}
	}
}