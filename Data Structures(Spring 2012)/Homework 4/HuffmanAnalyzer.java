import java.util.PriorityQueue;

public class HuffmanAnalyzer {
	static int[] myArray = new int[128];
	static PriorityQueue<HNode> nodeQueue = new PriorityQueue<HNode>();
	String code;
	
	public static String encode(String message){
		String results = "CHAR   ASCII   FREQ";
		for (int i=0; i<128; i++){
			myArray[i] = 0;
		}
		for (char ch: message.toCharArray()) {
			for (int i=0; i<128;i++){
				if((char)i == (ch)){
					myArray[i]++;
					i = 128;
				}
			}
		}
		for (int i=0; i<128; i++){
			if (myArray[i] != 0){
				nodeQueue.add(new HNode(i,myArray[i],null,null,""));
				String s = "          ";
				for(int l=0; l*10<i; l++)
					System.out.print("");
					s = s.substring(0, s.length() - 1);
				String myChar =Character.toString((char)i);
				if (myChar.equals(" "))
					myChar= "sp";
				results = results + "\n"+myChar+"              "+ i +s+ myArray[i];
			}
		}
		return results;
	}
	
	public static HNode makeTree(){
		HNode treeNode = new HNode(0,0,null,null,"");
		if(nodeQueue.size()==1){
			HNode only = nodeQueue.poll();
			only.code = "1";
			return only;
		}
		while (nodeQueue.size()>1){
			HNode first = nodeQueue.poll();
			HNode second = nodeQueue.poll();
			treeNode = new HNode(0,(Integer)second.frequency +(Integer)first.frequency,getLesser(first,second),getGreater(first,second),"");
			nodeQueue.add(treeNode);
		}
		return treeNode;
	}
	
	public static HNode getGreater(HNode A,HNode B){
		if (A.getFrequency()<B.getFrequency()){
			buildCode(B,"1");
			return B;
		}
		else
			buildCode(A,"1");
			return A;
	}
	
	public static HNode getLesser(HNode A,HNode B){
		if (A.getFrequency()>=B.getFrequency()){
			buildCode(B,"0");
			return B;
		}
		else
			buildCode(A,"0");
			return A;
	}
	
	public static void buildCode(HNode t,String value){
		if (t!=null){
		buildCode(t.left,value);
		t.code = value + t.code;
		buildCode(t.right,value);
		}
	}
	
	public String messageToCode(HNode t, String input){
		String message = "Encoded Message: ";
		System.out.print("hi");
		System.out.print(input);
		for (char ch: input.toCharArray()) {
			message = message +  findCode(ch,t);
		}
		return message;
	}

	private String findCode(char str, HNode t) {
		if (t!=null){
			findCode(str,t.right);
			if((char)t.nodeIndex == str)
				code = t.code;
			findCode(str,t.left);
		}
			return code;
	}
}
