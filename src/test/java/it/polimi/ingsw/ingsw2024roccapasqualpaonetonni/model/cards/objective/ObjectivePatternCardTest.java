package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.*;

class ObjectivePatternCardTest {

    @Test
    void TestObjectivePoints5() {
        Seed[][] pattern = {{GREEN, null, null},
                {null, GREEN, null},
                {null, null, GREEN}};
        ObjectiveCard objectiveCard = new ObjectivePatternCard(110,2,pattern);

        String carta= objectiveCard.toString();
        System.out.println(carta);
    }

}