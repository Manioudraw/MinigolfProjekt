package GUI;

public class Bande 
{
	public double startX, startY, height, width;
    public int x=0, y=0; // Startpunkt des Balles
    public char type = 'b';
    public Bande second=null;

    public Bande (double x, double y, double height, double width, char type) 
    {
        int add;
        if (type == 'w')        { add = 0; }
        else if (type == 'l')   { add = 2; }
        else                    { add = 20; }
        this.startX = x + add; // +20, da optischer Rahmen
        this.startY = y + add;
        this.height = height - add * 2;
        this.width = width - add * 2;
    }

    public static void main(String[] args) 
    {
        System.out.println("Henlo!");
    }
}
