package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;

public interface ObjectivePointsStrategy {
    int count(Player player, ObjectiveCard objectiveCard);
}
