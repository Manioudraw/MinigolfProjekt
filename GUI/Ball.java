package GUI;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class Ball 
{
	Image ball;
	int width;
	int height;
	//Variablen f�r die Bewegung des Balls
	public int x = 80, y = 130;
	//Variablen für Geschwindigkeit des Balls in Fließkommzahl für "genauere" Bewegung
	public double geschwX = 0.0, geschwY = 0.0;
	
	Ball()
	{
		ball = new ImageIcon("BallSelbstgemacht.png").getImage();
		
		width = ball.getWidth(null);
		height = ball.getHeight(null);
	}
	
	public void ballZeichnen(Graphics2D g2D)
	{
		g2D.drawImage(ball, x, y, null);
	}

	public void setKoords(int pX, int pY) {
		this.x = pX;
		this.y = pY;
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
