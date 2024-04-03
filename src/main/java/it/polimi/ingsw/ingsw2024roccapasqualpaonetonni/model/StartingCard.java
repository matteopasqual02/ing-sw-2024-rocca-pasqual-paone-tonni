package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class StartingCard extends PlayingCard{
    private Boolean[] center;
    private Corner[] cornersBack;

    public StartingCard(int id, Boolean[] c, Corner[] cf, Corner[] cb){
        super(id,Seed.EMPTY,cf,0);
        this.center= Arrays.copyOf(c,4);
        this.cornersBack= Arrays.copyOf(cb,4);
        this.isFlipped=false;

    }
    public Boolean[] getCenter() { return center; }
    public Corner getCorner(int pos){
        if (isFlipped) {
            return cornersBack[pos - 1];
        }
        else {
            return corners[pos - 1];
        }
    }
}
