/* InchwormNode
 */


/** A Inchworm is a sequence of segments, represented as a Linked list
    of InchwormNode.  Each InchwormNode contains a Segment and a
    pointer to the next node.  A inchworm can be created: grow: (adds
    another segment at the end) shrink: (loses the segment just behind
    the head), but it never getrs smaller than 1 node.  move: (move
    forward, with each segment following the one ahead of it) */


import java.util.*;
import java.util.LinkedList;
import java.awt.Color;


public class InchwormNode{

  private Segment segment;    // the info about this segment of the inchworm
  private InchwormNode next;    // the next segment of the inchworm
  Segment temp1 = null;
  InchwormNode temp2 = null;
  InchwormNode temp3 = null;
  Stack<Segment> segs = new Stack<Segment>();
  Stack<InchwormNode> nodes = new Stack<InchwormNode>();
  
  // There are two constructors

  /** Constructor for the head of a new inchworm.
      A new inchworm should 
        be just one segment long,
        be in a random position,
        have a random direction,
        have a random color (which changes along the segments -
	each segment is a different hue from the previous one)
  */
  public InchwormNode(){
    setSegment(new Segment());         // Make a head segment
    setNext(null);
  }

  /** Constructor for a segment to be attached to a previous segment:
      - position is one radius over from the x and y of the previous segment
        (in the opposite direction from the prevDir),
      - moving in the same direction as the previous segment
      - color is the next shade through the spectrum from the previous segment
   */
  public InchwormNode(Segment prevSegment){
    setSegment(new Segment(prevSegment));
    setNext(null);
  }
  

  /* true if the head of this inchworm is near to a position */
  public boolean near(int x, int y){
    return getSegment().near(x,y);
  }
  

  /** Return the position of the head of the inchworm */

  public int getX(){
    return getSegment().getX();
  }

  public int getY(){
    return getSegment().getY();
  }

  /** move: the inchworm should move along, with each segment
       following the one in front of it. That means that each segment
       must move in the direction the previous segment moved the last
       time.  The version of move with no arguments will work out a
       new direction to move (most commonly the same as the current
       direction of the head) then call move(newDirection).  It will
       turn if it is over the edge, or with a 1 in 10 probability.
       The version of move with a new direction should first tell the
       segment after it to move in this segment's current direction,
       then it should move itself in the new direction, and remember
       this new direction.  Note, move doesn't cause it to redraw - it
       just changes the current position.  */

  public void move(){  
    String newDir = getSegment().getNewDirection();
    move(newDir);
  }    

  public void move(String newDir){
    String currentDir = getSegment().getDirection();

    if (getNext()!=null)
      getNext().move(currentDir);

    getSegment().setDirection(newDir);
    getSegment().move();
  }    
 

  /** redraw should first redraw the rest of the inchworm, then it
       should draw this segment.  The segment should draw itself as a
       colored circle, centered at its position.  If it is a face, It
       should draw two eyes just above its center */

  public void redraw(DrawingCanvas canvas){
 // (temporary code that draws just the head, makes the template compile)
    getSegment().redraw(canvas); 
    if(getNext() != null) {
      getNext().redraw(canvas);
    }
  }


  /* Grow will add a new segment onto the end of the inchworm
     It should have the appropriate position and direction.
  */
  public void grow(){
	  Segment temp1 = getSegment();
	  InchwormNode temp2 = getNext();
	  InchwormNode temp3 = null;
	  if (temp2 == null)
	  {
		  InchwormNode newest = new InchwormNode(temp1); 
	  	  setNext(newest);
	  }
	  else
	  {
		  while (temp2 != null)  
		  {
			  temp3 = temp2;
			  temp1 = temp2.getSegment();
			  temp2 = temp2.getNext();
		  }
		  InchwormNode newest = new InchwormNode(temp1); 
	  	  temp3.setNext(newest);
	  }
}


  /* As long as there are at least two segments, shorten should remove
     the very last segment from the inchworm (ie, the last node of the list  */

  public void shorten(){
	  InchwormNode temp2 = getNext();
	  InchwormNode temp3 = null;
	  if (temp2 != null){
		  if (temp2.getNext() == null)
		  {
			  setNext(null);
		  }
		  else
		  {
			  while (temp2.getNext() != null)  
			  {
				  temp3 = temp2;
				  temp1 = temp2.getSegment();
				  temp2 = temp2.getNext();
			  }
			  temp3.setNext(null);
		  }	
	  }
  }


  /** Reverse will reverse the inchworm, turning the head into the
      tail and the tail into the head, reversing all the links along
      the way, and returning a reference to the new head Note, it is
      also necessary to replace the direction of travel in each
      segment by its opposite .*/

  public InchwormNode reverse() {
	  InchwormNode temp1 = getNext();
	  getSegment().setDirection(switchDir(getSegment().getDirection()));
	  segs.add(getSegment());
	  if (temp1 != null){
		  temp1.getSegment().setDirection(switchDir(getSegment().getDirection()));
		  segs.add(temp1.getSegment());
		  nodes.add(temp1);
		  while (temp1.next != null){
			  temp1 = temp1.getNext();
			  temp1.getSegment().setDirection(switchDir(getSegment().getDirection()));
			  segs.add(temp1.getSegment());
			  nodes.add(temp1);
		  }
	  }
	  setSegment(segs.pop());
	  temp1 = getNext();
	  InchwormNode trueTemp1 = getNext().getNext();
	  if (temp1 != null){
		  temp1.setSegment(segs.pop());
		  //trueTemp1 = trueTemp1.getNext();
		  temp1.setNext(nodes.pop());
		  while (trueTemp1 != null){
			  temp1 = trueTemp1;
			  trueTemp1 = trueTemp1.getNext();
			  temp1.setSegment(segs.pop());
			  temp1.setNext(nodes.pop());
		  }
	  }
      return this; //just a place holders
  }
  
  public String switchDir(String d)
  {
	if (d.equals("north"))
		d = ("south");
	else if (d.equals("south"))
		d = ("north");
	else if (d.equals("east"))
		d = ("west");
	else if (d.equals("west"))
		d = ("east");
	return d;
  }
  

public Segment getSegment() {
	return segment;
}

public void setSegment(Segment segment) {
	this.segment = segment;
}

public InchwormNode getNext() {
	return next;
}

public void setNext(InchwormNode next) {
	this.next = next;
}
}

