/*

 */

import java.util.*;
import java.awt.Color;


/** A Food Source is a  food source stuck on the ground.
    It can draw itself, and return its position and type.

 */


public class FoodSource{

  public enum Food { Nutritious,  Wasting }

  private int size = 4;    // the size of the food source
  private int x;     //the FoodSource's location on the screen
  private int y;
  private Food type;   // the kind of food
  private Color color;   


  private static int westEdge = 0;//edge of reg. beyond which  food can't exist
  private static int eastEdge = 300;
  private static int northEdge = 0;
  private static int southEdge = 300;
  
  

  public FoodSource(){
    x = (int) (Math.random()*(eastEdge-westEdge));
    y = (int) (Math.random()*(southEdge-northEdge));
    double r = Math.random();
    if(r<.66){
       type=Food.Nutritious;
       color=Color.green;
   } else {     type=Food.Wasting;
                color=Color.red;
   }
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public Food getType(){
    return type;
  }

  public boolean near(int otherX, int otherY){
    return (Math.abs(x-otherX)<size && Math.abs(y-otherY)<size);
  }
  

  public void redraw(DrawingCanvas canvas){

    canvas.setForeground(color);
    canvas.fillRect(x-size, y-size, size*2, size*2, false);
  }
 
}

