package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.AlreadyCoveredException;

public class Corner {
    private int position;
    private Seed seed;
    private Boolean isFree;

    public Corner(int position,Seed seed) {
        this.position=position;
        this.seed=seed;
        isFree=true;
    }

    public void cover(int position) throws AlreadyCoveredException {
        if(isFree){
            isFree=false;
        }
        else {
            throw new AlreadyCoveredException("already covered");
        }
    }
    public boolean isCovered(int position) {
        if(isFree) return true;
        return false;
    }

    public Seed getSeed() {
        return seed;
    }
}
