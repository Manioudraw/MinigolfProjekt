package GUI;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class Ball 
{
	Image ball;
	int width;
	int height;
	
	Ball()
	{
		ball = new ImageIcon("BallSelbstgemacht.png").getImage();
		
		width = ball.getWidth(null);
		height = ball.getHeight(null);
	}
	
	public void ballZeichnen(Graphics2D g2D, int x, int y, ImageObserver observer)
	{
		g2D.drawImage(ball, x, y, observer);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
