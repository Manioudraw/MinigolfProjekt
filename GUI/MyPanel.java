package GUI;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;

import javax.swing.*;

import Ballogik.*;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
	Timer timer;
	Scoreboard score;
	GUI.Game game = null;
	Spielfeld spiel = new Spielfeld();
	
	//neu: Spieler - Winning-Condition
	boolean winning;
	int endstandOne = -1, endstandTwo = -1;
	int jederSchlag = 0;
	
	//Variablen f�r die 2 Positionen der Maus, um die Linie zu zeichnen
	private int startX, startY, endX, endY;
	//Variablen für die Fenstergröße:
	private int width = 1525, height = 785;
	
	//Scoreboard
	private int schlagzähler1 = 0, schlagzähler2 = 0;
	
	public int getSchlagzaehler1() {
		return schlagzähler1;
	}

	public int getSchlagzaehler2() {
		return schlagzähler2;
	}
	
	public int getLochCounter() {
		return this.game.getAktPlayer().lochCounter;
	}
	
	public int getJederSchlag() {
		return jederSchlag;
	}
	
	
	public MyPanel() {
		//Größe des Fensters
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setBackground(new Color(255, 246, 143));
		
		//Um die Mausposition abzufragen
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		//Wie oft soll der Ball neugezeichnet werden? -> Ballanimation
		timer = new Timer(80, this);
		timer.start();
	}
	
	/*
	 * paint()-Methode wird �berschrieben und daf�r genutzt, dass der Ball am Punkt P(x|y)
	 * gezeichnet wird, sowie die Linie, welche visualisiert, in welche Richtung und wie
	 * schnell sich der Ball bewegen soll.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

         //neu > Game & damit auch Bälle erstellen
		if (game == null) {
			this.game = new GUI.Game(this);
		}
        
		spiel.init(g2D, this.game);

        //spielfeldInBufferedImage();
        //g2D.drawImage(bufSpielfeld, null, 0, 0);
		
        game.zeichne(g2D);
		
        //Linie erstellen
        g2D.setPaintMode();
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(startX, startY, endX, endY);
        
        //Scoreboard
        score = new Scoreboard(this);
  		score.scoreZeichnen(g2D, width, height, this.game.playerPrimary.schlagzaehler, this.game.playerSecondary.schlagzaehler, 1);
  		this.add(score);
  		
  		if(game.winner == 1
		|| game.winner == 2) {
			try {
				game.winnerAnzeige(g2D, game.winner);
			} 
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * mousePressed()-Methode wird �berschrieben und daf�r genutzt, um die Startposition der 
	 * Linie zu definieren, indem die Position �bergeben wird, an der die Maus geklickt wird.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
	}
	
	/*
	 * mouseDragged()-Methode wird �berschrieben und daf�r genutzt, um die Endposition der 
	 * Linie zu definieren, indem die Position �bergeben wird, an der die Maus gedr�ckt 
	 * gehalten wird.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		endX = e.getX();
		endY = e.getY();
	}
	
	/*
	 * actionPerformed()-Methode wird �berschrieben und daf�r genutzt, um abzufragen, ob der
	 * Ball den Rand des Fensters erreicht. Wenn ja, dann wird die Bewegungsrichtung des Balls
	 * umgekehrt.
	 * 
	 * Ebenso wird die Methode repaint() aufgerufen, welche das Neuzeichnen triggert, sowie die
	 * neu erstellte Methode geschwReduzieren()
	 * 
	 * Diese Methode wird zur Aktualisierung des Fensters aufgerufen.
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (game == null 
		|| spiel == null) // "Wartet" bis alles fertig gerendert ist
		{ 
			return;
		}

		Ball ball = game.getAktPlayer().getBall();
		ball.x = (int) Math.floor(ball.x + ball.geschwX);
		ball.y = (int) Math.floor(ball.y + ball.geschwY);

		this.bounce();
		geschwReduzieren();
		this.spiel.isLoch();
		
		this.repaint();
	}


	// Methode ist nötig für die Bandenkollision.
	// Auslagerung dieser Methode wäre sinnvoll, allerdings müssten dann auch die Parameter für die Bälle und Spielfelder übergeben werden.
	// Daher bleiben die folgenden Methode hier:
	private void bounce() {
		Ball ball = game.getAktPlayer().getBall();

		// Ist der Ball überhaupt im gesamten gezeichneten Fenster?
		if(ball.x >= this.width - ball.getWidth() 
		|| ball.x < 0) {
			this.invert('x');
		} else if(ball.y >= this.width - ball.getWidth() 
		|| ball.y < 0) {
			this.invert('y');
		}

		Bande bande = spiel.banden.get(this.game.getAktPlayer().bahnCounter);
		double[] varianz = spiel.getVarianz(bande);

		// Verschnellert den Prozess, wenn der Ball innerhalb der Banden ist:
		if (spiel.isInside(bande, 'b')) {
			return;
		}
		
		if (bande.second != null) {
			// Logik für mehrteilige Banden
			double[] varianzSecond = spiel.getVarianz(bande.second);
			double[] varianzCombined = new double[8];

			if (spiel.isInside(bande.second, 'b')
			|| spiel.isInside(bande.second, 'b')) {
				return;
			}

			// Mache aus zwei Arrays eins:
			for (int i = 0; i < varianz.length; i++) {
				varianzCombined[i] = varianz[i];
				varianzCombined[i + 4] = varianzSecond[i];
			}
			
			int smallest = 0;
			for (int i = 0; i < varianzCombined.length; i++) {
				if (varianzCombined[smallest] >= varianzCombined[i]) {
					smallest = i;
				}
			}
			// Smallest ist nun der integer, mit welchem man auf die Bande mit dem kleinsten Abstand zugreifen kann
			if (smallest == 8) {
				System.out.println("Smallest ist richtig!"); // Ist es nicht! (Zumindest für Second der letzten Bahn :/
				// Sollte hier printen, sobald der Ball im letzten Bahnstück rechts hinaus möchte.
				// Siehe Known Bugs in der Dokumentation
			}
			this.invert((int) smallest % 4);
		} else {
			// Logik für einfache Banden
			int smallest = 0;
			for (int i = 0; i < varianz.length; i++) {
				if (varianz[smallest] >= varianz[i]) {
					smallest = i;
				}
			}
			this.invert(smallest);
		}
	}

	// Kehrt die Geschwindigkeit in die gewünschte Richtung um
	// p=0: Linker Rand
	// p=1: Rechter Rand
	// p=2: Oberer Rand
	// p=3: Unterer Rand
	private void invert(int p) {
		Ball ball = game.getAktPlayer().getBall();
		switch (p) {
			case 0:
				if (ball.geschwX < 0) {
					this.invert('x', ball);
				}
				break;
			case 1:
				if (ball.geschwX > 0) {
					this.invert('x', ball);
				}
				break;
			case 2:
				if (ball.geschwY < 0) {
					this.invert('y', ball);
				}
				break;
			case 3:
				if (ball.geschwY > 0) {
					this.invert('y', ball);
				}
				break;
		}
	}

	// Hilfsmethode für die Obige um diese zu verkleinern und doppelten Code zu vermeiden:
	private void invert(char direction, Ball pBall) {
		if (direction == 'x') {
			pBall.geschwX *= -1;
		} else {
			pBall.geschwY *= -1;
		}
	}
	
	/*
	 * Die neu erstellte Methode geschwReduzieren() sorgt daf�r, dass der Ball in immer
	 * k�rzerer Distanz neu gezeichnet wird, bis die Geschwindigkeit bei 0 ist.
	 */
	public void geschwReduzieren()
	{
		double langsamer = 0.8;
		double toleranz = 1 - langsamer;
		Ball ball = game.getAktPlayer().getBall();

		//Geschwindigkeit für x-Koordinate
		if (ball.geschwX > toleranz && ball.geschwY != 0) {
			ball.geschwX = ball.geschwX - langsamer;
		}
		else if (ball.geschwX < (toleranz * -1) && ball.geschwY != 0) {
			ball.geschwX = ball.geschwX + langsamer;
		}
		else {
			ball.geschwX = 0;
		}
		
		//Geschwindigkeit für y-Koordinate
		if (ball.geschwY > toleranz && ball.geschwX != 0) {
			ball.geschwY = ball.geschwY - langsamer;
		}
		else if (ball.geschwY < (toleranz * -1) && ball.geschwX != 0) {
			ball.geschwY = ball.geschwY + langsamer;
		}
		else {
			ball.geschwY = 0;
		}
	}
	
	// Scoreboard & für Winning-Condition
	public void schlagzahlHochzählen()
	{
		this.game.getAktPlayer().schlagzaehler++;
	}
	
	/*
	 * mouseReleased()-Methode wird �berschrieben, um die Geschwindigkeit zu berechnen in 
	 * Abh�ngigkeit zu der L�nge der Linie. 
	 * Ebenso wird �berpr�ft, in welche Richtung die Linie gezogen ist, um gegebenfalls die 
	 * Geschwindigkeit umzukehren, wenn der Ball z.B. nach links geschlagen werden soll.
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		Ball ball = game.getAktPlayer().getBall();

		//Bewegung nach unten rechts
		if (endX >= startX && endY >= startY)
		{
			ball.geschwX = (int) (Math.sqrt(endX-startX));
			ball.geschwY = (int) (Math.sqrt(endY-startY));
		}
		//Bewegung nach oben links
		else if (endX <= startX && endY <= startY)
		{
			ball.geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			ball.geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
		//Bewegung nach unten links
		else if (endX <= startX && endY >= startY)
		{
			ball.geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			ball.geschwY = ((int) (Math.sqrt(endY-startY)));
		}
		//Bewegung nach oben rechts
		else if (endX >= startX && endY <= startY)
		{
			ball.geschwX = ((int) (Math.sqrt(endX-startX)));
			ball.geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
		
		schlagzahlHochzählen();
	}
	
	/*
	 * Die folgenden Methoden m�ssen �berschrieben werden. Jedoch werden diese (bisher) nicht
	 * ben�tigt, weshalb sie keinen Inhalt haben.
	 */
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	
}