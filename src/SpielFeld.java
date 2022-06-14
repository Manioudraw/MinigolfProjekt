package src;

public class SpielFeld {
    private char[][] field;
    private int length;
    private int width;
    private char barrier;


    public SpielFeld(int width, int length) {
        this.length = length;
        this.width = width;
        this.barrier = 'x';
        initField();
    }

    public void draw(int x1, int y1, int x2, int y2, char c) {
        if (x1 > length || x2 > length) {
            System.out.println("X Wert im Spielfeld zu hoch!");
        } else if (y1 > width || y2 > width) {
            System.out.println("Y Wert im Spielfeld zu hoch!");
        }

        for (int i=x1; i<x2; i++) {
            for (int j=y1; j<y2; j++) {
                if (field[i][j] == this.barrier) { // Keine Überschreibung der Hindernisse möglich!
                    continue;
                } else{
                    field[i][j] = c;
                }
            }
        }
    }

    private void initField() {
        field = new char[length][width];

        for(int i=0; i<length; i++) {
            for (int u=0; u<width; u++) {
                if (i==0 || u==0 || i==length-1 || u==width-1) { // Umrandung (optional)
                    field[i][u] = this.barrier; 
                } else {
                    field[i][u] = ' ';
                }
            }
        }
    }

    public void print() {
        for (int i=0; i<field.length; i++) {
            System.out.println(field[i]);
        }
    }
}
