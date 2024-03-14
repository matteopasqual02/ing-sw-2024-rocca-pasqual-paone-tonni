package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class GoldCard extends PlayingCard{
    private String pointCondition;
    private int[] placeCondition=new int[4];
    public GoldCard(int id, Seed seed, Corner[] c,int points,String cond,int[] p){
        super(id,seed,c,points);
        pointCondition=cond;
        this.placeCondition= Arrays.copyOf(p,4);
    }

    public boolean checkRequirements(int[] aviable_seeds) {
        for (int i = 0; 0 < 4; i++) {
            if (aviable_seeds[i] < placeCondition[i]) {
                return false;
            }
        }
        return true;
    }
    //public Boolean checkRequirements
    //public int countPoint()

}
