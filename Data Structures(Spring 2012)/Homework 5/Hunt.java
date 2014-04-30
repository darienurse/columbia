import java.awt.BorderLayout;
import java.awt.Color;
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


public class Hunt implements ActionListener {
	private JFrame frame;
	private DrawingCanvas canvas;
	private JTextArea messageArea;
	private String filename;
	private String wordFile;
	private int maxWordSize;
	public int count =0;
	public Graphics Gmain;
	private BufferedReader diskInput;
	HuntPanel panel;
	ArrayList<String> fileLines = new ArrayList<String>();
	ArrayList<HuntNode> letters = new ArrayList<HuntNode>();
	String[] dict= new String[50287];
	int maxCollisions = 0;
	
	public Hunt(String file, String word, String range, ArrayList<HuntNode> L) {
		    filename=file;
		    wordFile=word;
		    maxWordSize= Integer.parseInt(range)+1;
		    if (maxWordSize<1){
		    	System.out.print("invalid word size. try again");
		    	System.exit(0);
		    }
		    frame = new JFrame("3D Word Hunt");
		    frame.setSize(500, 500);
	        //The graphics area
		    if(L == null){
		    	canvas = new DrawingCanvas();
		    	frame.getContentPane().add(canvas, BorderLayout.CENTER);
		    }
		    else{
		    	panel = new HuntPanel(L);
		    	frame.getContentPane().add(panel, BorderLayout.CENTER);
		    }
		    //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    //The message area, mainly for debugging.
		    messageArea = new JTextArea(5, 80);     //one line text area for messages.
		    frame.getContentPane().add(messageArea, BorderLayout.SOUTH);
		    JPanel buttonPanel = new JPanel(new java.awt.GridLayout(2,0));
		    frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		    JScrollPane messageScroll = new JScrollPane(messageArea);
		    frame.getContentPane().add(messageScroll, BorderLayout.SOUTH);
		    addButton(buttonPanel, "Read File").setForeground(Color.black);
		    addButton(buttonPanel, "Draw Hunt").setForeground(Color.black);
		    addButton(buttonPanel, "HUNT!").setForeground(Color.black);
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
	  if (cmd.equals("Read File") ){
	     initialize();
	     return;
	  }else
     if (cmd.equals("Draw Hunt") ){
    		panel = new HuntPanel(letters);
    		frame.getContentPane().remove(canvas);
	    	frame.getContentPane().add(panel, BorderLayout.CENTER);
	    	frame.validate();
    	    frame.repaint();
 	       return;
	  } else
	  if (cmd.equals("HUNT!") ){
		  for(HuntNode node:letters){
			trueHunt(node);
		  }
		  return;
	  }else 
	  if (cmd.equals("Close") ){
		     System.exit(0);
		     return;
		  }else 
	  	throw new RuntimeException("No such button: "+cmd);
	}
	
	public void trueHunt(HuntNode node ){
		frame.getContentPane().remove(panel);
		allOff(letters);
		letters.get(letters.indexOf(node)).light = true;
		panel = new HuntPanel(letters);
	    wordHunt(node,"");
	    wait(.1);
	    frame.getContentPane().add(panel, BorderLayout.CENTER);
	    panel.paintComponents(Gmain);
	    panel.drawHunt(letters,Gmain);
	    panel.update(Gmain);
	    frame.paintAll(Gmain);
	    panel.repaint();
    	frame.validate();
    	frame.repaint();
    	frame.getContentPane().repaint();
	    count++;
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
		       while ((strLine = diskInput.readLine()) != null){
				   fileLines.add(strLine); 
			   }
			} catch (IOException e) {
				System.out.println("io exception!");
			}
	     
