package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;


import static org.fusesource.jansi.Ansi.ansi;

public class ResourceCard extends PlayingCard{
    public ResourceCard(int id, Seed seed, Corner[] c,int points){
        super(id,seed,c,points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }

    public String toString (boolean flipped) {

        StringBuilder ris = new StringBuilder();

        if (!flipped) {
            // stampa carta fronte
            if ( points==0){
                ris.append(ansi())
            }

        }
        else {
            // carta retro
        }
    }
}

// Resource Card is actually a PlayingCard Without being an abstract class


