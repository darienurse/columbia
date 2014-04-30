/*  Segment
 */


/** A Segment is one part of the body of an inchworm.
    A segment knows
     - its position on the screen,
     - its color
     - the direction it will move next
     - whether it should have a face on it
 */


import java.util.*;
import java.awt.Color;

public class Segment{

  // constant values:
  // the radius of all the segments
  private static int size = 12;

  // the edges of the region where the caterpillars should turn round
  private static final int westEdge = 10; 
  private static final int eastEdge = 290;
  private static final int northEdge = 10;
  private static final int southEdge = 290;
  
  


  private int x;     //the segment's location on the screen
  private int y;

  private String direction;  // Which direction the segment just moved in.
  private Color color;       // What colour to draw it in.
  private boolean face;  // whether it is the 1st segment, should have a face
  
  // There are two constructors

  /** Constructor for the head of a new inchworm.
      A new inchworm should 
        be just one segment long,
        be in a random position,
        have a random direction,
        have a random color (which fades along the segments - each segment is a bit more grey than the previous one)
  */
  public Segment(){
    x = (int) (Math.random()*(eastEdge-westEdge));
    y = (int) (Math.random()*(southEdge-northEdge));

    double n = Math.random();
    if (n<0.25)      direction = "north";
    else if (n<0.5)  direction = "east";
    else if (n<0.75) direction = "south";
    else             direction = "west";

    color = Color.getHSBColor((float)Math.random(), 1.0f, 1.0f);
    face = true;
  }

  /** Constructor for a non-head segment.
      - position is one radius over from the x and y of the previous segment
        (in the opposite direction from the prevDir),
      - moving in the same direction as the previous segment
      - color is the next shade through the spectrum from the previous segment
   */
  public Segment(Segment prevSegment){
    color = nextShade(prevSegment.color);
    x = prevSegment.x;
    y = prevSegment.y;
    if (prevSegment.direction.equals("north"))      y = y+size;
    else if (prevSegment.direction.equals("south")) y = y-size;
    else if (prevSegment.direction.equals("east"))  x = x-size;
    else if (prevSegment.direction.equals("west"))  x = x+size;
    direction = prevSegment.direction;
    face = false;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public String getDirection(){
    return direction;
  }

  public void reverseDirection(){
    direction = opposite(direction);
  }
  
  public void setDirection(String newDir){
    direction = newDir;
  }
    
  public void setFace(boolean f){
    face = f;
  }

  public boolean near(int otherX, int otherY){
      // add near distance measure to see if segment is near food
      // needs to be within size units in either X or Y direction
	  if ((Math.abs(otherX-x) < size) && (Math.abs(otherY-y) < size))
	  {
		  return true;
	  }
      return false;  //placeholder only
  }
  
  public void move(){
    if      (direction.equals("north")) y = y-size;
    else if (direction.equals("south")) y = y+size;
    else if (direction.equals("east"))  x = x+size;
    else if (direction.equals("west"))  x = x-size;
  }
  
  public void updateReverse()
  {
    if      (direction.equals("north")) y = y-size;
    else if (direction.equals("south")) y = y+size;
    else if (direction.equals("east"))  x = x+size;
    else if (direction.equals("west"))  x = x-size;
      
  }
 
  public String toString() {
      String s = "("+String.valueOf(x)+","+String.valueOf(y)+","+direction+")";
      return s;
  }



  /**  A segment should draw itself as a colored circle, centered at its position.
       If it is a face, It should draw two  eyes just above its center
  */
  public void redraw(DrawingCanvas canvas){
    //Template
    // canvas.drawOval(x-size, y-size, size*2, size*2, false);

    canvas.setForeground(color);
    canvas.fillOval(x-size, y-size, size*2, size*2, false);
    canvas.setForeground(Color.black);
    canvas.drawOval(x-size, y-size, size*2, size*2, false);
    if (face){
      canvas.setForeground(Color.black);
      canvas.fillOval(x-size/2, y-size/2, size/3, size/3, false);
      canvas.fillOval(x+size/6, y-size/2, size/3, size/3, false);
    }      
  }


  public String getNewDirection(){
      if      (x<=westEdge)       return "east";
      else if (x>=eastEdge)  return "west";
      else if (y<=northEdge) return "south";
      else if (y>=southEdge) return "north";
      else if (Math.random()<0.9) return direction;
      else  //with low probability, turn
	if (Math.random()<0.6){    // usually turn right
	  if      (direction.equals("north")) return "east";
	  else if (direction.equals("south")) return "west";
	  else if (direction.equals("east"))  return "south";
	  else                                return "north";
	}
	else{                     // but sometimes turn left
	  if      (direction.equals("north")) return "west";
	  else if (direction.equals("south")) return "east";
	  else if (direction.equals("east"))  return "north";
	  else                                return "south";
	}
    }

  // Utility methods, used by the methods above.

  private Color nextShade(Color col){
    float[] hsb = Color.RGBtoHSB(col.getRed(), col.getGreen(), col.getBlue(), null);
    return Color.getHSBColor(hsb[0]+5.0f/360, 1.0f, 1.0f);
  }

  private String opposite(String dir){
    if      (dir.equals("north")) return "south";
    else if (dir.equals("south")) return "north";
    else if (dir.equals("east"))  return "west";
    else                          return "east";
  }

}



