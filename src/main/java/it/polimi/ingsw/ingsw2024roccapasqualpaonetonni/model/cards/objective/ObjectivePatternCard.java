package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.fusesource.jansi.Ansi;

public class ObjectivePatternCard extends ObjectiveCard {
    private final Seed[][] pattern;

    public ObjectivePatternCard(int id, int points, Seed[][] pattern) {
        super(id, points);
        this.pattern = pattern;
        objectivePointsStrategy = new ObjectivePointsPattern();
    }
    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,null,pattern);
    }

    public Seed[][] getPattern() {
        return pattern;
    }


    public String toString () {
        StringBuilder sb = new StringBuilder();
        Ansi.Color background = Ansi.Color.WHITE;

        //First Line


        //Second Line


        //Third Line



        return sb.toString();
    }
}
