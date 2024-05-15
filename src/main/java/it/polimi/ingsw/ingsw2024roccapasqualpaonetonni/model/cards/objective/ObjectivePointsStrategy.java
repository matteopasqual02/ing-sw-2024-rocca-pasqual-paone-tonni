package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.PlayerBoard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;

import java.io.Serializable;

public interface ObjectivePointsStrategy extends Serializable {
    int count(PlayerBoard playerBoard, int[] countTypes, Seed[][] pattern);
}
