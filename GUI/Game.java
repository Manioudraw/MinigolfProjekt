package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
<<<<<<< HEAD
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
=======
>>>>>>> parent of 38997ab (Soweit ganz gut :))


public class Game 
{
	MyPanel panel;
	
	Player playerPrimary;
	Player playerSecondary;
	Player currentPlayer;
	
	Game(MyPanel panel, Graphics2D g2D, ImageObserver observer, int ballX, int ballY)
	{
		this.panel = panel;
<<<<<<< HEAD
		playerPrimary = new Player(panel.getBall(), g2D, null, 1, panel.getSchlagzähler1(), ballX, ballY);
		playerSecondary = new Player(panel.getBall(), g2D, null, 2, panel.getSchlagzähler2(), ballX, ballY-10);
		currentPlayer = playerPrimary;
=======
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
>>>>>>> parent of 38997ab (Soweit ganz gut :))
	}
	
	public void gameloopStarten(Graphics2D g2D, MyPanel panel) throws InterruptedException, MalformedURLException
	{
<<<<<<< HEAD
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
=======
		Graphics2D g2D = (Graphics2D) g;
        Rectangle2D.Double free = new Rectangle2D.Double(0, 0, panel.getWidth(), panel.getHeight());
>>>>>>> parent of 38997ab (Soweit ganz gut :))
        
        g2D.setColor(Color.black);
        g2D.fill(free);
        g2D.draw(free);
        
        g2D.setColor(Color.white);
        g2D.setFont(new Font("DialogInput", Font.BOLD, 30));
        g2D.drawString("Spieler " + spielerNr +" ist Sieger!", (panel.getWidth() / 2) - 160, panel.getHeight() / 2);
        
        
        g2D.drawImage(konfetti, 0, 0, panel);
	}
	
<<<<<<< HEAD
	private Player playerTauschen() 
	{
        this.currentPlayer = (this.currentPlayer == playerPrimary) ? playerSecondary : playerPrimary;
        
        return currentPlayer;
=======
	private void playerTauschen() 
	{
        this.currentPlayer = (this.currentPlayer == playerPrimary) ? playerSecondary : playerPrimary;
>>>>>>> parent of 38997ab (Soweit ganz gut :))
    }
}