		   for (int i=0;i<fileLines.size()-1;){
			   String tempArray[] = fileLines.get(i).split("\\s+");
			   if (tempArray.length == 1){
				   i++;
				   String n = tempArray[0];
				   tempArray = fileLines.get(i).split("\\s+");
				   HuntNode newNode = new HuntNode(n, Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]),Integer.parseInt(tempArray[2]));
				   i++;
				   tempArray = fileLines.get(i).split("\\s+");
				   while(tempArray.length != 1 && i<fileLines.size()-1){
					   newNode.adj.add(new HuntNode("dummy", Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]),Integer.parseInt(tempArray[2])));
					   i++;
					   tempArray = fileLines.get(i).split("\\s+");
				   }
				   letters.add(newNode);
			   }
		   }
		   for (HuntNode N:letters){
			   for(HuntNode o:N.adj){
				   for (HuntNode n:letters){
					   if(o.xpos == n.xpos && o.ypos == n.ypos && o.zpos == n.zpos){
						   N.adj.set(N.adj.indexOf(o),n);
					   }
				   }
			   }
		   }
		   try {
			hashDict();
		} catch (Exception e) {
			System.out.print("FAIL");
			e.printStackTrace();
		}
		   canvas.display();
	}
	
	public void hashDict()throws Exception{
		for(int index=0;index<dict.length; index++){
			dict[index]=null;
		}
		
		int index = 0;
		diskInput = new BufferedReader (new InputStreamReader (
			new FileInputStream (
			new File (wordFile))));
		String key = diskInput.readLine(); //read next word'
		if (key != null){
			key = key.toLowerCase();
		}
		while (key != null) {
			int collisions=0;
			index= (int) this.hash(key,dict.length);
			while(dict[index]!=null) { // is it a collision?
				collisions++;
				index++;
			}
			dict[index] = key;
			if (collisions>maxCollisions){
				maxCollisions=collisions;
			}
			key = diskInput.readLine();
			if (key != null){
				key = key.toLowerCase();
			}
		}
	}
	
	public int hash(String key, int tableSize){
		int hashVal = 0; //uses Horner’s method to evaluate a polynomial
		for( int i = 0; i < key.length( ); i++ )
			hashVal = 61 * hashVal + key.charAt( i );
		hashVal %= tableSize;
		if( hashVal < 0 )
			hashVal += tableSize; //needed if hashVal is negative
		return hashVal;
	}
	
	private void wordHunt(HuntNode n,String word) {
		word = word + n.name;
		if(word.length() == maxWordSize){
			int index = hash(word,dict.length);
			for(int i =0; i<=maxCollisions;i++){
				try{
					if(dict[index+i] != null){ 
						if (word.equals(dict[index+i])){
							messageArea.append(word+"\n");
						}
					}
				}catch(ArrayIndexOutOfBoundsException e){
				}
			}
		}
		else
			for(HuntNode o:n.adj){
				wordHunt(o,word);
			}
	}
	private void allOff(ArrayList<HuntNode> L) {
		for(HuntNode n:L){
			n.light = false;
		}
	}
	public void wait (double d){
        long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (d * 1000));
    }


	class HuntNode implements Comparable{  //standard Binary Tree HNode
  		String name;
  		int xpos;
  		int ypos;
  		int zpos;
  		boolean light =false;
  		ArrayList<HuntNode> adj = new ArrayList<HuntNode>();
  	    double distance;  

  	    HuntNode(String n, int x, int y, int z) {
  	    	name = n;
  	    	xpos = x;
  	    	ypos = y;
  	    	zpos = z;
  	    }
  	    
		public int compareTo(Object otherNode) {
			return 0;
		}
  	  }
	
	public class HuntPanel extends JPanel {
		ArrayList<HuntNode> L;
  		int xs;
  		int ys;

  		public HuntPanel(ArrayList<HuntNode> L) {
  		  this.L = L; // allows dispay routines to access the tree
  		  setBackground(Color.white);
  		  setForeground(Color.black);
  		  System.out.print(count + " ");
  		}
  		
  		protected void paintComponent(Graphics g) {
  			Gmain=g;
  			g.setColor(getBackground()); //colors the window
  			g.fillRect(0, 0, getWidth(), getHeight());
  			g.setColor(getForeground()); //set color and fonts
  			Font MyFont = new Font("SansSerif",Font.PLAIN,10);
  			g.setFont(MyFont);
	     	MyFont = new Font("SansSerif",Font.BOLD,20); //bigger font for tree
	     	g.setFont(MyFont);
	    	drawHunt(this.L,g); // draw the tree
		}
	
			private void drawHunt(ArrayList<HuntNode> L, Graphics g) {
			  System.out.print("PAINT\n");
			  int dx, dy, dx2, dy2, circleSize;
		      double XSCALE, ZSCALE, YSCALE;  
		      XSCALE=100; //scale x by total HNodes in tree
		      YSCALE=100; //scale y by tree height
		      ZSCALE=62;
		      circleSize = 50;
		      for(HuntNode c:L){
			      dx = (int) ((c.ypos * XSCALE)+(c.zpos*ZSCALE))+500; // get x,y coords., and scale them 
			      dy = (int) ((c.xpos * YSCALE)-(c.zpos*ZSCALE))+200;
			      for(HuntNode d:c.adj){
			    	  g.setColor(Color.black);
			    	  dx2 = (int) ((d.ypos * XSCALE)+(d.zpos*ZSCALE))+500; // get x,y coords., and scale them 
				      dy2 = (int) ((d.xpos * YSCALE)-(d.zpos*ZSCALE))+200;
				      g.drawLine(dx,dy,dx2,dy2); 
			      } 
		      }
		      for(HuntNode c:L){
		    	  dx = (int) ((c.ypos * XSCALE)+(c.zpos*ZSCALE))+500; // get x,y coords., and scale them 
			      dy = (int) ((c.xpos * YSCALE)-(c.zpos*ZSCALE))+200;
			      if(c.light==true){
			    	  System.out.print("DEBUG"+ " "+ L.indexOf(c) +"\n");
			    	  g.setColor(Color.red);
			    	  g.fillOval(dx-circleSize/2,dy-circleSize/2, circleSize, circleSize);
			    	  g.setColor(Color.black);
			    	  g.drawString(c.name.toUpperCase(), dx, dy); // draws the word 
			      }
			      else{
			    	  //g.setPaintMode();
			    	  g.setColor(Color.white);
			    	  g.fillOval(dx-circleSize/2,dy-circleSize/2, circleSize, circleSize);
			    	  g.setColor(Color.black);
				      g.drawOval(dx-circleSize/2,dy-circleSize/2, circleSize, circleSize);
				      g.drawString(c.name.toUpperCase(), dx, dy); // draws the word 
			      }
			      g.setColor(Color.black);
		      }
  		}
  	}
  	
  	public static void main(String[] args){
	    //  args[0] is file name from command line
  		
	    Hunt H=new Hunt(args[0],args[1],args[2],null);
	    
	}
}
