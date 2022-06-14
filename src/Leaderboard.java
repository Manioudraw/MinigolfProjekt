package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Leaderboard {
    private String fileName;
    private AbstractPlayer[] aktivePlayers;

    public Leaderboard(String fileName) {
        this.fileName = fileName;
    }

    public Leaderboard() {
        this.fileName = "LeaderBoardFile.csv";
    }

    public void startGameWith(AbstractPlayer[] names) {
        this.aktivePlayers = names;
    }

    public ArrayList<String> getFile(){
        ArrayList<String> result = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getItemsFromFile(String name) {
        ArrayList<String> file = this.getFile();
        for (int i=0; i<file.size(); i++) {
            if (file.get(i).contains(name)) {
                return file.get(i).split(";");
            }
        }
        return null;
    }

    public boolean writeMultiple(AbstractPlayer[] ap, boolean[] winnerIs) {
        ArrayList<String> file = this.getFile();
        AbstractPlayer p;

        for (int i=0; i<ap.length; i++) {
            p = ap[i];    
            if (file.contains(p.getName())) {
                this.writeToFile(p, winnerIs[i], p.getStrokes());
            } else {
                
            }
        }
        
        return false;
    }

    public boolean writeToFile(AbstractPlayer spieler, boolean won, int hits) {
        ArrayList<String> file = this.getFile();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName));

            if (file.isEmpty()) {
                System.out.print("Datei ist leer, wird jetzt aber gefüllt!");
                writer.write(this.getNewString(spieler.getName(), won, hits));
                writer.close();
                return true;
            }
            
            for (int i=0; i<file.size()-1; i++) {
                System.out.println(file.get(i));
                if (file.get(i).contains(spieler.getName())) {
                    String[] arr = file.get(i).split(";");
                    int winCounter = Integer.parseInt(arr[1]);
                    int looseCounter = Integer.parseInt(arr[2]);
                    if (won) {  winCounter ++;  }
                    else { looseCounter++; }

                    arr[1] = String.valueOf(winCounter);
                    arr[2] = String.valueOf(looseCounter);
                    arr[3] = String.valueOf((Integer.parseInt(arr[3]) + hits) / 2);

                    for (int u=0; u<file.size(); u++) {
                        if (i==u) {
                            writer.write(arr[0] + ";" + arr[1] + ";" + arr[2] + ";" + arr[3] + ";\n ");
                        } else {
                            writer.write(file.get(u) + ";\n");
                        }
                    }
                    writer.close();
                    return true;
                } else {
                    writer.write(this.getNewString(spieler.getName(), won, hits));
                    writer.write(file.get(i));
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getNewString(String name, boolean won, int hits) {
        String result = name + ";";
        if (won) {
            result += "1;0;";
        } else {
            result += "0;1;";
        }
        result += String.valueOf(hits) + ";\n";
        return result;
    }

    public String[] getNewTuple(String[] vars) {
    // vars[0] => # gewinne
    // vars[1] => # verluste
    // vars[2] => durchschnittliche Schlaganzahl
    // vars[3] => neue Schlaganzahl
    // gibt Paramter mit angepasster durchschnittlichen Schlaganzahl zurück
        String[] result = new String[3];

        if (vars == null || vars.length < 3) {
            return null;
        }

        result [0] = vars[0];
        result [1] = vars[1]; 
// Viele Typkonvertierungen nötig, da nur mit int gerechnet werden kann, die Daten aber als String vorliegen (Weil aus datei gelesen wird)
        result [2] = Integer.toString((int) ((Integer.parseInt(vars[2]) + Integer.parseInt(vars[3])) / 2)); 
        return result;
    }

    public void write(String str, int rel, int hits) throws IOException { // TODO: remove
        // @Params str = Spieler String
        // @Params rel = Verrechnung mit aktuellem Spielerstand => gewonnen=1; verloren=-1

        String readLB = "";
        try {
            readLB = (String) String.valueOf(getFile().toArray());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("File is empty, but will get filled");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        if (readLB.contains(str)) {
            String[] currentLine = readLB.split("\n");
            String result = "";

            for (int i=0; i<readLB.split("\n").length; i++) {
                String[] splitLine = currentLine[i].split(":");
                int wonCounter = 0;
                int lostCounter = 0;

                if (splitLine[0].contains(str)) {
                    if (rel>0) {
                        wonCounter = Integer.parseInt(splitLine[1].substring(1)) + 1;
                        lostCounter = Integer.parseInt(splitLine[2].substring(1));
                    } else {
                        wonCounter = Integer.parseInt(splitLine[1].substring(1));
                        lostCounter = Integer.parseInt(splitLine[2].substring(1)) + 1;
                    }
                    int hitCounter = Integer.parseInt(splitLine[3].substring(1)) + hits;
                    result += str + ": " + wonCounter + ": " + lostCounter + ": " + hitCounter + ":  \n";
                } else {
                    for (int u=0; u<this.aktivePlayers.length; u++) {
                        if (splitLine[0].contains(aktivePlayers[u].getName())) {
                            wonCounter = Integer.parseInt(splitLine[1].substring(1));
                            lostCounter = Integer.parseInt(splitLine[2].substring(1)) + 1;
                            int hitCounter = Integer.parseInt(splitLine[3].substring(1)) + hits;
                            result += str + ": " + wonCounter + ": " + lostCounter + ": " + hitCounter + ":  \n";
                        } else {
                            result += currentLine[i] + ": \n";
                        }
                    }
                }
            }
            writer.write(result);
            writer.close();
        } else { // Noch kein Eintrag für Spieler:
            writer.write(readLB + ": \n");
            System.out.println("Spieler " + str + " wird hinzugefügt!");
            if (rel > 0) {
                writer.write(str + ": 1: 0: " + hits + ": \n");
            } else {
                writer.write(str + ": 0: 1: " + hits + ": \n");
            }
            writer.close();
        }
    }  
}
