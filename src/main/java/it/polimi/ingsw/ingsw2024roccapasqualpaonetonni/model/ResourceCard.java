package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.fusesource.jansi.Ansi;
import static org.fusesource.jansi.Ansi.ansi;

public class ResourceCard extends PlayingCard{
    public ResourceCard(int id, Seed seed, Corner[] c,int points){
        super(id,seed,c,points);
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        return points;
    }

    public String toString(){
        StringBuilder resultString = new StringBuilder();

        resultString.append(ansi().cursor(0,0).fg(Ansi.Color.WHITE).bg(Ansi.Color.YELLOW));

        return resultString.toString();
    }
}

// Resource Card is actually a PlayingCard Without being an abstract class


