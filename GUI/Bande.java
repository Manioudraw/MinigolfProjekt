package GUI;

public class Bande {
	public double startX, startY, height, width;
    public int x=0, y=0; // Startpunkt des Balles

    public Bande (double x, double y, double height, double width) {
        this.startX = x;
        this.startY = y;
        this.height = height;
        this.width = width;
    }

    public static void main(String[] args) {
        System.out.println("Henlo!");
    }

    public void setParams(int pStartX, int pStartY, int pEndX, int pEndY) {
        this.startX = pStartX;
        this.startY = pStartY;
        this.height = pEndX;
        this.width = pEndY;
    }
}
