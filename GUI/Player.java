package GUI;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class Player 
{
	int spieleranzahl;
	int nr;
	int schlagzaehler;
	Ball ball;
	
	Player(Ball ball, Graphics2D g2D, ImageObserver observer, int nr, int schlagzaehler, int x, int y)
	{
		this.ball = ball;
		this.nr = nr;
		this.schlagzaehler = schlagzaehler;
		
		ball.ballZeichnen(g2D, x, y, observer);
	}
	
	public int getNr()
	{
		return nr;
	}
}
