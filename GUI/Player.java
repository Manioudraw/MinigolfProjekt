package GUI;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class Player 
{
	int spieleranzahl;
	int nr;
	int schlagz�hler;
	Ball ball;
	
	Player(Ball ball, Graphics2D g2D, ImageObserver observer, int nr, int schlagz�hler, int x, int y)
	{
		this.ball = ball;
		this.nr = nr;
		this.schlagz�hler = schlagz�hler;
		
		ball.ballZeichnen(g2D, x, y, observer);
	}
	
	public int getNr()
	{
		return nr;
	}
}
