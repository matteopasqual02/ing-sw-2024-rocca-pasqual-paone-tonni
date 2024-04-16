package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

public class ObjectiveCountCard extends ObjectiveCard {
    private final int[] countTypes;

    public ObjectiveCountCard(int id, int points, int[] countTypes) {
        super(id, points);
        this.countTypes = countTypes;
        objectivePointsStrategy = new ObjectivePointsCount();
    }

    public int[] getCountTypes() {
        return countTypes;
    }
}
