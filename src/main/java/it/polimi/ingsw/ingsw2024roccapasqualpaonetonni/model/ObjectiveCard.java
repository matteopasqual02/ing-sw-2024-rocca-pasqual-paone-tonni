package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

public class ObjectiveCard extends Card{
    private int points;
    private boolean isCount; //se è una carta obiettivo di tipo conteggio isCount=true, se è di tipo pattern isCount=false
    private Seed type;
    private String shape;
    private Seed primaryCard;
    private Seed secondaryCard;

    public ObjectiveCard(int id, int points, boolean isCount, Seed type, Seed[] psCards, String shape){
        super(id);
        this.points = points;
        this.isCount = isCount;
        this.type = type;
        this.primaryCard = psCards[0];
        this.secondaryCard = psCards[1];
        this.shape = shape;
    }

    public int getPoints(){
        return points;
    }
    public boolean getisCount(){
        return isCount;
    }
    public Seed getType(){
        return type;
    }
    public String getShape(){
        return shape;
    }
    public Seed getPrimaryCard(){
        return primaryCard;
    }
    public Seed getSecondaryCard(){
        return secondaryCard;
    }

}
