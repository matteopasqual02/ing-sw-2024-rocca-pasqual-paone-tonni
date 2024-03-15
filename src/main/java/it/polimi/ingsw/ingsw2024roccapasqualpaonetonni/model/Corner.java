package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.alreadyCoveredException;

public class Corner {
    private int position;
    private Seed seed;
    Boolean isFree;

    public Corner(int position,Seed seed) {
        this.position=position;
        this.seed=seed;
        isFree=true;
    }

    public void cover(int position) throws alreadyCoveredException {
        if(isFree){
            isFree=false;
        }
        else {
            throw new alreadyCoveredException("already covered");
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
