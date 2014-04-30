// Code for popping up a window that displays a custom component
// in this case we are displaying a Binary Search tree  
// reference problem 4.38 of Weiss to compute tree node x,y positions

// input is a text file name that will form the Binary Search Tree

//     java DisplaySimpleTree textfile


import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.*;

public class DisplaySimpleTree extends JFrame implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JScrollPane scrollpane;
  DisplayPanel panel;
  static MyTree tree = new MyTree(); // t is Binary tree we are displaying
  static JTextField textField = new JTextField(20);
  static DisplaySimpleTree dt = new DisplaySimpleTree(tree);
  Analyzer analyzer = new Analyzer();
  JTextArea inputArea = new JTextArea();     
  JTextArea postfixArea = new JTextArea();  
  JTextArea postfixArea2 = new JTextArea();
  JTextArea expressionArea = new JTextArea();
  JTextArea expressionArea2 = new JTextArea();
  JTextArea infixArea = new JTextArea();     
  JTextArea infixArea2 = new JTextArea();
  String input;
  static String output;
  static Boolean drawn = false;
  
  public DisplaySimpleTree(MyTree t) {
	if (t.root == null){
		panel = new DisplayPanel();
	}
	else{
    panel = new DisplayPanel(t);
	}
    panel.setPreferredSize(new Dimension(300, 300));
    
    scrollpane = new JScrollPane(panel);
    getContentPane().add(scrollpane, BorderLayout.CENTER);
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    pack();  // cleans up the window panel
    
    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(1,1));
    getContentPane().add(buttonPanel, BorderLayout.NORTH);
    
    inputArea.setEditable(false);
    inputArea.setText("Please type in an infix expression:");
    panel.add(buttonPanel.add(inputArea));
    panel.add(buttonPanel.add(textField));
    panel.add(addButton(buttonPanel, "Infix to Postfix")).setForeground(Color.black);
    panel.add(addButton(buttonPanel, "Postfix to Tree")).setForeground(Color.black);
    panel.add(addButton(buttonPanel, "Evaluate Expression Tree")).setForeground(Color.black);
    panel.add(addButton(buttonPanel, "Tree to Infix")).setForeground(Color.black);
    postfixArea.setText("Postfix Expression:");
    expressionArea.setText("Expression Tree Value:");
    infixArea.setText("Infix Expression:");
    panel.add(buttonPanel.add(postfixArea));
    if (tree.root == null)
    	postfixArea2.setText(textField.getText());
    panel.add(buttonPanel.add(postfixArea2));
    panel.add(buttonPanel.add(expressionArea));
    panel.add(buttonPanel.add(expressionArea2));
    panel.add(buttonPanel.add(infixArea));
    panel.add(buttonPanel.add(infixArea2));
  }
  
  
  
  private JButton addButton(JPanel panel, String name){
	    JButton button = new JButton(name);
	    button.addActionListener(this);
	    panel.add(button);
	    return button;
	  }
 public void actionPerformed(ActionEvent e){
	    String cmd = e.getActionCommand();
	    if (cmd.equals("Infix to Postfix") && !drawn) {
	    		infixToPostfix();
	    } else if (cmd.equals("Postfix to Tree") && !drawn) {
	    		drawn=true;
	    		postfixToTree();
	    } else if(cmd.equals("Evaluate Expression Tree") && tree.root != null){
	    	evaluate();
	    } else if(cmd.equals("Tree to Infix") && tree.root != null){
	    	treeToInfix();
	    }else{
	    }
	  }
 
 public void infixToPostfix(){
	 tree = new MyTree();
	 input = textField.getText();// read in the words to create the Binary Search Tree
	 output = Analyzer.infixPostfix(input);
	 
     if(input.equals("")){
    	 postfixArea2.setText("Nothing is there");
    	 System.out.println("Nothing is there");
     }
     else{
    	 postfixArea2.setText(output);
     }
     panel.repaint();
 }
 
 public void postfixToTree(){
	 dt.setVisible(false); //show the display
	 tree.root = Analyzer.postfixTree(output);
	 if(tree.root == null){
		 postfixArea2.setText("Invalid Infix Expression. Try Again.");
		 dt.setVisible(true);
	 }
	 else{
	     tree.computeNodePositions(); //finds x,y positions of the tree nodes
	     tree.maxheight=tree.treeHeight(tree.root); //finds tree height for scaling y axis
	     dt = new DisplaySimpleTree(tree);
	     dt.setVisible(true); //show the display
	     postfixArea2.setText(output);
	     panel.repaint();
	 }
 }
 
 public void evaluate(){
	 expressionArea2.setText(Analyzer.evaluateTree(output));
	 postfixArea2.setText(output);
	 panel.repaint();
 }
 
 public void treeToInfix(){
		 infixArea2.setText(Analyzer.Treeinfix(output));
		 panel.repaint();
 }
 
  public static void main(String args[]) {
    dt.setVisible(true); //show the display
  }
}

  class DisplayPanel extends JPanel {
     MyTree t;
     int xs;
     int ys;
     static Boolean drawn = false;

    public DisplayPanel(MyTree t) {
      this.t = t; // allows dispay routines to access the tree
      setBackground(Color.white);
      setForeground(Color.black);
    }
    
    public DisplayPanel() {
        setBackground(Color.white);
        setForeground(Color.black);
      }

    protected void paintComponent(Graphics g) {
      if(!drawn){
	      g.setColor(getBackground()); //colors the window
	      g.fillRect(0, 0, getWidth(), getHeight());
	      g.setColor(getForeground()); //set color and fonts
	      Font MyFont = new Font("SansSerif",Font.PLAIN,10);
	      g.setFont(MyFont);
	      xs=10;   //where to start printing on the panel
	      ys=20;
	      ys=ys+10;;
	      MyFont = new Font("SansSerif",Font.BOLD,20); //bigger font for tree
	      g.setFont(MyFont);
	      try{
	    	  this.drawTree(g, t.root); // draw the tree
	      }
	      catch(NullPointerException e){
	      }
	      revalidate(); //update the component panel
	    	}
    	}
	
	      public void drawTree(Graphics g, Node root) {//actually draws the tree
	      int dx, dy, dx2, dy2;
	      int SCREEN_WIDTH=800; //screen size for panel
	      int SCREEN_HEIGHT=700;
	      int XSCALE, YSCALE;  
	      int circleSize = 50;
	      int halfCircleSize = circleSize/2;
	      XSCALE=SCREEN_WIDTH/t.totalnodes; //scale x by total nodes in tree
	      YSCALE=(SCREEN_HEIGHT-circleSize-ys)/(t.maxheight+1); //scale y by tree height
	
	      if (root != null) { // inorder traversal to draw each node
	        drawTree(g, root.left); // do left side of inorder traversal 
	        dx = (root.xpos * XSCALE)+circleSize; // get x,y coords., and scale them 
	        dy = root.ypos * YSCALE +ys;
	        String s = (String) root.data; //get the word at this node
	        g.drawString(s, dx, dy); // draws the word
	        g.drawOval(dx-halfCircleSize,dy-halfCircleSize, circleSize, circleSize);
	// this draws the lines from a node to its children, if any
	        if(root.left!=null){ //draws the line to left child if it exists
	          dx2 = root.left.xpos * XSCALE+circleSize; 
	          dy2 = root.left.ypos * YSCALE +ys;
	          g.drawLine(dx,dy,dx2,dy2); 
	        }
	        if(root.right!=null){ //draws the line to right child if it exists
	          dx2 = root.right.xpos * XSCALE+circleSize;//get right child x,y scaled position
	          dy2 = root.right.ypos * YSCALE + ys;
	          g.drawLine(dx,dy,dx2,dy2);
	        }
	        drawTree(g, root.right); //now do right side of inorder traversal 
	      }
	    }
}

  class MyTree {
    String inputString= new String();
    Node root;
    int totalnodes = 0; //keeps track of the inorder number for horiz. scaling 
    int maxheight=0;//keeps track of the depth of the tree for vert. scaling

    MyTree() {
      root = null;
    }

    public int treeHeight(Node t){
	if(t==null) return -1;
          else return 1 + max(treeHeight(t.left),treeHeight(t.right));
    }
    public int max(int a, int b){
	  if(a>b) return a; else return b;
    }

    public void computeNodePositions() {
      int depth = 1;
      inorder_traversal(root, depth);
    }
    

//traverses tree and computes x,y position of each node, stores it in the node

    public void inorder_traversal(Node t, int depth) { 
      if (t != null) {
        inorder_traversal(t.left, depth + 1); //add 1 to depth (y coordinate) 
        t.xpos = totalnodes++; //x coord is node number in inorder traversal
        t.ypos = depth; // mark y coord as depth
        inorder_traversal(t.right, depth + 1);
      }
}

/* below is standard Binary Search tree insert code, creates the tree */

    public Node insert(Node root, String s) { // Binary Search tree insert
      if (root == null) {
        root = new Node(s, null, null);
        return root;
      }
      else {
        if (s.compareTo((String)(root.data)) == 0) {
           return root;  /* duplicate word  found - do nothing */
        } else   if (s.compareTo((String)(root.data)) < 0)
                     root.left = insert(root.left, s);
                 else
                     root.right = insert(root.right, s);
        return root;
      }
    }
  }

class Node {  //standard Binary Tree node
    Object data;
    Node left;
    Node right;
    int xpos;  //stores x and y position of the node in the tree
    int ypos;  

    Node(String x, Node l, Node r) {
      left = l;
      right = r;
      data = (Object) x;
    }
  }

