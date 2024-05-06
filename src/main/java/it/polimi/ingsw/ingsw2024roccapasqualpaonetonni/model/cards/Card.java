package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

// Card superclass, that all the other classes of cards extend

public abstract class Card {
    private final int idCard;

    public Card(int id){
        this.idCard=id;
    }

    public int getIdCard() {
        return idCard;
    }
}
