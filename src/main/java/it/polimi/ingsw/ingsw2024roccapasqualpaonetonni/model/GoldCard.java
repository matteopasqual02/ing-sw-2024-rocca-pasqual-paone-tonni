package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Arrays;

public class GoldCard extends PlayingCard{
    private String pointCondition;
    private int[] placeCondition;
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
    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        int points = 0;
        PlayingCard cardOnBoard;
        switch(pointCondition) {
            case "corners":
                int[][] postions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
                for (int[] i : postions) {
                    cardOnBoard = board[x + i[0]][y + i[1]];
                    if (cardOnBoard != null) {
                        points += points;
                    }
                };
            case "feather", "potion", "scroll":
                points += points * seedCount[Seed.getByName(pointCondition).getId()];
            case null, default:
                ;
        }
        return points;
    }

    public int[] getPlaceCondition(){return placeCondition;};

}

/*  not used -- if needed for testing use it
    public String getPointCondition() {
        return pointCondition;
    }
 */
