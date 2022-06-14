package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;


public class Game 
{
	MyPanel panel;
	
	Player playerPrimary;
	Player playerSecondary;
	Player currentPlayer;
	
	Game(MyPanel panel, Graphics2D g2D, ImageObserver observer, int ballX, int ballY)
	{
		this.panel = panel;
		playerPrimary = new Player(panel.getBall(), g2D, null, 1, panel.getSchlagzaehler1(), ballX, ballY);
		playerSecondary = new Player(panel.getBall(), g2D, null, 2, panel.getSchlagzaehler2(), ballX, ballY-10);
		currentPlayer = playerPrimary;
	}
	
	public void gameloopStarten(Graphics g) throws InterruptedException
	{
		if(panel.getSchlagzaehler1() == -1)
		{
//			System.out.println("Spieler 1");
			winnerAnzeige(g, 1);
		}
		else if(panel.getSchlagzaehler2() == -1)
		{
//			System.out.println("Spieler 2");
			winnerAnzeige(g, 2);
		}
		else
		{
			playerTauschen();
		}
	}
	
	private void winnerAnzeige(Graphics g, int spielerNr)
	{
		Graphics2D g2D = (Graphics2D) g;
        Rectangle2D.Double free = new Rectangle2D.Double(0, 0, panel.getWidth(), panel.getHeight());
        
        g2D.setColor(Color.black);
        g2D.fill(free);
        g2D.draw(free);
        
        g2D.setColor(Color.white);
        g2D.setFont(new Font("DialogInput", Font.BOLD, 30));
        g2D.drawString("Spieler " + spielerNr +" ist Sieger!", (panel.getWidth() / 2) - 160, panel.getHeight() / 2);
	}
	
	private void playerTauschen() 
	{
        this.currentPlayer = (this.currentPlayer == playerPrimary) ? playerSecondary : playerPrimary;
    }
}
