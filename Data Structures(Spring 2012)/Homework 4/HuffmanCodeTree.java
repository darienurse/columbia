// Code for popping up a window that displays a custom component
// in this case we are displaying a Binary Search tree  
// reference problem 4.38 of Weiss to compute tree HNode x,y positions

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

public class HuffmanCodeTree extends JFrame implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
JScrollPane scrollpane;
  HuffDisplayPanel panel;
  static JTextField textField = new JTextField(20);
  static HuffTree tree = new HuffTree(); // t is Binary tree we are displaying
  static HuffmanCodeTree dt = new HuffmanCodeTree(tree);
  HuffmanAnalyzer analyzer = new HuffmanAnalyzer();
  JTextArea inputArea = new JTextArea();
  JTextArea messageArea = new JTextArea();     
  JTextArea frequencyArea = new JTextArea();  
  String input;
  static String encodedMessage;
  static String output;
  static Boolean drawn = false;
  
  public HuffmanCodeTree(HuffTree t) {
	  	JPanel mainPanel = new JPanel();
	  	setContentPane(mainPanel);
	  	mainPanel.setLayout(new BorderLayout(2,2));
	    JPanel buttonPanel = new JPanel(new GridLayout(0,1));
	    JPanel textPanel = new JPanel(); 
		if (t.root == null){
			panel = new HuffDisplayPanel();
		}
		else{
			panel = new HuffDisplayPanel(t,encodedMessage);
		}	
	    panel.setPreferredSize(new Dimension(300, 300));
	    scrollpane = new JScrollPane(panel);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    if (textField.getText().length()>40)
	    	messageArea.append("Message: " +textField.getText().substring(0,40)+ "...");
   	    else
   	    	messageArea.setText("Message: " + textField.getText());
	    if (tree.root != null)
	    	messageArea.append("\n"+output);
	    inputArea.setEditable(false);
	    inputArea.setText("Please type a message to encode:");
	    textPanel.add(inputArea);
	    textPanel.add(textField);
	    addButton(buttonPanel, "Compute Frequency").setForeground(Color.black);
	    addButton(buttonPanel, "Draw Tree").setForeground(Color.black);
	    JScrollPane messageScroll = new JScrollPane(messageArea);
	    buttonPanel.add(messageScroll);
	    getContentPane().add(textPanel, BorderLayout.NORTH);
	    getContentPane().add(buttonPanel, BorderLayout.WEST);
	    getContentPane().add(scrollpane, BorderLayout.CENTER); 
	    pack();  // cleans up the window panel
  }
  
  private JButton addButton(JPanel panel, String name){
	    JButton button = new JButton(name);
	    button.addActionListener(this);
	    panel.add(button);
	    return button;
	  }
  
 public void actionPerformed(ActionEvent e){
	    String cmd = e.getActionCommand();
	    if (cmd.equals("Compute Frequency") && !drawn) {
	    		compute();
	    } 
	    else if (cmd.equals("Draw Tree") && !drawn) {
	    		drawn=true;
	    		displayTree();
	    } 
	    else{
	    }
	  }
 
 public void compute(){
	 input = textField.getText();// read in the words to create the Binary Search Tree
	 output = HuffmanAnalyzer.encode(input);
	 
     if(input.equals("")){
    	 messageArea.append("\nNothing is there");
     }
     else{
    	 if (input.length()>40)
    		 messageArea.append(input.substring(0,40)+ "..."+"\n"+output);
    	 else
    		 messageArea.append(input+"\n"+output);
     }
 }
 
 public void displayTree(){
	 dt.setVisible(false); //show the display
	 tree.root = HuffmanAnalyzer.makeTree();
	 encodedMessage = analyzer.messageToCode(tree.root,input);
	 if(tree.root == null){
		 messageArea.setText("Invalid Expression");
		 dt.setVisible(true);
	 }
	 else{
	     tree.computeHNodePositions(); //finds x,y positions of the tree HNodes
	     tree.maxheight=tree.treeHeight(tree.root); //finds tree height for scaling y axis
	     dt = new HuffmanCodeTree(tree);
	     //dt.invalidate();
	     //dt.validate();
	     //dt.repaint();
	     dt.setVisible(true); //show the display
	 } 
	 panel.repaint();
     scrollpane.repaint(); 
 }
 
  public static void main(String args[]) {
    dt.setVisible(true); //show the display
  }
}


	class HuffDisplayPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		HuffTree t;
		String encodedMessage;
		int xs;
		int ys;
		static Boolean drawn = false;

    public HuffDisplayPanel(HuffTree t, String encodedMessage) {
      this.t = t; // allows dispay routines to access the tree
      this.encodedMessage = encodedMessage;
      setBackground(Color.white);
      setForeground(Color.black);
    }
    
    public HuffDisplayPanel() {
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
	      ys=ys+10;
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
	
	      public void drawTree(Graphics g, HNode root) {//actually draws the tree
	      int dx, dy, dx2, dy2;
	      int SCREEN_WIDTH=800; //screen size for panel
	      int SCREEN_HEIGHT=600;
	      int XSCALE, YSCALE;  
	      int circleSize = 50;
	      int halfCircleSize = circleSize/2;
	      XSCALE=SCREEN_WIDTH/t.totalHNodes; //scale x by total HNodes in tree
	      YSCALE=(SCREEN_HEIGHT-circleSize-ys)/(t.maxheight+1); //scale y by tree height
	
	      if (root != null) { // inorder traversal to draw each HNode
	        drawTree(g, root.left); // do left side of inorder traversal 
	        dx = (root.xpos * XSCALE)+circleSize; // get x,y coords., and scale them 
	        dy = root.ypos * YSCALE +ys;
	        String s = (char)root.nodeIndex + ":" +root.frequency.toString(); //get the word at this HNode
	        g.drawString(s, dx, dy); // draws the word
	        if(root.left == null && root.right == null)
	        	g.drawString(root.code, dx, dy+50);
	        g.drawOval(dx-halfCircleSize,dy-halfCircleSize, circleSize, circleSize);
	// this draws the lines from a HNode to its children, if any
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
	      g.drawString(encodedMessage, 10, (int) (SCREEN_HEIGHT*1.1));
	    }
}

  class HuffTree {
    String inputString= new String();
    HNode root;
    int totalHNodes = 0; //keeps track of the inorder number for horiz. scaling 
    int maxheight=0;//keeps track of the depth of the tree for vert. scaling

    HuffTree() {
      root = null;
    }

    public int treeHeight(HNode t){
	if(t==null) return -1;
          else return 1 + max(treeHeight(t.left),treeHeight(t.right));
    }
    public int max(int a, int b){
	  if(a>b) return a; else return b;
    }

    public void computeHNodePositions() {
      int depth = 1;
      inorder_traversal(root, depth);
    }

//traverses tree and computes x,y position of each HNode, stores it in the HNode

    public void inorder_traversal(HNode t, int depth) { 
      if (t != null) {
        inorder_traversal(t.left, depth + 1); //add 1 to depth (y coordinate) 
        t.xpos = totalHNodes++; //x coord is HNode number in inorder traversal
        t.ypos = depth; // mark y coord as depth
        inorder_traversal(t.right, depth + 1);
      }
    }
  }

   

@SuppressWarnings("rawtypes")
class HNode implements Comparable {  //standard Binary Tree HNode
    int nodeIndex;
    Object frequency;
    HNode left;
    HNode right;
    String code;
    int xpos;  //stores x and y position of the HNode in the tree
    int ypos;  

    HNode(int x, int y, HNode l, HNode r, String c) {
      left = l;
      right = r;
      nodeIndex = (int) x;
      frequency = (Object) y;
      code = c;
    }
    
    public int compareTo(Object otherNode) {
		int otherFrequency = ((HNode)otherNode).getFrequency();
		return (Integer) this.frequency - otherFrequency;
	}
    
	public int getFrequency(){
		return (Integer) frequency;
	}
  }
  


