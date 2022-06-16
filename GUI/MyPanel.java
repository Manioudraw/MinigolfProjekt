package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.net.MalformedURLException;

import javax.swing.*;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	Timer timer;
	Scoreboard score;
	GUI.Game game = null;
	
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

	ArrayList<Bande> banden = new ArrayList<Bande>();
	ArrayList<Bande> löcher = new ArrayList<Bande>();
	
	public int getSchlagzaehler1() 
	{
		return schlagzähler1;
	}

	public int getSchlagzaehler2() 
	{
		return schlagzähler2;
	}
	
	public int getLochCounter() 
	{
		return this.game.getAktPlayer().lochCounter;
	}
	
	public int getJederSchlag() 
	{
		return jederSchlag;
	}
	
	
	MyPanel()
	{
		//Gr��e des Fensters
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
	public void paint(Graphics g)
	{
		super.paint(g);

        Graphics2D g2D = (Graphics2D) g;

         //neu > Game & damit auch Bälle erstellen
		if (game == null) {
			this.game = new GUI.Game(this);
		}
        
        //Bahn 1 erstellen
        this.banden.add(rechteckErstellen(20, 20, 500, 250, g)); //Bahn
        this.löcher.add(rundesRechteckErstellen(447, 130, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(0).x = 80;
		this.banden.get(0).y = 130;

        //Bahn 2 erstellen
        this.banden.add(rechteckErstellen(20, 300, 500, 400, g)); //Bahn
        this.löcher.add(rundesRechteckErstellen(140, 450, 250, 100, 90, 90, g, "wasser")); //TODO: Wasser
        this.löcher.add(rundesRechteckErstellen(447, 630, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(1).x = 80;
		this.banden.get(1).y = 330;

        //Bahn 3 erstellen
        this.banden.add(rechteckErstellen(550, 20, 200, 450, g)); //Bahnstück 1
        this.banden.get(2).second = rechteckErstellen(550, 280, 650, 210, g); //Bahnstück 2
        this.löcher.add(rundesRechteckErstellen(1130, 365, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(2).x = 650;
		this.banden.get(2).y = 60; // TODO: werte anpassen lol

        Rectangle2D.Double gras = new Rectangle2D.Double(570, 280, 160, 20); //Teil der Bande verkleinern
        g2D.setColor(new Color(144, 238, 144));
        g2D.fill(gras);
        g2D.draw(gras);

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
  		
  		if(game.winner == 1)
		{
			try 
			{
				game.winnerAnzeige(g2D, 1);
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			}
		}
		else if(game.winner == 2)
		{
			try 
			{
				game.winnerAnzeige(g2D, 2);
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
			} 
		}
	}
	
	public Bande rechteckErstellen(double x, double y, double height, double width, Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

        Rectangle2D.Double bande = new Rectangle2D.Double(x, y, height, width);
        Rectangle2D.Double gras = new Rectangle2D.Double(x+20, y+20, height-40, width-40);

        g2D.setColor(new Color(139, 125, 107));
        g2D.fill(bande);
        g2D.draw(bande);

        g2D.setColor(new Color(144, 238, 144));
        g2D.fill(gras);
        g2D.draw(gras);

		return new Bande(x, y, height, width, 'b');
    }

    public Bande rundesRechteckErstellen(double x, double y, double height, double width, double arcw, double arch, Graphics g, String s)
    {
        Graphics2D g2D = (Graphics2D) g;

        RoundRectangle2D.Double lochOderWasser = new RoundRectangle2D.Double(x, y, height, width, arcw, arch);

		Bande result = new Bande(x, y, height, width, 'l'); 

        if (s == "loch")
        {
            g2D.setColor(new Color(0, 139, 69));
        }
        else if (s == "wasser")
        {
            g2D.setColor(new Color(30, 144, 255));
			result.type = 'w';
        }

        g2D.fill(lochOderWasser);
        g2D.draw(lochOderWasser);

		return result;
    }

	public void nextCourse() 
	{
		int i = this.game.getAktPlayer().bahnCounter + 1;
		if (i > 2) {
			i = 0;
			this.game.gewonnen++;
		}

		Bande bande = this.banden.get( i );

		this.game.nextCourse(bande.x, bande.y);
	}
	
	/*
	 * mousePressed()-Methode wird �berschrieben und daf�r genutzt, um die Startposition der 
	 * Linie zu definieren, indem die Position �bergeben wird, an der die Maus geklickt wird.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		startX = e.getX();
		startY = e.getY();
	}
	
	/*
	 * mouseDragged()-Methode wird �berschrieben und daf�r genutzt, um die Endposition der 
	 * Linie zu definieren, indem die Position �bergeben wird, an der die Maus gedr�ckt 
	 * gehalten wird.
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
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
		if (this.banden.isEmpty() || this.löcher.isEmpty()) // "Wartet" bis alles fertig gerendert ist
		{ 
			return;
		}

		Ball ball = game.getAktPlayer().getBall();
		ball.x = (int) Math.floor(ball.x + ball.geschwX);
		ball.y = (int) Math.floor(ball.y + ball.geschwY);

		this.bounce();
		geschwReduzieren();
		this.isLoch();
		
		this.repaint();
	}

	private void bounce() {
		Ball ball = game.getAktPlayer().getBall();

		if(ball.x >= this.width - ball.width 
		|| ball.x < 0) {
			this.invert('x');
		} else if(ball.y >= this.width - ball.width 
		|| ball.y < 0) {
			this.invert('y');
		}

		Bande bande = this.banden.get(this.game.getAktPlayer().bahnCounter);
		double[] varianz = this.getVarianz(bande);

		if (this.isInside(bande, 'b')) {
			return;
		}
		
		if (bande.second != null) {
			// Logik für mehrteilige Banden
			double[] varianzSecond = this.getVarianz(bande.second);
			double[] varianzCombined = new double[8];

			if (this.isInside(bande.second, 'b')
			|| isInside(bande.second, 'b')) {
				return;
			}

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
			if (smallest == 8) {
				System.out.println("Smallest ist richtig!"); // TODO: Ist es nicht! (Zumindest für Second der letzten Bahn :)
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

	private void invert(char direction, Ball pBall) {
		if (direction == 'x') {
			pBall.geschwX *= -1;
		} else {
			pBall.geschwY *= -1;
		}
	}

	private double[] getVarianz(Bande bande) {
		double[] varianz = new double[4]; 
		Ball ball = game.getAktPlayer().getBall();

		varianz[0] = bande.startX - ball.x;
		varianz[1] = (bande.startX + bande.height) - ball.x - ball.width; // irgendwas wird hier falsch berechnet
		varianz[2] = bande.startY - ball.y;
		varianz[3] = (bande.startY + bande.width) - ball.y - ball.height; // irgendwas wird hier falsch berechnet
		for (int i = 0; i < varianz.length; i++) {
			if (varianz[i] < 0) {
				varianz[i] *= -1;
			}
		}
		return varianz;
	}

	private boolean isInside(Bande bande, char direction) {
		Ball ball = game.getAktPlayer().getBall();

		if (direction == 'x') {
			if (ball.x > bande.startX
			&& ball.x + ball.height < bande.startX + bande.height) {
				return true;
			}
		} else if (direction == 'y') {
			if (ball.y > bande.startY
			&& ball.y + ball.width < bande.startY + bande.width) {
				return true;
			}
		} else if (direction == 'b') {
			return isInside(bande, 'x') 
			&& isInside(bande, 'y');
		}
		return false;
	}

	// unnötige Methode, da Java keine optionalen Parameter unterstützt: ?!
	private void isLoch() 
	{
		this.isLoch(this.game.getAktPlayer().bahnCounter);
		this.isLoch(this.game.getAktPlayer().bahnCounter + 1); 
		// Bahn darf hierfür maximal aus einem Wasserloch bestehen!
		// Lässt sich aber einfach erweitern, also: while(Wasserloch){+1}; for(ergebnis der while){isLoch(for int)}
		
	}
	
	private void isLoch(int index) 
	{
		Bande loch = this.löcher.get(index);
		Ball ball = game.getAktPlayer().getBall();

		if (ball.y >= (int) loch.startY - ball.getWidth() && 
			ball.y <= (int) loch.startY + loch.width && 
			ball.x >= (int) loch.startX - ball.getHeight() && 
			ball.x <= (int) loch.startX + loch.height) 
		{
			if (loch.type == 'w') 
			{
				ball.x = this.banden.get(this.game.getAktPlayer().bahnCounter).x;
				ball.y = this.banden.get(this.game.getAktPlayer().bahnCounter).y;
				ball.geschwX = 0;
				ball.geschwY = 0;
				System.out.println("Ball wird zurückgesetzt");
			} 
			else 
			{
				this.nextCourse();
			}
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

		//Geschwindigkeit f�r x-Koordinate
		if (ball.geschwX > toleranz && ball.geschwY != 0)
		{
			ball.geschwX = ball.geschwX - langsamer;
		}
		else if (ball.geschwX < (toleranz * -1) && ball.geschwY != 0)
		{
			ball.geschwX = ball.geschwX + langsamer;
		}
		else
		{
			ball.geschwX = 0;
		}
		
		//Geschwindigkeit f�r y-Koordinate
		if (ball.geschwY > toleranz && ball.geschwX != 0)
		{
			ball.geschwY = ball.geschwY - langsamer;
		}
		else if (ball.geschwY < (toleranz * -1) && ball.geschwX != 0)
		{
			ball.geschwY = ball.geschwY + langsamer;
		}
		else
		{
			ball.geschwY = 0;
		}
	}
	
	//neu > Scoreboard & für Winning-Condition
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