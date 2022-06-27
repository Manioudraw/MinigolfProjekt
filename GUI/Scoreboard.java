package GUI;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class Scoreboard extends JLayeredPane
{
	MyPanel panel;
	
	Scoreboard(MyPanel panel)
	{
		this.panel = panel;
	}
	
	public void scoreZeichnen(Graphics2D g2D, int width, int height, int schlagzähler1, int schlagzähler2, int spielerNr)
	{
        Rectangle2D.Double scoreAnzeige = new Rectangle2D.Double(1230, 330, 255, 45);
        
        g2D.setColor(Color.black);
        g2D.fill(scoreAnzeige);
        g2D.draw(scoreAnzeige);
        
        g2D.setColor(Color.white);
        g2D.setFont(new Font("DialogInput", Font.BOLD, 15));
        g2D.drawString("Schlaganzahl - Spieler " + spielerNr + ": " + schlagzähler1, 1230+5, 330+15);
        g2D.drawString("Schlaganzahl - Spieler " + (spielerNr + 1) + ": " + schlagzähler2, 1230+5, 330+35);

	}
}