package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Corner;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;

class ResourceCardTestToString {

    @Test
    void TestResource () {
        Corner c1 = new Corner(1,null);
        Corner c2 = new Corner(2,null);
        Corner c3 = new Corner(3,null);
        Corner c4 = new Corner(4,null);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        String carta= card_to_add.toString();
        System.out.println(carta);
    }

    @Test
    void TestResource1 () {
        Corner c1 = new Corner(1,GREEN);
        Corner c2 = new Corner(2,BLUE);
        Corner c3 = new Corner(3,PURPLE);
        Corner c4 = new Corner(4,POTION);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        String carta= card_to_add.toString();
        System.out.println(carta);
    }

}