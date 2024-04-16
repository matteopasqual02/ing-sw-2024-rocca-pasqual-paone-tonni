package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.util.*;

public class ObjectivePointsCount implements ObjectivePointsStrategy{
    @Override
    public int count(Player player, int[] countTypes, Seed[][] pattern) {
        List<Integer> currentCount = new LinkedList<>();

        for(int position: countTypes){
            currentCount.add(player.getCountSeed()[position]/countTypes[position]);
        }

        return  currentCount.stream().min(Integer::compareTo).orElse(0);
    }
}
