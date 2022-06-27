package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class Game
{
	MyPanel panel;
	
	Player playerPrimary;
	Player playerSecondary;
	public int gewonnen;
	
	int winner = 0;

	public Game (MyPanel panel) {
		this.panel = panel;
		playerPrimary = new Player(1);
		playerSecondary = new Player(2);
		playerPrimary.toggleAktiverSpieler();
		this.gewonnen = 0;
	}
	
	public void winnerAnzeige(Graphics2D g2D, int spielerNr) throws MalformedURLException
	{
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask()
				{
					@Override
					public void run()
					{
						winner = 0;
					}
				};
			
		Rectangle2D.Double free = new Rectangle2D.Double(0, 0, panel.getWidth(), panel.getHeight());
	    Image konfetti = new ImageIcon(new URL("https://acegif.com/wp-content/gif/confetti-27.gif")).getImage();
	
	    g2D.setColor(Color.black);
	    g2D.fill(free);
	    g2D.draw(free);
	    
	    g2D.setColor(Color.white);
	    g2D.setFont(new Font("DialogInput", Font.BOLD, 30));
	    g2D.drawString("Spieler " + spielerNr +" ist Sieger!", (panel.getWidth() / 2) - 160, panel.getHeight() / 2);
	    
	    g2D.drawImage(konfetti, 0, 0, panel);
	    
	    timer.schedule(task, 5000); //Endscreen verschwindet nach 5 Sekunden wieder
	}
	
	public void nextCourse(int x, int y) {
		if(this.playerPrimary.bahnCounter == 2 || this.playerSecondary.bahnCounter == 2) {
			if (this.gewonnen == 2) {
				if (this.playerPrimary.schlagzaehler < this.playerSecondary.schlagzaehler) {
					winner = 1;
					System.out.println("Spieler eins hat gewonnen! " + this.playerPrimary.schlagzaehler + ": " + this.playerSecondary.schlagzaehler);
				} else {
					winner = 2;
					System.out.println("Spieler zwei hat gewonnen! " + this.playerPrimary.schlagzaehler + ": " + this.playerSecondary.schlagzaehler);
				}
				this.gewonnen = 0;
				this.playerPrimary.schlagzaehler = 0;
				this.playerSecondary.schlagzaehler = 0;
			} 
		}
		this.getAktPlayer().nextCourse(x, y);

        this.playerPrimary.toggleAktiverSpieler();
		this.playerSecondary.toggleAktiverSpieler();
    }

	public void zeichne(Graphics2D g2D) {
		this.playerPrimary.zeichneBall(g2D);
		this.playerSecondary.zeichneBall(g2D);
	}

	public Player getAktPlayer() {
		if (playerPrimary.aktiverSpieler) {
			return playerPrimary;
		} else {
			return playerSecondary;
		}
	}
}