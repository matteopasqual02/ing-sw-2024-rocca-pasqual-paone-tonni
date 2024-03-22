package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class GoldCard extends PlayingCard{
    private String pointCondition;
    private int[] placeCondition=new int[4];
    public GoldCard(int id, Seed seed, Corner[] c, int points, String cond, int[] p){
        super(id,seed,c,points);
        pointCondition=cond;
        this.placeCondition= Arrays.copyOf(p,4);
    }

    public boolean checkRequirements(int[] available_seeds) {
        for (int i = 0; i < 4; i++) {
            if (available_seeds[i] < placeCondition[i]) {
                return false;
            }
        }
        return true;
    }

    public int[] getRequirements() {return placeCondition;}

    public String getPointCondition() {
        return pointCondition;
    }

}
