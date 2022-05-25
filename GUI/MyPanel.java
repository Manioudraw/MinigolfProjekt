package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	//Image backgoundImage;
	Image ball;
	Timer timer;
	//Variablen f�r die 2 Positionen der Maus, um die Linie zu zeichnen
	private int startX, startY, endX, endY;
	//Variablen f�r die Bewegung des Balls
	private int x = 80, y = 130, geschwX = 0, geschwY = 0;
	//Variablen für die Fenstergröße:
	private int width = 1525, height = 785, bahnCounter=0;
	
	ArrayList<Bande> banden = new ArrayList<Bande>();
	ArrayList<Bande> löcher = new ArrayList<Bande>();
	
	MyPanel()
	{
		//backgoundImage = new ImageIcon("Rasen.jpg").getImage();
		ball = new ImageIcon("BallSelbstgemacht.png").getImage();
		
		//Gr��e des Fensters
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setBackground(new Color(255,246,143));
		
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


        //Bahn 1 erstellen
        rechteckErstellen(20, 20, 500, 250, g); //Bahn
        rundesRechteckErstellen(447, 130, 32, 32, 40, 40, g, "loch"); //Loch
		this.banden.get(0).x = 80;
		this.banden.get(0).y = 130;

        //Bahn 2 erstellen
        rechteckErstellen(20, 300, 500, 400, g); //Bahn
        rundesRechteckErstellen(447, 630, 32, 32, 40, 40, g, "loch"); //Loch
        rundesRechteckErstellen(140, 450, 250, 100, 90, 90, g, "wasser"); //Wasser
		this.banden.get(1).x = 80;
		this.banden.get(1).y = 330;

        //Bahn 3 erstellen
        rechteckErstellen(550, 20, 200, 450, g); //Bahnstück 1
        rechteckErstellen(550, 280, 650, 200, g); //Bahnstück 2
        rundesRechteckErstellen(1130, 365, 32, 32, 40, 40, g, "loch"); //Loch
		this.banden.get(2).x = 650;
		this.banden.get(2).y = 60; // TODO: werte anpassen lol

        Rectangle2D.Double gras = new Rectangle2D.Double(570, 280, 160, 20); //Teil der Bande verkleinern
        g2D.setColor(new Color(144, 238, 144));
        g2D.fill(gras);
        g2D.draw(gras);

        //spielfeldInBufferedImage();
        //g2D.drawImage(bufSpielfeld, null, 0, 0);

        //Ball erstellen
        g2D.drawImage(ball, x, y, null);


        //Linie erstellen
        g2D.setPaintMode();
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(startX, startY, endX, endY);
	}

	public void rechteckErstellen(double x, double y, double height, double width, Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;

		this.banden.add(new Bande(x, y, height, width));

        Rectangle2D.Double bande = new Rectangle2D.Double(x, y, height, width);
        Rectangle2D.Double gras = new Rectangle2D.Double(x+20, y+20, height-40, width-40);

        g2D.setColor(new Color(139, 125, 107));
        g2D.fill(bande);
        g2D.draw(bande);

        g2D.setColor(new Color(144, 238, 144));
        g2D.fill(gras);
        g2D.draw(gras);
    }

    public void rundesRechteckErstellen(double x, double y, double height, double width, double arcw, double arch, Graphics g, String s)
    {
        Graphics2D g2D = (Graphics2D) g;

        RoundRectangle2D.Double lochOderWasser = new RoundRectangle2D.Double(x, y, height, width, arcw, arch);

        if (s == "loch")
        {
			this.löcher.add(new Bande(x, y, height, width));
            g2D.setColor(new Color(0, 139, 69));
        }
        else if (s == "wasser")
        {
            g2D.setColor(new Color(30, 144, 255));
        }

        g2D.fill(lochOderWasser);
        g2D.draw(lochOderWasser);
    }


	public void nextCourse() {
		this.bahnCounter++;
		if (this.bahnCounter > 2) {
			this.bahnCounter = 0;
		}
		// Teleportiert den Ball zum neuen Startpunkt:
		this.x = this.banden.get(bahnCounter).x;
		this.y = this.banden.get(bahnCounter).y;

		geschwX = 0;
		geschwY = 0;
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
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(this.invertX())
		{
			geschwX = geschwX * -1;
		}
		
		x = x + geschwX;
		
		if(this.invertY()) 
		{
			geschwY = geschwY * -1;
		}

		y = y + geschwY;
		
		geschwReduzieren();
		
		this.isLoch();
		
		this.repaint();
	}

	/* Die beiden folgenden Methoden kontrollieren, ob der Ball an der aktuellen Bahn eine Bande berührt
	* und gibt daraufhin den passenden Wahrheitswert zurück, ob sich die Bewegung invertieren muss. 
	*/
	private boolean invertX() {
		// Fragt ab, ob der Ball das Fenster verlassen würde:
		if(x >= this.width - ball.getWidth(null) || x<0) {
			return true;
		} 
		
		// Bahnbegrenzungen:
		if (x <= this.banden.get(this.bahnCounter).startX + 20) {
			return true;
		} else if (x >= this.banden.get(this.bahnCounter).startX + this.banden.get(this.bahnCounter).height - 40) {
			return true;
		}
		
		return false;
	}
	private boolean invertY() {
		// Fragt ab, ob der Ball das Fenster verlassen würde:
		if(y >= this.width - ball.getWidth(null) || x<0) {
			return true;
		} 
		
		// TODO: Bugfixxen
		if (y <= this.banden.get(this.bahnCounter).startY + 20) {
			return true;
		} else if (y >= this.banden.get(this.bahnCounter).startY + this.banden.get(this.bahnCounter).width - 40) {
			return true;
		}

		return false;
	}
	private void isLoch() {
		int toleranz = 5;
		if (y >= this.löcher.get(this.bahnCounter).startY - this.löcher.get(this.bahnCounter).width && 
			y <= this.löcher.get(this.bahnCounter).startY + toleranz &&
			x >= this.löcher.get(this.bahnCounter).startX - this.löcher.get(this.bahnCounter).height && 
			x <= this.löcher.get(this.bahnCounter).startX + toleranz) {
			this.nextCourse();
		}

	}
	
	
	/*
	 * Die neu erstellte Methode geschwReduzieren() sorgt daf�r, dass der Ball in immer
	 * k�rzerer Distanz neu gezeichnet wird, bis die Geschwindigkeit bei 0 ist.
	 */
	public void geschwReduzieren()
	{
		//Geschwindigkeit f�r x-Koordinate
		if (geschwX > 0 && geschwY != 0)
		{
			geschwX = geschwX - 1;
		}
		else if (geschwX < 0 && geschwY != 0)
		{
			geschwX = geschwX + 1;
		}
		else
		{
			geschwX = 0;
		}
		
		//Geschwindigkeit f�r y-Koordinate
		if (geschwY > 0 && geschwX != 0)
		{
			geschwY = geschwY - 1;
		}
		else if (geschwY < 0 && geschwX != 0)
		{
			geschwY = geschwY + 1;
		}
		else
		{
			geschwY = 0;
		}
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
		//Bewegung nach unten rechts
		if (endX >= startX && endY >= startY)
		{
			geschwX = (int) (Math.sqrt(endX-startX));
			geschwY = (int) (Math.sqrt(endY-startY));
		}
		//Bewegung nach oben links
		else if (endX <= startX && endY <= startY)
		{
			geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
		//Bewegung nach unten links
		else if (endX <= startX && endY >= startY)
		{
			geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			geschwY = ((int) (Math.sqrt(endY-startY)));
		}
		//Bewegung nach oben rechts
		else if (endX >= startX && endY <= startY)
		{
			geschwX = ((int) (Math.sqrt(endX-startX)));
			geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
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
