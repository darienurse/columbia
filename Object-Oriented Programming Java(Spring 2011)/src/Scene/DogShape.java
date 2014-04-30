package Scene;
import java.awt.*;
import java.awt.geom.*;

/**
   A house shape.
*/
public class DogShape extends SelectableShape
{
   /**
      Constructs a house shape.
      @param x the left of the bounding rectangle
      @param y the top of the bounding rectangle
      @param width the width of the bounding rectangle
   */
   public DogShape(int x, int y, int width)
   {
      this.x = x;
      this.y = y;
      this.width = width;
   }

   public void draw(Graphics2D g2)
   {
	  int segement= (2*width)/6; 
      Rectangle2D.Double body = new Rectangle2D.Double(x, y, 1.5*width, width);
      Ellipse2D.Double head = new Ellipse2D.Double(x + width, y-(width/1.5), width/1.5, width/1.5);
      Ellipse2D.Double leg1 = new Ellipse2D.Double(x +(0*segement), y + width, width/4, width/4);
      Ellipse2D.Double leg2 = new Ellipse2D.Double(x +(1*segement), y + width, width/4, width/4);
      Ellipse2D.Double leg3 = new Ellipse2D.Double(x +(3*segement), y + width, width/4, width/4);
      Ellipse2D.Double leg4 = new Ellipse2D.Double(x +(4*segement), y + width, width/4, width/4);
      Ellipse2D.Double tail = new Ellipse2D.Double(x -(width/1.5), y, width/1.5, width/4);
      
      g2.draw(body);
      g2.draw(head);
      g2.draw(leg1);
      g2.draw(leg2);
      g2.draw(leg3);
      g2.draw(leg4);
      g2.draw(tail);
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
