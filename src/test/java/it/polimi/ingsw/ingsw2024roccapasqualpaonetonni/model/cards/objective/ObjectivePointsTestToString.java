package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.*;

class ObjectivePointsTestToString {

    @Test
    void TestObjectivePoints() {
        int[] types ={0, 0, 0, 0, 2, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints1() {
        int[] types ={0, 0, 0, 0, 0, 2, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints2() {
        int[] types ={0, 0, 0, 0, 0, 0, 2};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints3() {
        int[] types ={0, 0, 0, 0, 1, 1, 1};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints4() {
        int[] types ={0, 0, 0, 3, 0, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints5() {
        int[] types ={0, 0, 3, 0, 0, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints6() {
        int[] types ={0, 3, 0, 0, 0, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

    @Test
    void TestObjectivePoints7() {
        int[] types ={3, 0, 0, 0, 0, 0, 0};
        ObjectiveCard objectiveCard1 = new ObjectiveCountCard(110,2, types);

        String carta= objectiveCard1.toString();
        System.out.println(carta);
    }

}