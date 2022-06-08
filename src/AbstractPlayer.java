package src;

abstract class AbstractPlayer {
    private char id; // Eindeutige Zuordnung
    private String[] values; // Gesammelter Speicher für Daten aus Datei
    protected String name;
    private int strokes; // Im aktuellen Spiel benötigte Schläge TODO: mit value speicherung verbinden
    private Leaderboard lb; // Nicht nötig, aber doppelte Instanz damit hier ausgeschlossen

    public AbstractPlayer() {
        this.lb = new Leaderboard();
        this.strokes = 0;
        this.values = this.lb.getItemsFromFile(name);
    }
    
    abstract void setMove();

    public void addStroke(){
        this.strokes++;
    }

    public int getStrokes() {
        return this.strokes;
    }

    public void setHits(int hits) { // TODO: testen
        // Gebe dem Spieler die aktuell gebrauchte Anzahl an Schlägen mit
        // Rest wird aus der Datei gelesen
        
        Leaderboard lb = new Leaderboard();
        if (this.values == null) {
                String[] dat;
                dat = lb.getItemsFromFile(this.name);    
                this.values = lb.getNewTuple(dat);
        } else {
            String[] dat = new String[4];
            for (int i=0; i<4; i++) {
                dat[i] = this.values[i];
            }
            dat[3] = String.valueOf(hits);
            this.values = lb.getNewTuple(dat);
        }
    }

    public void setValues(String[] val){
        this.values = val;
    }

    public String[] getValues() {
        String[] result = new String[this.values.length];
        for (int i=0; i<result.length; i++) {
            result [i] = String.valueOf( this.values[i] );
        }
        return result;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getName() {
        return this.name;
    }

    public char getID() {
        return this.id;
    }

    public void setID(char c) {
        this.id = c;
    }
}
