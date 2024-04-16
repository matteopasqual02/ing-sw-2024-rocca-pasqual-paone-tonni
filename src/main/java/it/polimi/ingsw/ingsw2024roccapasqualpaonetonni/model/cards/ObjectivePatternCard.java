package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

public class ObjectivePatternCard extends ObjectiveCard {
    private final Seed[][] pattern;

    public ObjectivePatternCard(int id, int points, Seed[][] pattern) {
        super(id, points);
        this.pattern = pattern;
    }

    public Seed[][] getPattern() {
        return pattern;
    }
}
