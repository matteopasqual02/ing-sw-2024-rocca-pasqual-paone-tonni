package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.io.Serializable;

/**
 * The interface Objective points strategy.
 */
public interface ObjectivePointsStrategy extends Serializable {
    /**
     * Count int.
     *
     * @param playerBoard the player board
     * @param countTypes  the count types
     * @param pattern     the pattern
     * @return the int
     */
    int count(PlayerBoard playerBoard, int[] countTypes, Seed[][] pattern);
}
