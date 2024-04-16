package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

public class ResourceCard extends PlayingCard {
    public ResourceCard(int id, Seed seed, Corner[] c, int points) {
        super(id, seed, c, points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }


    public String toString(boolean flipped) {

        int spacer;

        StringBuilder ris = new StringBuilder();

        //creazione di tutte le carte resource, controllare con TEST
        if (!flipped) {
            // stampa carta fronte
            if (points == 0) {
                ris.append(ansi().cursor(DefaultValue.row_commonCards - 1 + spacer)
                        .fg(Ansi.Color.WHITE).bg(Ansi.Color.RED)
                );
            } else {

            }
        else{
                // carta retro
            }
        }
    }
    public String toString ( boolean flipped, String color, String[]corners,int points){
        StringBuilder sb = new StringBuilder();

        if (!flipped) {
            // Fronte della carta
            sb.append("Colore: \n");
            sb.append("-------------------\n");
            sb.append("| ").append(corners[0]).append(" | ").append(points).append(" | ").append(color).append(" |\n");
            sb.append("-------------------\n");
            sb.append("|               |\n");
            sb.append("-------------------\n");
            sb.append("| ").append(corners[2]).append("     | ").append(color).append(" |\n");
            sb.append("-------------------\n");
            sb.append("Punti: ").append(points).append("\n");
        } else {
            // Retro della carta
            sb.append("Retro della carta\n");
        }

        return sb.toString();
    }


}



// Resource Card is actually a PlayingCard Without being an abstract class


