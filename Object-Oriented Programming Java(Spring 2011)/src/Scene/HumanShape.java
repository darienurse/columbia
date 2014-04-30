package Scene;
import java.awt.*;
import java.awt.geom.*;

/**
   A house shape.
*/
public class HumanShape extends SelectableShape
{
   /**
      Constructs a house shape.
      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
   */
   public HumanShape(int x, int y, int width)
   {
      this.x = x;
      this.y = y;
      this.width = width;
   }

   public void draw(Graphics2D g2)
   {
	  int segement= width/6; 
      Rectangle2D.Double body = new Rectangle2D.Double(x, y, width, 2*width);
      Ellipse2D.Double head = new Ellipse2D.Double(x +(width/4), y-(width/1.5), width/1.5, width/1.5);
      Ellipse2D.Double lArm = new Ellipse2D.Double(x -(width/1.5), y+(width/4), width/1.5, width/4);
      Ellipse2D.Double rArm = new Ellipse2D.Double(x + width, y+(width/4), width/1.5, width/4);
      Ellipse2D.Double lLeg = new Ellipse2D.Double(x, y+(2*width), width/3, width);
      Ellipse2D.Double rLeg = new Ellipse2D.Double(x + (4*segement), y+(2*width), width/3, width);
      
      g2.draw(body);
      g2.draw(head);
      g2.draw(lArm);
      g2.draw(rArm);
      g2.draw(lLeg);
      g2.draw(rLeg);
   }
   
   public boolean contains(Point2D p)
   {
      return x <= p.getX() && p.getX() <= x + width 
         && y <= p.getY() && p.getY() <= y + 2 * width;
   }

   public void translate(int dx, int dy)
   {
      x += dx;
      y += dy;
   }

   private int x;
   private int y;
   private int width;
}
