package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;


public class Game 
{
	MyPanel panel;
	
	Player playerPrimary;
	Player playerSecondary;
	Player currentPlayer;
	
	Game(MyPanel panel, Graphics2D g2D, ImageObserver observer, int ballX, int ballY)
	{
		this.panel = panel;
		playerPrimary = new Player(panel.getBall(), g2D, null, 1, panel.getSchlagzähler1(), ballX, ballY);
		playerSecondary = new Player(panel.getBall(), g2D, null, 2, panel.getSchlagzähler2(), ballX, ballY-10);
		currentPlayer = playerPrimary;
	}
	
	public void gameloopStarten(Graphics2D g2D, MyPanel panel) throws InterruptedException, MalformedURLException
	{
		if(panel.getSchlagzähler1() == -1)
		{
//			System.out.println("Spieler 1");
			winnerAnzeige(g2D, 1, panel);
		}
		else if(panel.getSchlagzähler2() == -1)
		{
//			System.out.println("Spieler 2");
			winnerAnzeige(g2D, 2, panel);
		}
		else
		{
			playerTauschen();
		}
	}
	
	private void winnerAnzeige(Graphics2D g2D, int spielerNr, MyPanel panel) throws MalformedURLException
	{
        Rectangle2D.Double free = new Rectangle2D.Double(0, 0, panel.getWidth(), panel.getHeight());
        Image konfetti = new ImageIcon(new URL("https://acegif.com/wp-content/gif/confetti-27.gif")).getImage();
        
        g2D.setColor(Color.black);
        g2D.fill(free);
        g2D.draw(free);
        
        g2D.setColor(Color.white);
        g2D.setFont(new Font("DialogInput", Font.BOLD, 30));
        g2D.drawString("Spieler " + spielerNr +" ist Sieger!", (panel.getWidth() / 2) - 160, panel.getHeight() / 2);
        
        
        g2D.drawImage(konfetti, 0, 0, panel);
	}
	
	private Player playerTauschen() 
	{
        this.currentPlayer = (this.currentPlayer == playerPrimary) ? playerSecondary : playerPrimary;
        
        return currentPlayer;
    }
}
