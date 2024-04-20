package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;

public class ObjectiveCountCard extends ObjectiveCard {
    private final int[] countTypes;

    public ObjectiveCountCard(int id, int points, int[] countTypes) {
        super(id, points);
        this.countTypes = countTypes;
        objectivePointsStrategy = new ObjectivePointsCount();
    }

    @Override
    public int pointCard(PlayerBoard playerBoard){
        return points * objectivePointsStrategy.count(playerBoard,countTypes,null);
    }

    public int[] getCountTypes() {
        return countTypes;
    }
}
