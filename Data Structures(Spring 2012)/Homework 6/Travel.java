import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Travel extends JFrame implements ActionListener{

	// Fields
	  private JFrame frame;
	  private DrawingCanvas canvas;
	  private JTextArea messageArea;
	  int maxCoord = 500;
	  TravelPanel panel;
	  ArrayList<CityNode> cities = new ArrayList<CityNode>();
	  ArrayList<CityNode> hullCities = new ArrayList<CityNode>();
	  ArrayList<CityNode> nonHullCities = new ArrayList<CityNode>();
	  ArrayList<DisNode> mins = new ArrayList<DisNode>();
	  Stack<CityNode> grahamStack = new Stack<CityNode>();
	  public Travel(ArrayList<CityNode> d) {
		    frame = new JFrame("The Traveling Salesman");
		    frame.setSize(500, 500);
		    
		    //The graphics area
		    if(d == null){
		    	canvas = new DrawingCanvas();
		    	frame.getContentPane().add(canvas, BorderLayout.CENTER);
		    }
		    else{
		    	panel = new TravelPanel(d,null,null);
		    	frame.getContentPane().add(panel, BorderLayout.CENTER);
		    }
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    //The message area, mainly for debugging.
		    messageArea = new JTextArea(5, 80);     //one line text area for messages.
		    messageArea.append("Enter a Number Here");
		    frame.getContentPane().add(messageArea, BorderLayout.SOUTH);

		    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(2,0));
		    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		    
		    JScrollPane messageScroll = new JScrollPane(messageArea);
		    frame.getContentPane().add(messageScroll, BorderLayout.SOUTH);

		    addButton(buttonPanel, "Create Cities").setForeground(Color.black);
		    addButton(buttonPanel, "Draw Hull").setForeground(Color.black);
		    addButton(buttonPanel, "Insert Minimums").setForeground(Color.black);
		    addButton(buttonPanel, "Close").setForeground(Color.black);

		    frame.setVisible(true);
	  }
	  
	  /** Helper method for adding buttons */
	  private JButton addButton(JPanel panel, String name){
	    JButton button = new JButton(name);
	    button.addActionListener(this);
	    panel.add(button);
	    return button;
	  }
	  
	  /** Respond to button presses */

	  public void actionPerformed(ActionEvent e){
	    String cmd = e.getActionCommand();
	    if (cmd.equals("Create Cities") ){
	    	String input = messageArea.getText();// read in the words to create the Binary Search Tree
	    	try{
	    		int numCities = Integer.parseInt(input);
	    		if (numCities<4){
	    			messageArea.setText("Not enough cities. Try Again.");
	    		}
	    		else{
		    		messageArea.setText("SUCCESS");
		   		 	initialize(numCities);
		   		 	panel = new TravelPanel(cities,null,null);
		    		frame.getContentPane().remove(canvas);
			    	frame.getContentPane().add(panel, BorderLayout.CENTER);
			    	frame.validate();
		    	    frame.repaint();
	    		}
	   	 	}
	   	 	catch (Exception x){
	        	messageArea.setText("Invalid Input. Try Again.");
	   	 	}
		return;
	    } else
	    if (cmd.equals("Draw Hull")){
	    	if(messageArea.getText().equals("SUCCESS")){
		    	frame.getContentPane().remove(panel);
		    	grahamStack=graham();
	           	panel = new TravelPanel(cities,grahamStack,null);
		    	frame.getContentPane().add(panel, BorderLayout.CENTER);
		    	frame.validate();
		    	frame.repaint();
	    	}
           return;
        }  else 
    	if (cmd.equals("Insert Minimums") ){
    		frame.getContentPane().remove(panel);
           	panel = new TravelPanel(cities,grahamStack,cheapInsert());
	    	frame.getContentPane().add(panel, BorderLayout.CENTER);
	    	frame.validate();
	    	frame.repaint();
 	       	return;
 	    } else	
    	 if (cmd.equals("Close") ){
		     System.exit(0);
		     return;
		  }else 
        	throw new RuntimeException("No such button: "+cmd);
	  }
		  
	  public void initialize(int numCities){
		  canvas.clear();
		  for (int i=0;i<numCities;i++){
			  CityNode newNode = new CityNode(Integer.toString(i+1), Math.random() * maxCoord, Math.random() * maxCoord);
			  cities.add(newNode);
		  }
	      canvas.display();
		}
	  private Stack<CityNode> graham() {
		  Stack<CityNode> s = new Stack<CityNode>();
		  ArrayList<CityNode> hCities = new ArrayList<CityNode>(cities);
		  CityNode min = findMin(hCities);
		  hCities=qSort(hCities,min);
		  CityNode max = hCities.get(hCities.size()-1);
		  s.push(max);
		  s.push(min);
		  int i=1;
		  while(i<hCities.size()+1){
			  CityNode current = hCities.get(i-1);
			  CityNode top = s.pop();
			  try{
				  int position =Integer.signum((int) ((top.xpos-s.peek().xpos)*(current.ypos-s.peek().ypos)
						  - (top.ypos-s.peek().ypos)*(current.xpos-s.peek().xpos)));
				  if(position == 1){
				  s.push(top);
				  s.push(current);
				  i++;
				  }
			  }
			  catch(Exception x){
				  messageArea.setText("Can't seem to draw this hull! Try again with more points");
			  }
		  }
		  Stack<CityNode> ss= (Stack<CityNode>) s.clone(); 
		  hullCities = new ArrayList<CityNode>();
		  while(!ss.isEmpty()){
			  hullCities.add(ss.pop());
		  }
		  for(CityNode c: cities){
			  if(!hullCities.contains(c)){
				  nonHullCities.add(c);
			  }
		  }
		  return s;
	  }
	  
	  public ArrayList<DisNode> cheapInsert(){
		  for(int i=0;i<hullCities.size()-1;i++){
			  mins.add(new DisNode(hullCities.get(i),hullCities.get(i+1)));
		  }
		  for(int i=0;i<nonHullCities.size();i++){
			  DisNode currentMin = new DisNode(new CityNode("Dummy1", 0,0), new CityNode("Dummy2",maxCoord,maxCoord));
			  for(DisNode w:mins){
				  DisNode d1 = new DisNode(w.city1,nonHullCities.get(i));
				  DisNode d2 = new DisNode(nonHullCities.get(i),w.city2);
				  double thisMin=d1.distance+d2.distance-w.distance;
				  if(thisMin<currentMin.distance){
					  currentMin=w;
				  }
			  }
			  mins.add(mins.indexOf(currentMin),new DisNode(currentMin.city1,nonHullCities.get(i)));
			  mins.add(mins.indexOf(currentMin),new DisNode(nonHullCities.get(i),currentMin.city2));
			  mins.remove(currentMin);
			  nonHullCities.remove(i);
			  i=0;
		  }
		  return mins;
	  }
	  
	  private ArrayList<CityNode> qSort(ArrayList<CityNode> c,CityNode min) {
		  if(c.size()>1){
			  ArrayList<CityNode> smaller = new ArrayList<CityNode>();
			  ArrayList<CityNode> same = new ArrayList<CityNode>();
			  ArrayList<CityNode> larger = new ArrayList<CityNode>();
			  CityNode pivot = c.get((int) (Math.random()*c.size()));
			  for(CityNode i:c){
				  int position =Integer.signum((int) ((pivot.xpos-min.xpos)*(i.ypos-min.ypos) - (pivot.ypos-min.ypos)*(i.xpos-min.xpos)));
				  if(position == -1){
					  smaller.add(i);
				  }
				  else if(position == 1){
					  larger.add(i);
				  }
				  else{
					  same.add(i);
				  }  
			  }
			  if(same.size()>1){
				  qSortSame(same,min);
			  }
			  qSort(smaller,min);
			  qSort(larger,min);
			  c.clear();
			  c.addAll(smaller);
			  c.addAll(same);
			  c.addAll(larger);  
		  }
		  return c;
	  }
	  
	  private ArrayList<CityNode> qSortSame(ArrayList<CityNode> d,CityNode min){
		  ArrayList<CityNode> smaller = new ArrayList<CityNode>();
		  ArrayList<CityNode> larger = new ArrayList<CityNode>();
		  CityNode pivot = d.get((int) (Math.random()*d.size()));
		  for(CityNode i:d){
			  double d1 = distance(i.xpos,i.ypos,min.xpos,min.ypos);
			  double d2 = distance(pivot.xpos,pivot.ypos,min.xpos,min.ypos);
			  if(d1<d2){
				  smaller.add(i);
			  }
			  else{
				  larger.add(i);
			  }
		  }
		  qSort(smaller,min);
		  qSort(larger,min);
		  d.clear();
		  d.addAll(smaller);
		  d.addAll(larger);
		  return d;
	  }
	  
	  

	public CityNode findMin(ArrayList<CityNode> cities){
		  CityNode currentMin = new CityNode("Dummy", 0,maxCoord);
		  for(CityNode n:cities){
			  if(n.ypos<currentMin.ypos){
				  currentMin = n;
			  }
		  }
		  for(CityNode n:cities){
			  if(n.xpos>currentMin.xpos && n.ypos ==currentMin.ypos){
				  currentMin = n;
			  }
		  }
		  return currentMin;
	  }
  	
  	public double distance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
  	public double crossProduct(){
  		
  		return 0;
  	}
  	
  	class CityNode{  //standard Binary Tree HNode
  		String city;
  		CityNode parent;
  		ArrayList<Double> coordinates = new ArrayList<Double>();
  	    double xpos;  //stores x and y position of the HNode in the tree
  	    double ypos;  

  	    CityNode(String name, double x, double y) {
  	      city = name;
  	      parent = this;
  	      xpos = x;
  	      ypos = y;
  	      coordinates.add(xpos);
  	      coordinates.add(ypos);
  	    }
  	  }
  	
  	class DisNode implements Comparable{  //standard Binary Tree HNode
  		CityNode city1;
  	    CityNode city2;  //stores x and y position of the HNode in the tree
  	    double distance;  

  	    DisNode(CityNode one, CityNode two) {
  	      city1 = one;
  	      city2 = two;
  	      distance = distance(city1.xpos,city1.ypos,
					city2.xpos,city2.ypos);;
  	    }
  	    
		public int compareTo(Object otherNode) {
			double otherDistance = ((DisNode)otherNode).distance;
			return (int) ((int) this.distance - otherDistance);
		}
  	  }
  	
  	public class TravelPanel extends JPanel {
  		ArrayList<CityNode> d;
  		Stack<CityNode> e;
  		ArrayList<DisNode> f;
  		int xs;
  		int ys;

  		public TravelPanel(ArrayList<CityNode> cities, Stack<CityNode> stack, ArrayList<DisNode> hCities) {
  		  this.d = cities;
  		  this.e = stack;
  		  this.f = hCities;
  		  setBackground(Color.white);
  		  setForeground(Color.black);
  		}
  		
  		protected void paintComponent(Graphics g) {
  			g.setColor(getBackground()); //colors the window
  			g.fillRect(0, 0, getWidth(), getHeight());
  			g.setColor(getForeground()); //set color and fonts
  			Font MyFont = new Font("SansSerif",Font.PLAIN,10);
  			g.setFont(MyFont);
	     	MyFont = new Font("SansSerif",Font.BOLD,20); //bigger font for tree
	     	g.setFont(MyFont);
	     	 try{
	    	  this.drawMap(d,e,f,g); // draw the tree
	     	 }
	     	 catch(NullPointerException e){
	    	  }
	     	 revalidate(); //update the component panel
		}
	
		private void drawMap(ArrayList<CityNode> d2,Stack<CityNode> e2,ArrayList<DisNode> f2, Graphics g) {
		  int dx, dy, dx2, dy2;
	      double XSCALE, YSCALE;  
	      XSCALE=1; //scale x by total HNodes in tree
	      YSCALE=1; //scale y by tree height
	      for (int i=0;i<d2.size();i++){
	    	  g.setColor(Color.black);
	    	  CityNode c = d2.get(i);
		      dx = (int) (c.xpos * XSCALE)+frame.getWidth()/3; // get x,y coords., and scale them 
		      dy = (int) Math.abs((c.ypos * YSCALE)-frame.getHeight()+180);
		      g.fillOval( dx, dy,(int)XSCALE*5,(int)YSCALE*5); // draws the word
	          
	      	}
	      if(e2 != null){
	    	  Stack<CityNode> temp = (Stack<CityNode>) e2.clone();
	    	  g.setColor(Color.red);
	    	  CityNode city1 = temp.pop();
	    	  dx = (int) (city1.xpos * XSCALE)+frame.getWidth()/3; // get x,y coords., and scale them 
		      dy = (int) Math.abs((city1.ypos * YSCALE)-frame.getHeight()+180);
		      while(!temp.isEmpty()){
		    	  CityNode city2 = temp.pop();
		    	  dx2 =(int) (city2.xpos * XSCALE)+frame.getWidth()/3; // get x,y coords., and scale them 
  			      dy2 = (int) Math.abs((city2.ypos * YSCALE)-frame.getHeight()+180);
  			      g.drawLine(dx,dy,dx2,dy2);
  			      city1=city2;
  			      dx=dx2;
  			      dy=dy2;
		      }
	      }
	      if(f2 != null){
	    	  double total = 0;
	    	  for (DisNode c:f2){
	    		  g.setColor(Color.blue);
	    		  dx = (int) (c.city1.xpos * XSCALE)+frame.getWidth()/3;
  			      dy = (int) Math.abs((c.city1.ypos * YSCALE)-frame.getHeight()+180);
  			      //g.drawString(c.city1.city, dx, dy); // draws the word
  			      dx2 =(int) (c.city2.xpos * XSCALE)+frame.getWidth()/3;
  			      dy2 = (int) Math.abs((c.city2.ypos * YSCALE)-frame.getHeight()+180);
  			      //g.drawString(c.city2.city, dx2, dy2); // draws the word
  			      total = total+c.distance;
  		          g.drawLine(dx,dy,dx2,dy2); 
		      	}
	    	  total = Math.round(total*100);
	    	  g.drawString("Tour Distance: " + total/100,maxCoord+frame.getWidth()/3,maxCoord);
	      }
		} 
  	}
	  	
	  	public static void main(String[] args){
		    //  args[0] is file name from command line
		    Travel P=new Travel(null);   
		}
}