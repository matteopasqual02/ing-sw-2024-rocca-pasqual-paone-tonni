package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.io.Serializable;

/**
 * The type Corner.
 */
public class Corner implements Serializable {
    /**
     * The Position.
     */
    private final int position;
    /**
     * The Seed.
     */
    private final Seed seed;

    /**
     * Instantiates a new Corner.
     *
     * @param position the position
     * @param seed     the seed
     */
    public Corner(int position,Seed seed) {
        this.seed=seed;
        this.position = position;
    }

    /**
     * Gets seed.
     *
     * @return the seed
     */
    public Seed getSeed() {
        return seed;
    }

    /**
     * Gets position.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }
}
