package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

public class ObjectivePointsPattern implements ObjectivePointsStrategy{
    @Override
    public int count(Player player, ObjectiveCard objectiveCard) {
        return 10;
    }
}
