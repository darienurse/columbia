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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Fibroconnect extends JFrame implements ActionListener{

	// Fields
	  private JFrame frame;
	  private DrawingCanvas canvas;
	  private JTextArea messageArea;
	  private String filename;
	  private BufferedReader diskInput;
	  FibroPanel panel;
	  int numCities = 29;
	  String[] fileLines = new String[numCities];
	  ArrayList<CityNode> cities = new ArrayList<CityNode>();
	  PriorityQueue<DisNode> distancesMST = new PriorityQueue<DisNode>();
	  PriorityQueue<DisNode> distances = new PriorityQueue<DisNode>();
	  
	  public Fibroconnect(String file, PriorityQueue<DisNode> d) {
		    filename=file;
		    frame = new JFrame("Fibroconnect");
		    frame.setSize(500, 500);
		    
		    //The graphics area
		    if(d == null){
		    	canvas = new DrawingCanvas();
		    	frame.getContentPane().add(canvas, BorderLayout.CENTER);
		    }
		    else{
		    	panel = new FibroPanel(d);
		    	frame.getContentPane().add(panel, BorderLayout.CENTER);
		    }
		    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    //The message area, mainly for debugging.
		    messageArea = new JTextArea(5, 80);     //one line text area for messages.
		    frame.getContentPane().add(messageArea, BorderLayout.SOUTH);

		    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(2,0));
		    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		    
		    JScrollPane messageScroll = new JScrollPane(messageArea);
		    frame.getContentPane().add(messageScroll, BorderLayout.SOUTH);

		    addButton(buttonPanel, "Read File").setForeground(Color.black);
		    addButton(buttonPanel, "Calculate Distances").setForeground(Color.black);
		    addButton(buttonPanel, "Draw Map").setForeground(Color.black);
		    addButton(buttonPanel, "Draw MST Map").setForeground(Color.black);

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
	    if (cmd.equals("Read File") ){
	       initialize();
	       return;
	    } else
	    if (cmd.equals("Calculate Distances") ){
           calcDistances();
           return;
        }  else 
    	if (cmd.equals("Draw Map") ){
    		panel = new FibroPanel(distances);
    		frame.getContentPane().remove(canvas);
	    	frame.getContentPane().add(panel, BorderLayout.CENTER);
	    	frame.validate();
    	    frame.repaint();
 	       return;
 	    } else	
    	if (cmd.equals("Draw MST Map") ){
    		kruskal();
    		frame.getContentPane().remove(panel);
	       	panel = new FibroPanel(distancesMST);
	    	frame.getContentPane().add(panel, BorderLayout.CENTER);
	    	frame.validate();
	    	frame.repaint();
	       return;
	    } else
        	throw new RuntimeException("No such button: "+cmd);
	  }

	public void initialize(){
	       canvas.clear();
	       try{
	          diskInput = new BufferedReader(new InputStreamReader(
		          new FileInputStream(
		          new File(filename))));
	          messageArea.insert("File Name: "+ filename,0);
	          messageArea.append("\n");
	       }
	       catch (IOException e){
	            System.out.println("io exception!");
	       }
	       
	       try {
	    	   String strLine;
		       int i = 0;
		       while ((strLine = diskInput.readLine()) != null && i<numCities){
				   fileLines[i] = strLine; 
				   i++;
			   }
			} catch (IOException e) {
				System.out.println("io exception!");
			}
	       
		   for (String s : fileLines){
			   String tempArray[] = s.split("\\s+");
			   CityNode newNode = new CityNode(tempArray[0], Double.parseDouble(tempArray[1]),Double.parseDouble(tempArray[2]));
			   cities.add(newNode);
		  }
	       canvas.display();
	    }
	  
	  	public void calcDistances(){
	  		int counter = 0;
	  		int counter2 = counter + 1;
	  		while(counter<numCities){
	  			while(counter2<numCities){
	  				DisNode dis = new DisNode(cities.get(counter), cities.get(counter2));
	  				distances.add(dis);
		  			messageArea.append(cities.get(counter).city +"  "+ cities.get(counter2).city +"  "+ dis.distance+"\n");
		  			counter2++;
	  			}
	  			counter++;
	  			counter2 = counter + 1;
	  		}
	  	}
	  	
	  	public void kruskal(){
	  		messageArea.append("\n\n\nMinimum Tree Pairs:\n");
	  		while (!kruskalTest()){
	  			DisNode edge = distances.poll();
	  			if(!sameSet(edge.city1,edge.city2)){
	  				distancesMST.add(edge);
	  				union(edge.city1,edge.city2);
	  				messageArea.append(edge.city1.city +"  "+ edge.city2.city +"  "+"\n");
	  			}
	  		}
	  	}
	  	
	  	private void union(CityNode city1, CityNode city2) {
			CityNode p1 = find(city1);
			CityNode p2 = find(city2);	
			p1.parent = p2;
		}

		private boolean sameSet(CityNode city1, CityNode city2) {
	  		for (CityNode n: cities){
	  			int counter = 0;
	  			while(n.parent != n){
	  				if(n == city1 || n ==city2)
	  					counter++;
	  				n=n.parent;
		  			if (counter==1){
		  				//System.out.print(counter);
		  				return true;
		  			}
	  			}
	  		}
			return false;
		}
		

		public boolean kruskalTest(){
	  		for (CityNode n: cities){
	  			for(int i=0; i<cities.size();i++)
	  				if(find(n) != find(cities.get(i)))
	  					return false;
	  		}
	  		return true;
	  	}
	  	
	  	public CityNode find(CityNode node){
	  		while(node.parent != node)
	  			node=node.parent;
			return node;
	  	}
	  	
	  	public double distance(double x1, double y1, double x2, double y2){
  			return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
  		}
	  	
	  	class CityNode{  //standard Binary Tree HNode
	  		String city;
	  		CityNode parent;
	  	    double xpos;  //stores x and y position of the HNode in the tree
	  	    double ypos;  

	  	    CityNode(String name, double x, double y) {
	  	      city = name;
	  	      parent = this;
	  	      xpos = x;
	  	      ypos = y;
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
	  	
	  	public class FibroPanel extends JPanel {
	  		PriorityQueue<DisNode> d;
	  		int xs;
	  		int ys;

	  		public FibroPanel(PriorityQueue<DisNode> d) {
	  		  this.d = d; // allows dispay routines to access the tree
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
  		    	  this.drawMap(d,g); // draw the tree
  		     	 }
  		     	 catch(NullPointerException e){
  		    	  }
  		     	 revalidate(); //update the component panel
  				}
  		
  			private void drawMap(PriorityQueue<DisNode> d, Graphics g) {
  			  PriorityQueue<DisNode> dis = new PriorityQueue<DisNode>(d);
  			  int dx, dy, dx2, dy2;
  		      double XSCALE, FSCALE, YSCALE;  
  		      XSCALE=.4; //scale x by total HNodes in tree
  		      YSCALE=.4; //scale y by tree height
  		      FSCALE=.7;
  		      while (!dis.isEmpty()){
  		    	  DisNode c = dis.poll();
  			      dx = (int) (c.city1.xpos * XSCALE); // get x,y coords., and scale them 
  			      dy = (int) Math.abs((c.city1.ypos * YSCALE)-frame.getHeight()+180);
  			      g.drawString(c.city1.city, dx, dy); // draws the word
  			      dx2 =(int) (c.city2.xpos * XSCALE); // get x,y coords., and scale them 
  			      dy2 = (int) Math.abs((c.city2.ypos * YSCALE)-frame.getHeight()+180);
  			      g.drawString(c.city2.city, dx2, dy2); // draws the word
  		          g.drawLine(dx,dy,dx2,dy2); 
  		      }
	  		}
	  	}
	  	
	  	public static void main(String[] args){
		    //  args[0] is file name from command line
	  		
		    Fibroconnect P=new Fibroconnect(args[0],null);
		    
		}
}