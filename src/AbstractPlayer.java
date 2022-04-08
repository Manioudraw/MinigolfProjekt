package src;

abstract class AbstractPlayer {
    private char id;
    
    abstract void setMove();

    public char getID() {
        return this.id;
    }

    public void setID(char c) {
        this.id = c;
    }
}
