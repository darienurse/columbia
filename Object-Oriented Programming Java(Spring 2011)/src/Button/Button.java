package Button;
import javax.swing.JButton;

/**
This class creates Buttons
*/
public class Button 
{
	public Button(String aColor)
	   {
	      color = aColor;
	   }
	
	public JButton getButton()
	   {
	      JButton b = new JButton(color);
	      return b; 
	   }
	
	private String color;
}
