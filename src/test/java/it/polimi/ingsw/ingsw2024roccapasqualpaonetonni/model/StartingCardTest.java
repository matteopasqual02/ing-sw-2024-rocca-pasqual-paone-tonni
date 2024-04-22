package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class StartingCardTest {

    @Test
    void TestStarting () {
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,GREEN);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,GREEN);
        Corner cb2 = new Corner(2,PURPLE);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,BLUE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start = new StartingCard(1,c,cf,cb);

        String carta= start.toString(false);
        System.out.println(carta);
    }

}