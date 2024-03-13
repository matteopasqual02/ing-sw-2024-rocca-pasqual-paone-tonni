package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoSeedException;

import java.util.Arrays;

public class PlayingCard extends Card{
    private Seed cardSeed;
    private int points;
    private boolean isFlipped;
    private Corner[] corners=new Corner[4];

    public PlayingCard(int id, Seed seed, Corner[] c,int points){
        super(id);
        this.cardSeed=seed;
        this.corners= Arrays.copyOf(c,4);
        this.isFlipped=false;
        this.points=points;
    }

    public void flip(){
        if(isFlipped) isFlipped=false;
        else isFlipped=true;
    }

    public Corner getCorner(int pos){
        return corners[pos];
    }
    public Seed getSeed() throws NoSeedException{
        if(!cardSeed.equals("EMPTY")) return cardSeed;
        else throw new NoSeedException("No center seed");

    }

}
