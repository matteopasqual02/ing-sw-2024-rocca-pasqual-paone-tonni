package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoSeedException;

import java.util.Arrays;

public class StartingCard extends PlayingCard{
    private Boolean[] center;
    private Corner[] cornersBack;
    public StartingCard(int id,Boolean[] c,Corner[] cf,Corner[] cb){
        super(id,Seed.EMPTY,cf,0);
        this.center= Arrays.copyOf(c,4);
        this.cornersBack= Arrays.copyOf(cb,4);
        this.isFlipped=false;

    }
    public Boolean[] getCenter() throws NoSeedException {
        if(!isFlipped) return center;
        else throw new NoSeedException("Wrong side");
    }

    public Corner[] getBackCorner(){return  cornersBack;}
}
