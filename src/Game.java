package src;

import java.io.IOException;

public class Game{
    public static void main(String[] args) {
        System.out.println("Spiel gestartet!");
        test();
    }

    private static void test() {
        SpielFeld sp = new SpielFeld(20,10);
        sp.draw(0, 0, 5, 10, '0');
        sp.print();
        try {
            Leaderboard lb = new Leaderboard();
            lb.write("Peter");
            System.out.println("Peter hat so oft \"gewonnen\": " + lb.getWinCounterOrFile("Peter"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}