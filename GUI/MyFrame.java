package GUI;

// import java.awt.Component;

import javax.swing.*;

public class MyFrame extends JFrame
{
	MyPanel panel;
	
	MyFrame()
	{
		panel = new MyPanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(panel);
		//Pack wird ben�tigt, um das Fenster zu kreieren.
		this.pack();
		//Fenster wird mittig ge�ffnet.
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
