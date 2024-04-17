package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import impl.org.controlsfx.collections.NonIterableChange;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class GoldCardTest {

    @Test
    void TestGold () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {3,1,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,"feather",placeCond);

        String carta= card_to_add1.toString(false);
        System.out.println(carta);
    }

}