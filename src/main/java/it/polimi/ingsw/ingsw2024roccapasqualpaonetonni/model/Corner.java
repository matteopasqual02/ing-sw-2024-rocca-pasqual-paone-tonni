package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.AlreadyCoveredException;

public class Corner {
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
