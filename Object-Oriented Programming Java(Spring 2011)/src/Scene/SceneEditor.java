package Scene;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
A program that allows users to edit a scene composed of items.
*/
public class SceneEditor
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final SceneComponent scene = new SceneComponent();
		
		JButton houseButton = new JButton("House");
		houseButton.addActionListener(new
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					scene.add(new HouseShape(50, 50, 50));
				}
			});
		
		JButton humanButton = new JButton("Human");
		humanButton.addActionListener(new
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					scene.add(new HumanShape(50, 50, 50));
				}
			});
		
		JButton carButton = new JButton("Dog");
		carButton.addActionListener(new
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					scene.add(new DogShape(50, 50, 50));
				}
			});
		
		
		JButton removeButton = new JButton("Remove");
		removeButton.addActionListener(new
			ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					scene.removeSelected();
				}
			});
		
		JPanel buttons = new JPanel();
		buttons.add(houseButton);
		buttons.add(humanButton);
		buttons.add(carButton);
		buttons.add(removeButton);
		
		frame.add(scene, BorderLayout.CENTER);
		frame.add(buttons, BorderLayout.NORTH);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}
