package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	Timer timer;
	Ball ball;
	Scoreboard score;
	Game game;
	
	//neu: Spieler - Winning-Condition
	boolean winning;
	int endstandOne = -1, endstandTwo = -1;
	int jederSchlag = 0;
	
	//Variablen f�r die 2 Positionen der Maus, um die Linie zu zeichnen
	private int startX, startY, endX, endY;
	//Variablen f�r die Bewegung des Balls
	private int x = 80, y = 130;
	//Variablen für Geschwindigkeit des Balls in Fließkommzahl für "genauere" Bewegung
	private double geschwX = 0.0, geschwY = 0.0;
	//Variablen für die Fenstergröße:
	private int width = 1525, height = 785, bahnCounter = 0, lochCounter = 0;
	
	//Scoreboard
	private int schlagzähler1 = 0, schlagzähler2 = 0;

	ArrayList<Bande> banden = new ArrayList<Bande>();
	ArrayList<Bande> löcher = new ArrayList<Bande>();
	
	
	//neu > Getter-Methoden
	public Ball getBall() 
	{
		return ball;
	}
	
	public int getSchlagzähler1() 
	{
		return schlagzähler1;
	}

	public int getSchlagzähler2() 
	{
		return schlagzähler2;
	}
	
	public int getLochCounter() 
	{
		return lochCounter;
	}
	
	public int getJederSchlag() 
	{
		return jederSchlag;
	}

	public int getWidth() 
	{
		return width;
	}
	
	public int getHeight() 
	{
		return height;
	}
	
	
	MyPanel()
	{
		ball = new Ball();
		
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
        this.banden.get(2).second = rechteckErstellen(550, 280, 650, 200, g); //Bahnstück 2
        this.löcher.add(rundesRechteckErstellen(1130, 365, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(2).x = 650;
		this.banden.get(2).y = 60; // TODO: werte anpassen lol

        Rectangle2D.Double gras = new Rectangle2D.Double(570, 280, 160, 20); //Teil der Bande verkleinern
        g2D.setColor(new Color(144, 238, 144));
        g2D.fill(gras);
        g2D.draw(gras);

        //spielfeldInBufferedImage();
        //g2D.drawImage(bufSpielfeld, null, 0, 0);

        //neu > Game & damit auch Bälle erstellen
        game = new Game(this, g2D, null, x, y);
        
        //Linie erstellen
        g2D.setPaintMode();
        g2D.setPaint(Color.white);
        g2D.setStroke(new BasicStroke(3));
        g2D.drawLine(startX, startY, endX, endY);
        
        //Scoreboard
        score = new Scoreboard(this);
  		score.scoreZeichnen(g2D, width, height, schlagzähler1, schlagzähler2, 1);
  		this.add(score);
  		
  		if(jederSchlag > 1)
  		{
  			try 
  			{
				game.gameloopStarten(g);
			} 
  			catch (InterruptedException e) 
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
		this.bahnCounter++;
		this.lochCounter++;
		
		if (this.bahnCounter > 2) 
		{
			this.bahnCounter = 0;
		}
		if (this.lochCounter > 3) 
		{
			this.lochCounter = 0;
		}
		
		// Teleportiert den Ball zum neuen Startpunkt:
		this.x = (int) this.banden.get(bahnCounter).x;
		this.y = (int) this.banden.get(bahnCounter).y;

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
		if (this.banden.isEmpty() || this.löcher.isEmpty()) // "Wartet" bis alles fertig gerendert ist
		{ 
			return;
		}

		if(this.invertX())
		{
			geschwX = geschwX * -1;
		}
		
		x = (int) Math.floor(x + geschwX);
		
		if(this.invertY()) 
		{
			geschwY = geschwY * -1;
		}

		y = (int) Math.floor(y + geschwY);
		
		geschwReduzieren();
		
		this.isLoch();
		
		this.repaint();
	}

	/* Die beiden folgenden Methoden kontrollieren, ob der Ball an der aktuellen Bahn eine Bande berührt
	* und gibt daraufhin den passenden Wahrheitswert zurück, ob sich die Bewegung invertieren muss. 
	*/
	private boolean invertX() 
	{
		return this.invertX(this.banden.get(this.bahnCounter));
	}
	
	private boolean invertX(Bande bande) 
	{
		// Fragt ab, ob der Ball das Fenster verlassen würde:
		if(x >= this.width - ball.getWidth() 
		|| x<0) 
		{
			return true;
		}

		// Bahnbegrenzungen:
		if (bande.second != null) 
		{
			if (isInside(bande, 'x') 
			&& isInside(bande, 'y')
			|| isInside(bande.second, 'x') 
			&& isInside(bande.second, 'y')) 
			{
				return false;
			} 
			else 
			{
				return true;
			}
		} 
		else 
		{
			return !isInside(bande, 'x');
		}
	}

	private boolean invertY() 
	{
		return this.invertY(this.banden.get(this.bahnCounter));
	} // TODO: Abfrage nach oben (#3, Bahnstück 2)! Problem: größer gleich???
	
	private boolean invertY(Bande bande) 
	{
		// Fragt ab, ob der Ball das Fenster verlassen würde:
		if(y >= this.height - ball.getHeight() || y<0) 
		{
			return true;
		} 

		// Bahnbegrenzungen:
		if (bande.second != null) 
		{
			if (isInside(bande, 'x') 
			&& isInside(bande, 'y')
			|| isInside(bande.second, 'x') 
			&& isInside(bande.second, 'y')) 
			{
				return false;
			} 
			else 
			{
				return true;
			}
		} 
		else 
		{
			return !isInside(bande, 'y');
		}
	}

	private boolean isInside(Bande bande, char direction) 
	{
		if (direction == 'x') 
		{
			if (x < bande.startX
			&& geschwX < 0) 
			{
				return false;
			} 
			else if (x + this.ball.getHeight() >= bande.startX + bande.height
			&& geschwX > 0) 
			{
				return false;
			}
		} 
		else if (direction == 'y') 
		{
			if (y < bande.startY
			&& geschwY < 0) 
			{
				return false; 
			} 
			else if (y + this.ball.getWidth() >= bande.startY + bande.width
			&& geschwY > 0) 
			{
				return false;
			}
		}
		return true;
	}

	// unnötige Methode, da Java keine optionalen Parameter unterstützt: ?!
	private void isLoch() 
	{
		this.isLoch(this.bahnCounter);
		this.isLoch(this.bahnCounter + 1); 
		// Bahn darf hierfür maximal aus einem Wasserloch bestehen!
		// Lässt sich aber einfach erweitern, also: while(Wasserloch){+1}; for(ergebnis der while){isLoch(for int)}
		
	}
	
	private void isLoch(int index) 
	{
		Bande loch = this.löcher.get(index);

		if (y >= (int) loch.startY - ball.getWidth() && 
			y <= (int) loch.startY + loch.width && 
			x >= (int) loch.startX - ball.getHeight() && 
			x <= (int) loch.startX + loch.height) 
		{
			if (loch.type == 'w') 
			{
				x = this.banden.get(this.bahnCounter).x;
				y = this.banden.get(this.bahnCounter).y;
				geschwX = 0;
				geschwY = 0;
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
		//Geschwindigkeit f�r x-Koordinate
		if (geschwX > toleranz && geschwY != 0)
		{
			geschwX = geschwX - langsamer;
		}
		else if (geschwX < (toleranz * -1) && geschwY != 0)
		{
			geschwX = geschwX + langsamer;
		}
		else
		{
			geschwX = 0;
		}
		
		//Geschwindigkeit f�r y-Koordinate
		if (geschwY > toleranz && geschwX != 0)
		{
			geschwY = geschwY - langsamer;
		}
		else if (geschwY < (toleranz * -1) && geschwX != 0)
		{
			geschwY = geschwY + langsamer;
		}
		else
		{
			geschwY = 0;
		}
	}
	
	//neu > Scoreboard & für Winning-Condition
	public void schlagzahlHochzählen()
	{
		//Scoreboard
		if(lochCounter != 3)
		{
			jederSchlag++;
			
			if(jederSchlag % 2 != 0)
			{
				schlagzähler1++;
			}
			else
			{
				schlagzähler2++;
			}
		}
		else
		{
			winning = true;
			
			endstandOne = schlagzähler1;
			endstandTwo = schlagzähler2;
			
			schlagzähler1 = -1;
			schlagzähler2 = -1;
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
