package src;

public class Game{
    public static void main(String[] args) {
        System.out.println("Spiel gestartet!");
        //test();
        Leaderboard lb = new Leaderboard();
        AbstractPlayer[] ap = new AbstractPlayer[3];
        
        //lb.startGameWith();
    }

    private static void test() {
        try {
            Leaderboard lb = new Leaderboard();
            AbstractPlayer[] spieler = new Player[3]; 

            spieler[0] = new Player("Peter", 'p');
            spieler[1] = new Player("Gunther", 'g');
            spieler[2] = new Player("Herbert", 'h');

            lb.startGameWith(spieler);
            spieler[0]. setHits(5);
            spieler[1]. setHits(0);
            spieler[2]. setHits(2);
            lb.writeMultiple(spieler, new boolean[] {true,false,false});

            System.out.println("Was ein Chaos: ");
            if (!lb.getFile().isEmpty()) {
                System.out.println(lb.getItemsFromFile(spieler[0].getName()) [0]);
                // System.out.println(lb.getKD(spieler[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}