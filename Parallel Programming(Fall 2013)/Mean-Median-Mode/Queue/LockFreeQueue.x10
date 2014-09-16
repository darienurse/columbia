import x10.util.concurrent.AtomicReference;
import x10.util.NoSuchElementException;

public class LockFreeQueue {
    private static type Data = Long;
    
    private static class Node {
	var data:Data=0;
	var next:AtomicReference[Node] = AtomicReference.newAtomicReference[Node](null);

	public def print() {
	    Console.OUT.println("[" + data + "," + next.get() + "]");	    
	}

	public def this(data:Data, next:Node) {
	    this.data = data;
	    this.next = AtomicReference.newAtomicReference[Node](next);
	}
    }

    private var head:AtomicReference[Node];
    private var tail:AtomicReference[Node];

    public def this() {
	val sentinel = new Node(0,null);
	head = AtomicReference.newAtomicReference[Node](sentinel);
	tail = AtomicReference.newAtomicReference[Node](sentinel);
    }

    public def print(hdr:String) {
	Console.OUT.println("****** " + hdr + " ******");
	var curr:Node = head.get();
	while (curr != null) {
	    curr.print();
	    curr = curr.next.get();
	}
    }

    public def enq(data:Data) {
	var d:Node = new Node(data, null);

	var t:Node = null;
	var n:Node = null;
	do {
	    t = tail.get();
	    n = t.next.get();
	    if (tail.get() != t) continue;
	    if (n != null) {                  // some other thread has started an enqueue...
		tail.compareAndSet(t,n);
		continue;
	    }
	    if (t.next.compareAndSet(null,d)) break;  // STEP 1: add new element

	} while (true);
	tail.compareAndSet(t,d);                      // STEP 2: update tail ptr
    }

    public def deq() {
	var d:Data=0;
	var h:Node=null;
	var t:Node=null;
	var n:Node=null;
	do {
	    h = head.get();
	    t = tail.get();
	    n = h.next.get();
	    if (head.get() != h) continue;
	    if (n == null)
		throw new NoSuchElementException("Nothing to dequeue!");
	    if (t == h)
		tail.compareAndSet(t,n);
	    else
		if (head.compareAndSet(h,n)) break;
	} while (true);
	d = n.data;
	n.data = 0;
	h.next = null;
	return d;
    }
}
