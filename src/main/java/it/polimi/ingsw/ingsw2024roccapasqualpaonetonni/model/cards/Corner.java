package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.io.Serializable;

public class Corner implements Serializable {
    private final int position;
    private final Seed seed;

    public Corner(int position,Seed seed) {
        this.seed=seed;
        this.position = position;
    }

    public Seed getSeed() {
        return seed;
    }

    public int getPosition() {
        return position;
    }
}
