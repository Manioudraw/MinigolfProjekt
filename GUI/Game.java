package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Game 
{
	MyPanel panel;
	
	Player playerPrimary;
	Player playerSecondary;
	int gewonnen;

	Graphics2D g2D;

	public Game (Graphics2D g2D, MyPanel panel) {
		this.g2D = g2D;
		this.panel = panel;
		playerPrimary = new Player(1);
		playerSecondary = new Player(2);
		playerPrimary.toggleAktiverSpieler();
		this.gewonnen = 0;
	}
	
	private void winnerAnzeige(Graphics g, int spielerNr)
	{
		Graphics2D g2D = (Graphics2D) g;
        Rectangle2D.Double free = new Rectangle2D.Double(0, 0, this.panel.getWidth(), this.panel.getHeight());
        
        g2D.setColor(Color.black);
        g2D.fill(free);
        g2D.draw(free);
        
        g2D.setColor(Color.white);
        g2D.setFont(new Font("DialogInput", Font.BOLD, 30));
        g2D.drawString("Spieler " + spielerNr +" ist Sieger!", (panel.getWidth() / 2) - 160, panel.getHeight() / 2);
	}
	
	public void nextCourse(int x, int y) {
		if(this.playerPrimary.bahnCounter == 2 || this.playerSecondary.bahnCounter == 2) {
			if (this.gewonnen == 2) {
				if (this.playerPrimary.schlagzaehler < this.playerSecondary.schlagzaehler) {
					winnerAnzeige(g2D, 1);
					System.out.println("Spieler eins hat gewonnen! " + this.playerPrimary.schlagzaehler + ": " + this.playerSecondary.schlagzaehler);
				} else {
					winnerAnzeige(g2D, 2);
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
