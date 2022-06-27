package GUI;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import Ballogik.Ball;

public class Player 
{
	int spieleranzahl;
	int nr;
	int schlagzaehler;
	Ball ball;
	boolean aktiverSpieler;
	public int bahnCounter, lochCounter;

	Player (int nr) {
		this.ball = new Ball();
		this.nr = nr;
		this.schlagzaehler = 0;
		this.bahnCounter = 0;
		this.lochCounter = 0;
		this.aktiverSpieler = false;
	}
	
	Player(Ball ball, Graphics2D g2D, ImageObserver observer, int nr, int schlagzaehler, int x, int y)
	{
		this.ball = ball;
		this.nr = nr;
		this.schlagzaehler = schlagzaehler;
		this.bahnCounter = 0;
		this.lochCounter = 0;
		
		ball.ballZeichnen(g2D);
		this.aktiverSpieler = false;
	}

	public void zeichneBall(Graphics2D g2D) {
		ball.ballZeichnen(g2D);
	}

	public Ball getBall() {
		return this.ball;
	}
	
	public int getNr()
	{
		return nr;
	}

	public void nextCourse(int x, int y) {
		// Teleportiert den Ball zum neuen Startpunkt:
		this.ball.x = x;
		this.ball.y = y;

		this.ball.geschwX = 0;
		this.ball.geschwY = 0;

		this.bahnCounter++;
		this.lochCounter++;
		
		if (this.bahnCounter > 2) 
		{
			this.bahnCounter = 0;
		}
		if (this.lochCounter > 2) 
		{
			this.lochCounter = 0;
		}
	}

	public void toggleAktiverSpieler() {
		this.aktiverSpieler = !this.aktiverSpieler;
	}
}