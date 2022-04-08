package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Leaderboard {
    private String fileName;

    public Leaderboard(String fileName) {
        this.fileName = fileName;
    }

    public Leaderboard() {
        this.fileName = "LocalLeaderBoardFile.txt";
    }
    
    public String getWinCounterOrFile(String playerString) throws IOException {
    // returns a) the complete content of the file (if playerString is empty)
    // or b) the number of previous wins of the player
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));

            if (!playerString.equals("")){
            // get number of previous wins
                // complete txt file:
                String readLB = this.getWinCounterOrFile("");
                // current row in file:
                String[] currentLine = readLB.split("\n");
    
                for (int i=0; i<currentLine.length; i++) {
                    if (currentLine[i].contains(playerString)) {
                        // Current Line splitted into an Array to differentiate between name and win counter:
                        String[] splitLine = currentLine[i].split(":");
                        // Checks if its really an Integer:
                        int winCounter = Integer.parseInt(splitLine[1].substring(1));
                        reader.close();
                        return String.valueOf(winCounter);
                    } else {
                        result += currentLine[i] + "\n";
                    }
                }
            } else {
            // get full content of the file                
                String nextLine = reader.readLine();
                while (nextLine != null) {
                    result += nextLine + "\n";
                    nextLine = reader.readLine();
                } 
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void write(String str) throws IOException {
        String readLB = this.getWinCounterOrFile("");
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        if (readLB.contains(str)) {
            String[] currentLine = readLB.split("\n");
            String result = "";

            for (int i=0; i<currentLine.length; i++) {
                String[] splitLine = currentLine[i].split(":");

                if (splitLine[0].equals(str)) {
                    int wonCounter = Integer.parseInt(splitLine[1].substring(1)) + 1;
                    result += str + ": " + wonCounter + "\n";
                } else {
                    result += currentLine[i] + "\n";
                }
            }
            writer.write(result);
            writer.close();
        } else {
            writer.write(readLB + str + ": 1");
            writer.close();
        }
    }

    // TODO: K/D ratio
    // TODO: Farbe, char,... an Namen verknüpfen
}
