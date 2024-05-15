package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.util.LinkedList;
import java.util.List;

/**
 * The type Objective points count.
 */
public class ObjectivePointsCount implements ObjectivePointsStrategy{
    /**
     * Count int.
     *
     * @param playerBoard the player board
     * @param countTypes  the count types
     * @param pattern     the pattern
     * @return the int
     */
    @Override
    public int count(PlayerBoard playerBoard, int[] countTypes, Seed[][] pattern) {
        List<Integer> currentCount = new LinkedList<>();

        for(int position=0; position<countTypes.length; position++){
            if (countTypes[position] != 0) {
                currentCount.add(playerBoard.getPlayer().getCountSeed()[position]/countTypes[position]);
            }
        }

        return  currentCount.stream().min(Integer::compareTo).orElse(0);
    }
}
