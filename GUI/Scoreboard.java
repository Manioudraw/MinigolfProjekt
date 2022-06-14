package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Scoreboard extends JLayeredPane
{
	MyPanel panel;
	
	Scoreboard(MyPanel panel)
	{
		this.panel = panel;
	}
	
	public void listeErstellen(List schlaganzahlListe, int schlagzaehler1, int schlagzaehler2)
	{
		schlaganzahlListe.setLocation(1230, 330);
  		schlaganzahlListe.setSize(255, 45);
  		
  		schlaganzahlListe.setBackground(Color.black);
  		schlaganzahlListe.setForeground(Color.white);
  		schlaganzahlListe.setFont(new Font("DialogInput", Font.BOLD, 14));
  		
  		schlaganzahlListe.add("Schlaganzahl - Spieler " + 1 + ": " + schlagzaehler1);
  		schlaganzahlListe.add("Schlaganzahl - Spieler " + 2 + ": " + schlagzaehler2);
  		
	}
}
