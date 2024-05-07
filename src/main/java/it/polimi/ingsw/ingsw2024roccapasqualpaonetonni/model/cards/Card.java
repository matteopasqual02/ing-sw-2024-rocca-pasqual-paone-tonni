package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

// Card superclass, that all the other classes of cards extend

import java.io.Serializable;

public abstract class Card implements Serializable {
    private final int idCard;

    public Card(int id){
        this.idCard=id;
    }

    public int getIdCard() {
        return idCard;
    }

}
