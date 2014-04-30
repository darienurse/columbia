import java.util.EmptyStackException;
import java.util.Stack;

public class Analyzer {
	public static String infixPostfix(String exp){
		Stack<String> stack = new Stack<String>();
		String infixExp;
		String postfixExp = "";
		String str = "";
		String prevStr = "";
		infixExp = exp;
		
		for (int i=0;i<infixExp.length();i++){
			str = infixExp.substring(i,i+1);
			if((str.matches("[a-zA-Z]|\\d") && (prevStr.matches("[a-zA-Z]|\\d"))||(prevStr.equals("."))||(str.equals("."))))
				postfixExp += str;
			else if(str.matches("[a-zA-Z]|\\d"))
				if (postfixExp.equals(""))
					postfixExp += str;
				else 
					postfixExp += " " + str;
			else if (isOperator(str)){
				if (stack.isEmpty()){
					stack.push(str);
				}
				else{
					String top = stack.peek();
					while (getPrecedence(top,str).equals(top)&& !(stack.isEmpty()) &&!top.equals("(")){
						postfixExp += " " + stack.pop();
						if (!(stack.isEmpty()))
							top = stack.peek();
					}
					stack.push(str);
				}
			}
			else if (isRightPara(str)){
				String top = stack.peek();
				while (!top.equals("(")){
					postfixExp += " " + stack.pop();
					if (!(stack.isEmpty()))
						top = stack.peek();
				}
					stack.pop();
			}
			prevStr = str;
		}
		while(!(stack.isEmpty()))
			postfixExp += " " + stack.pop();
		return postfixExp;
	}
	
	private static boolean isOperator(String s){
		
		String operators = "*/%+-(";
		if (operators.indexOf(s) != -1)
			return true;
		else
			return false;
	}
	
	private static boolean isRightPara(String s){
		
		String operators = ")";
		if (operators.indexOf(s) != -1)
			return true;
		else
			return false;
	}
	
	private static String getPrecedence(String op1, String op2){
		
		String paraOps = "(";
		String multiOps = "*/%";
		String addOps = "+-";
		
		if (paraOps.indexOf(op1) != -1)
			return op1;
		else if (paraOps.indexOf(op2) != -1)
			return op2;
		else if ((multiOps.indexOf(op1) != -1) && (addOps.indexOf(op2) != -1))
			return op1;
		else if ((multiOps.indexOf(op2) != -1) && (addOps.indexOf(op1) != -1))
			return op2;
		else if((multiOps.indexOf(op1) != -1) && (multiOps.indexOf(op2) != -1))
			return op1;
		else 
			return op1;
	}
	
	public static Node postfixTree(String postfixExp){
		Stack<Node> stack = new Stack<Node>();
		try{
			for (String s: postfixExp.split("\\s+")) {
				if(!isOperator(s)){
					Node n = new Node(s,null,null);
					stack.push(n);
				}
				else{
					Node n = new Node(s,stack.pop(),stack.pop());
					stack.push(n);
				}	
			}
		}
		catch(EmptyStackException e){
			return null;
		}
		Node n = stack.pop();
		return n;
	}
	
	public static String evaluateTree(String output){
		Stack<String> stack = new Stack<String>();
		for (String s: output.split("\\s+")) {
			if(!isOperator(s)){
				stack.push(s);
			}
			else{
				stack.push(doCalulation(s,stack.pop(),stack.pop()));
			}
		}
		return stack.pop();
	}
	
	public static String Treeinfix(String output){
		Stack<String> stack = new Stack<String>();
		String prevOp = "";
		String a;
		String b;
		for (String s: output.split("\\s+")) {
			if(!isOperator(s)){
				stack.push(s);
			}
			else{
				a = stack.pop();
				b = stack.pop();
				if (prevOp.equals(""))
					stack.push("("+ b + s +a);
				else
					stack.push("(" + b + s + a + ")");
				prevOp = s;
			}	
		}
		stack.push(stack.pop() + ")");
		return stack.pop();
	}
	
	public static String doCalulation(String s, String A, String B){
		Double a = Double.parseDouble(A);
		Double b = Double.parseDouble(B);
		if (s.equals("+")){
			Double sum = a+b;
			return sum.toString();
		}
		if (s.equals("-")){
			Double difference = a-b;
			return difference.toString();
		}
		if (s.equals("*")){
			Double product = a*b;
			return product.toString();
		}
		if (s.equals("/")){
			Double quotient = a/b;
			return quotient.toString();
		}
		if (s.equals("%")){
			Double remainder = a%b;
			return remainder.toString();
		}
		else
			return "";
	}
}
