package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Corner;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.util.Arrays;

public class GoldCard extends PlayingCard {
    private final String pointCondition;
    private final int[] placeCondition;
    public GoldCard(int id, Seed seed, Corner[] c, int points, String cond, int[] p){
        super(id,seed,c,points);
        pointCondition=cond;
        this.placeCondition= Arrays.copyOf(p,4);
    }

    public int[] checkRequirements(int[] available_seeds) {
        int[] result = new int[2];
        for (int i = 0; i < placeCondition.length; i++) {
            if (placeCondition[i] > available_seeds[i]) {
                result[1] = i;
                return result;
            }
        }
        result[0] = 1;
        return result;
    }

    public int calculatePoints(PlayingCard[][] board, int[] seedCount, int x, int y) {
        int curr_points = 0;
        PlayingCard cardOnBoard;
        switch(pointCondition) {
            case "corners":
                //count the number of corner covered by (4 corner) the current card
                int[][] positions = {{-1, -1, 1}, {-1, 1, 2}, {1, 1, 3}, {1, -1, 4}};
                for (int[] i : positions) {
                    cardOnBoard = board[x + i[0]][y + i[1]];
                    if (cardOnBoard != null) {
                        curr_points += points;
                    }
                }
                return curr_points;
            case "feather", "potion", "scroll":
                return points * seedCount[Seed.getByName(pointCondition).getId()];
            case null, default:
                return points;
        }
    }

    public int[] getPlaceCondition(){return placeCondition;}
    public String getPointCondition() { return pointCondition; }
}


