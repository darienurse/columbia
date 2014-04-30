public class SeparateChainingHashTable<AnyType>
{
     public SeparateChainingHashTable( ){
        this( DEFAULT_TABLE_SIZE );
    }
    public SeparateChainingHashTable( int size ){
        HashNode[] theLists = new HashNode[size];
		for( int i = 0; i < theLists.length; i++ ){
			HashNode head = new HashNode(i);
            theLists[ i ] = head;
		}
    }
    
    public void insert(AnyType newData){
        HashNode whichHead = theLists[ hash( newData ) ];
        if (!contains(whichHead, newData)){
	        if(whichHead.next == null)
	        	whichHead.setNext(new HashNode(newData));
	        else{
	        	HashNode temp = whichHead.next; 
	        	while (temp.next != null)
	        		temp = temp.next;
	        	temp.setNext(new HashNode(newData)); 
	        }
        }
    }
        	
    public boolean contains( HashNode Head, AnyType x ){
        if(Head.next == null)
        	return false;
        else if(Head.next != null){
        	HashNode temp = Head.next;
        	if (temp.data == x)
        		return true;
        	while (temp.next != null){
        		temp = temp.next;
	        	if (temp.data == x)
	        		return true;
        	}
        }
    		return false;
        }

    public int hash( AnyType key ){
        int hashVal = 0;
        //
        //for( int i = 0; i <  ((Object) key).length( ); i++ )
        //    hashVal = 37 * hashVal + ((Object) key).charAt( i );
        //
        return hashVal;
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

        /** The array of Lists. */
    private  HashNode[] theLists; 
    private int currentSize;
}

class HashNode{
	Object data;
	HashNode next;
	
	public HashNode(Object value){
	    data = value;        // Make a head segment
	    setNext(null);
	  }
	
	public void setNext(HashNode next) {
		this.next = next;
	}
}
