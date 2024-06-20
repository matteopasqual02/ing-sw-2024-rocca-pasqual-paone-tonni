package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;

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
     * The Card Attached.
     */
    private PlayingCard cardAttached;

    /**
     * Instantiates a new Corner.
     *
     * @param position the position
     * @param seed     the seed
     */
    public Corner(int position,Seed seed) {
        this.seed=seed;
        this.position = position;
        this.cardAttached=null;
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

    /**
     * Get card attached playing card.
     *
     * @return the playing card
     */
    public PlayingCard getCardAttached(){return cardAttached;}

    /**
     * Set card attached.
     *
     * @param card the card
     */
    public void setCardAttached(PlayingCard card){
        this.cardAttached=card;
        System.out.println(cardAttached.getIdCard());
    }
}
