package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.*;

class ObjectivePointsTestToString {

    @Test
    void TestObjectivePoints() {
        int[] types ={0, 0, 3, 0, 0, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

}