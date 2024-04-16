package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;

class ResourceCardTest {

    @Test
    void TestResource () {
        Corner c1 = new Corner(1,null);
        Corner c2 = new Corner(2,RED);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        String carta= card_to_add.toString(false);
        System.out.println(carta);
    }

}