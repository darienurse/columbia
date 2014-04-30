package Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ButtonTester 
{
	 public static void main(String[] args)
	   { 
		 JFrame frame = new JFrame();
		 final CircleIcon circle = new CircleIcon(100,Color.RED);
		 
		 
		 Button redb = new Button("red");
		 Button greenb = new Button("green");
		 Button blueb = new Button("blue");
		 
		 JButton red= redb.getButton();
		 JButton green= greenb.getButton();
		 JButton blue= blueb.getButton();
		 final JLabel theCircle = new JLabel(circle);
		 
		 
		 red.addActionListener (new ActionListener()
		 {
			 public void actionPerformed (ActionEvent event)
			 {
				 CircleIcon anotherCircle = new CircleIcon(100,Color.RED); 
				 theCircle.setIcon(anotherCircle);
			 }
		 });
		 
		 green.addActionListener (new ActionListener()
		 {
			 public void actionPerformed (ActionEvent event)
			 {
				 CircleIcon anotherCircle = new CircleIcon(100,Color.GREEN); 
				 theCircle.setIcon(anotherCircle);
			 }
		 });
		 
		 blue.addActionListener (new ActionListener()
		 {
			 public void actionPerformed (ActionEvent event)
			 {
				 CircleIcon anotherCircle = new CircleIcon(100,Color.BLUE); 
				 theCircle.setIcon(anotherCircle);
			 }
		 });
		
		 frame.setLayout(new FlowLayout());
		 frame.add(red);
		 frame.add(green);
		 frame.add(blue);
		 frame.add(theCircle);
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 frame.pack();
		 frame.setVisible(true);
	   }
}
