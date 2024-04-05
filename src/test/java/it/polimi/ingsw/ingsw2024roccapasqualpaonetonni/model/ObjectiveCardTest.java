package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {

    @Test
    void CountColorObjective() throws InvalidPlaceException, ConditionsNotMetException {
        //count points with RED objective
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner cb1 = new Corner(1,RED);
        Corner cb2 = new Corner(2,RED);
        Corner cb3 = new Corner(3,RED);
        Corner cb4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start = new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,RED);
        Corner c2 = new Corner(2,RED);
        Corner c3 = new Corner(3,BLUE);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);
        Seed[] psCard ={RED,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,true,RED,psCard,null);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int[] countSeed = owner.getCountSeed();
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(4,pointsReached);
    }

}