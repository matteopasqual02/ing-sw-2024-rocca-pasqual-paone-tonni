package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.util.*;

public class ObjectivePointsCount implements ObjectivePointsStrategy{
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
