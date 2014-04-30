package Button;
	import java.awt.*;
	import java.awt.geom.*;
import javax.swing.*;

	 /**
	   An icon that has the shape of a circle.
	*/
	 public class CircleIcon implements Icon
	 {
	   /**
	       Constructs a Circle icon of a given size.
	       @param aSize the size of the icon
	 * @return 
	    */
	    public CircleIcon(int aSize, Color aColor)
	    {
	       size = aSize;
	       theColor=aColor;
	    }
	
	    public int getIconWidth()
	    {
	       return size;
	   }
	
	   public int getIconHeight()
	  {
	      return size;
	   }
	
	   public void paintIcon(Component c, Graphics g, int x, int y)
	  {
		  Graphics2D g2 = (Graphics2D) g;
	      Ellipse2D.Double planet = new Ellipse2D.Double(x, y,
	         size, size);
	      g2.setColor(theColor);
	      g2.fill(planet);
	  }
	
	   private int size;
	   private Color theColor;
	 }

