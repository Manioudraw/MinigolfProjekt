package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener
{
	//Image backgoundImage;
	Image ball;
	Timer timer;
	//Variablen für die 2 Positionen der Maus, um die Linie zu zeichnen
	private int startX, startY, endX, endY;
	//Variablen für die Bewegung des Balls
	private int x = 30, y = 30, geschwX = 0, geschwY = 0;
	
	MyPanel()
	{
		//backgoundImage = new ImageIcon("Rasen.jpg").getImage();
		ball = new ImageIcon("BallSelbstgemacht.png").getImage();
		
		//Größe des Fensters
		this.setPreferredSize(new Dimension(1525, 785));
		this.setBackground(Color.lightGray);
		
		//Um die Mausposition abzufragen
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		//Wie oft soll der Ball neugezeichnet werden? -> Ballanimation
		timer = new Timer(80, this);
		timer.start();
	}
	
	/*
	 * paint()-Methode wird überschrieben und dafür genutzt, dass der Ball am Punkt P(x|y)
	 * gezeichnet wird, sowie die Linie, welche visualisiert, in welche Richtung und wie
	 * schnell sich der Ball bewegen soll.
	 */
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2D = (Graphics2D) g;
		
		//g2D.drawImage(backgoundImage, 0, 0, null);
		g2D.drawImage(ball, x, y, null);
		
		g2D.setPaintMode();
		g2D.setPaint(Color.white);
		g2D.setStroke(new BasicStroke(3));
		g2D.drawLine(startX, startY, endX, endY);
	}
	
	/*
	 * mousePressed()-Methode wird überschrieben und dafür genutzt, um die Startposition der 
	 * Linie zu definieren, indem die Position übergeben wird, an der die Maus geklickt wird.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		startX = e.getX();
		startY = e.getY();
	}
	
	/*
	 * mouseDragged()-Methode wird überschrieben und dafür genutzt, um die Endposition der 
	 * Linie zu definieren, indem die Position übergeben wird, an der die Maus gedrückt 
	 * gehalten wird.
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		endX = e.getX();
		endY = e.getY();
	}
	
	/*
	 * actionPerformed()-Methode wird überschrieben und dafür genutzt, um abzufragen, ob der
	 * Ball den Rand des Fensters erreicht. Wenn ja, dann wird die Bewegungsrichtung des Balls
	 * umgekehrt.
	 * 
	 * Ebenso wird die Methode repaint() aufgerufen, welche das Neuzeichnen triggert, sowie die
	 * neu erstellte Methode geschwReduzieren()
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(x>=1525-ball.getWidth(null) || x<0) 
		{
			geschwX = geschwX * -1;
		}
		
		x = x + geschwX;
		
		if(y>=785-ball.getHeight(null) || y<0) 
		{
			geschwY = geschwY * -1;
		}
		
		y = y + geschwY;
		
		geschwReduzieren();
		
		this.repaint();
	}
	
	/*
	 * Die neu erstellte Methode geschwReduzieren() sorgt dafür, dass der Ball in immer
	 * kürzerer Distanz neu gezeichnet wird, bis die Geschwindigkeit bei 0 ist.
	 */
	public void geschwReduzieren()
	{
		//Geschwindigkeit für x-Koordinate
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
		
		//Geschwindigkeit für y-Koordinate
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
	 * mouseReleased()-Methode wird überschrieben, um die Geschwindigkeit zu berechnen in 
	 * Abhängigkeit zu der Länge der Linie. 
	 * Ebenso wird überprüft, in welche Richtung die Linie gezogen ist, um gegebenfalls die 
	 * Geschwindigkeit umzukehren, wenn der Ball z.B. nach links geschlagen werden soll.
	 */
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		//Bewegung nach unten rechts
		if (endX > startX && endY > startY)
		{
			geschwX = (int) (Math.sqrt(endX-startX));
			geschwY = (int) (Math.sqrt(endY-startY));
		}
		//Bewegung nach oben links
		else if (endX < startX && endY < startY)
		{
			geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
		//Bewegung nach unten links
		else if (endX < startX && endY > startY)
		{
			geschwX = ((int) (Math.sqrt(startX-endX))) * -1;
			geschwY = ((int) (Math.sqrt(endY-startY)));
		}
		//Bewegung nach oben rechts
		else if (endX > startX && endY < startY)
		{
			geschwX = ((int) (Math.sqrt(endX-startX)));
			geschwY = ((int) (Math.sqrt(startY-endY))) * -1;
		}
	}
	
	/*
	 * Die folgenden Methoden müssen überschrieben werden. Jedoch werden diese (bisher) nicht
	 * benötigt, weshalb sie keinen Inhalt haben.
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
