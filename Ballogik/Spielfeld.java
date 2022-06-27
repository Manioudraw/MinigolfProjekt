package Ballogik;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import GUI.Game;

public class Spielfeld {
	public ArrayList<Bande> banden = new ArrayList<Bande>();
	public ArrayList<Bande> löcher = new ArrayList<Bande>();
    
    Game game = null;

    public void init(Graphics2D g, Game p) {
        // malt die vorhandenen Banden neu ins Fenster (Verweis darauf mit Graphics2D)
        this.game = p;

        //Bahn 1 erstellen
        this.banden.add(rechteckErstellen(20, 20, 500, 250, g)); //Bahn
        this.löcher.add(rundesRechteckErstellen(447, 130, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(0).x = 80;
		this.banden.get(0).y = 130;

        //Bahn 2 erstellen
        this.banden.add(rechteckErstellen(20, 300, 500, 400, g)); //Bahn
        this.löcher.add(rundesRechteckErstellen(140, 450, 250, 100, 90, 90, g, "wasser")); //Wasser
        this.löcher.add(rundesRechteckErstellen(447, 630, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(1).x = 80;
		this.banden.get(1).y = 330;

        //Bahn 3 erstellen
        this.banden.add(rechteckErstellen(550, 20, 200, 450, g)); //Bahnstück 1
        this.banden.get(2).second = rechteckErstellen(550, 280, 650, 210, g); //Bahnstück 2
        this.löcher.add(rundesRechteckErstellen(1130, 365, 32, 32, 40, 40, g, "loch")); //Loch
		this.banden.get(2).x = 650;
		this.banden.get(2).y = 60;

        Rectangle2D.Double gras = new Rectangle2D.Double(570, 280, 160, 20); //Teil der Bande verkleinern
        g.setColor(new Color(144, 238, 144));
        g.fill(gras);
        g.draw(gras);
    }

    /* Die folgende Methode ist nötig, um die Varianz, also den Abstand des Balls zu allen
     * Außenkanten der aktuellen Bande herauszufinden.
     * So werden Sonderfälle abgefangen, an denen nicht eindutig ist, an welcher Achse der Ball abprallen soll, 
     * wenn es mehr, als ein Bahnstück gibt.
     */
	public double[] getVarianz(Bande bande) {
		double[] varianz = new double[4]; 
		Ball ball = game.getAktPlayer().getBall();

		varianz[0] = bande.startX - ball.x;
		varianz[1] = (bande.startX + bande.height) - ball.x - ball.getHeight();
		varianz[2] = bande.startY - ball.y;
		varianz[3] = (bande.startY + bande.width) - ball.y - ball.getWidth();
		for (int i = 0; i < varianz.length; i++) {
			if (varianz[i] < 0) {
				varianz[i] *= -1;
			}
		}
		return varianz;
	}

    /*
     * Gibt einen boolean zurück, der zeigt, ob sich der Ball innerhalb der Grenzen der aktuellen Bande befindet.
     */
	public boolean isInside(Bande bande, char direction) {
		Ball ball = game.getAktPlayer().getBall();

		if (direction == 'x') {
			if (ball.x > bande.startX
			&& ball.x + ball.getHeight() < bande.startX + bande.height) {
				return true;
			}
		} else if (direction == 'y') {
			if (ball.y > bande.startY
			&& ball.y + ball.getWidth() < bande.startY + bande.width) {
				return true;
			}
		} else if (direction == 'b') {
			return isInside(bande, 'x') 
			&& isInside(bande, 'y');
		}
		return false;
	}

    // Bringt den aktuellen Ball zum Startpunkt der nächsten Bande:
	public void nextCourse() {
		int i = this.game.getAktPlayer().bahnCounter + 1;
		if (i > 2) {
			i = 0;
			this.game.gewonnen++;
		}

		Bande bande = this.banden.get( i );

		this.game.nextCourse(bande.x, bande.y);
	}

	// Doppelte Methode, da Java keine optionalen Parameter unterstützt?!
	public void isLoch() {
		this.isLoch(this.game.getAktPlayer().bahnCounter);
		this.isLoch(this.game.getAktPlayer().bahnCounter + 1); 
		// Bahn darf hierfür maximal aus einem Wasserloch bestehen!
        // Lässt sich aber durch einen Zähler erweitern, ist bei uns aber nicht nötig
	}

	/*
     * Prüft, ob sich der aktuelle Ball in einem Loch befindet.
     * Wenn dies der Fall ist, wird geprüft, ob sich das Loch um ein Loch für die nächste Bahn handelt. 
     * In diesem Falle wird der Ball weiterteleportiert.
     * Wenn der Ball in ein Wasserloch gefallen ist, wird der Ball zum Startpunkt der aktuellen Bahn zurückteleportiert.
     */
	public void isLoch(int index) {
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
	
	private Bande rechteckErstellen(double x, double y, double height, double width, Graphics2D g2D) {
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

    private Bande rundesRechteckErstellen(double x, double y, double height, double width, double arcw, double arch, Graphics2D g2D, String s) {
        RoundRectangle2D.Double lochOderWasser = new RoundRectangle2D.Double(x, y, height, width, arcw, arch);

		Bande result = new Bande(x, y, height, width, 'l'); 

        if (s == "loch") {
            g2D.setColor(new Color(0, 139, 69));
        }
        else if (s == "wasser") {
            g2D.setColor(new Color(30, 144, 255));
			result.type = 'w';
        }

        g2D.fill(lochOderWasser);
        g2D.draw(lochOderWasser);

		return result;
    }
}
