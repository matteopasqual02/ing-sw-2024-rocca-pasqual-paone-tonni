package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

public class ObjectivePatternCard extends ObjectiveCard {
    private final Seed[][] pattern;

    public ObjectivePatternCard(int id, int points, Seed[][] pattern) {
        super(id, points);
        this.pattern = pattern;
        objectivePointsStrategy = new ObjectivePointsPattern();
    }
    @Override
    public int pointCard(Player player){
        return points * objectivePointsStrategy.count(player,null,pattern);
    }

    public Seed[][] getPattern() {
        return pattern;
    }
}
