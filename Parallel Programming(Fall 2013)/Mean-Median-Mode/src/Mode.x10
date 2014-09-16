import x10.util.concurrent.AtomicLong;
import x10.util.concurrent.AtomicReference;
import x10.util.HashMap;
import x10.util.NoSuchElementException;
import x10.util.Set;
import x10.util.HashSet;
import x10.util.Map;
import x10.util.concurrent.Lock;
import x10.util.concurrent.AtomicBoolean;

public class Mode {
	private var sum: long;
	private var size: long;
	private val calcs = new HashSet[ModeCalc]();
	
	public def this(size: long) {
		this.sum = 0;
		this.size = size;
	}
	
	public def run(rail: Rail[long], max: long, threads: long) {
		val baseSize = threads > 0 ? max / threads : max;
		var remainingRemainder: long = max % threads;
		var pointer: long = 1;
		while (pointer <= max) {
			val subMin = pointer;
			if (remainingRemainder > 0) {
				remainingRemainder--;
				pointer += baseSize;
			}
			else {
				pointer += (baseSize-1);
			}
			
			val subMax = pointer++;
			this.calcs.add(new ModeCalc(subMin, subMax, this.size/threads));
		}
		
		for (calc in this.calcs) async {
			calc.run(rail);
		}
	}
	
	public def serialRun(rail: Rail[long]) {
		val calc = new ModeCalc(1, Long.MAX_VALUE, rail.size);
		calc.run(rail);
		this.calcs.add(calc);
	}
	
	public def getMode() : Set[Long] {
		var modeCount: long = 0;
		var modeSet: Set[long] = new HashSet[long]();
		for (calc in this.calcs) {
			while(!calc.obtainedLock.get()){} // avoid race to obtaining lock
			calc.lock.lock();
			this.sum += calc.sum;
			if (calc.maxCount > modeCount) {
				modeCount = calc.maxCount;
				modeSet = calc.currentMode;
			}
			else if (calc.maxCount == modeCount) {
				modeSet.addAll(calc.currentMode);
			}
			calc.lock.unlock();
		}
		return modeSet;
	}
	
	// must be called after getMode
	public def getMean() : Double {
		return (this.sum as double) / (this.size as double);
	}
	
	private static class ModeCalc {
		public var sum: long;
		private var min: long;
		private var max: long;
		private var map: Map[Long, Long];
		public var currentMode: Set[Long];
		public var maxCount: Long;
		public val lock = new Lock();
		// used to stop main thread from obtaining lock before async can be spawned and have this worker obtain it
		public var obtainedLock: AtomicBoolean = new AtomicBoolean(false);
		
		public def this(min: long, max: long, size: long) {
			this.min = min;
			this.max = max;
			this.map = new HashMap[Long, Long](2 * size);
			this.currentMode = new HashSet[Long]();
			this.maxCount = 0;
			this.sum = 0;
		}
		
		public def run(rail: Rail[long]) {
			this.lock.lock();
			this.obtainedLock.set(true);
			for (element in rail) {
				if (element <= this.max && element >= this.min) {
					this.addNumToMap(element);
				}
			}
			this.lock.unlock();
		}
		
		private def addNumToMap(num: long) {
			this.sum += num;
			val newCount = this.map.getOrElse(num, 0) + 1;
			this.map.put(num, newCount);
			
			if (newCount > this.maxCount) {
				this.currentMode.clear();
				this.currentMode.add(num);
				this.maxCount = newCount;
			}
			else if (newCount == this.maxCount) {
				this.currentMode.add(num);
			}
		}
	}
	
	
}