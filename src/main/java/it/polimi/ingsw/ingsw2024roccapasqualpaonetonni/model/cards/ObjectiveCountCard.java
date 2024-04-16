package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

public class ObjectiveCountCard extends ObjectiveCard {
    private final int[] countTypes;

    public ObjectiveCountCard(int id, int points, int[] countTypes) {
        super(id, points);
        this.countTypes = countTypes;
    }

    public int[] getCountTypes() {
        return countTypes;
    }
}
