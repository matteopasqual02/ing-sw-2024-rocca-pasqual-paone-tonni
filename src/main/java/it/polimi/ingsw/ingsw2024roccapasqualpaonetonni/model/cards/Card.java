package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

// Card superclass, that all the other classes of cards extend

import java.io.Serializable;

/**
 * The type Card.
 */
public abstract class Card implements Serializable {
    /**
     * ID card.
     */
    private final int idCard;

    /**
     * Instantiates a new Card.
     *
     * @param id the id
     */
    public Card(int id){
        this.idCard=id;
    }

    /**
     * Gets id card.
     *
     * @return the id card
     */
    public int getIdCard() {
        return idCard;
    }

}
