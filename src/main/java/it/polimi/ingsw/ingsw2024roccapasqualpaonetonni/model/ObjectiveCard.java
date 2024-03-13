package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class ObjectiveCard extends Card{
    int points;
    boolean isCount; //se è una carta obiettivo di tipo conteggio isCount=true, se è di tipo pattern isCount=false
    Seed type;
    String shape;
    Seed primaryCard;
    Seed secondaryCard;

    public ObjectiveCard(int id, int points,boolean isCount,Seed type,Seed[] psCards,String shape){
        super(id);
        this.points=points;
        this.isCount=isCount;
        if(isCount)
        {
            this.type=type;
            this.primaryCard=null;
            this.secondaryCard=null;
            this.shape=null;
        }
        else
        {
            this.type=null;
            this.primaryCard=psCards[0];
            this.secondaryCard=psCards[1];
            this.shape=shape;
        }

    }

    int getPoints(){
        return points;
    }
    boolean getisCount(){
        return isCount;
    }
    Seed getType(){
        return type;
    }
    String getShape(){
        return shape;
    }
    Seed getPrimaryCard(){
        return primaryCard;
    }
    Seed getSecondaryCard(){
        return secondaryCard;
    }

}
