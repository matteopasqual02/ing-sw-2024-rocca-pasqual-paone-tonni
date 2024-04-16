package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.EMPTY;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.*;

class calculateCenterUpdateTest {
    @Test
    void seedUpdateStartingCard(){
        int[] countSeed;
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);
        countSeed=owner.getCountSeed();
        int[] trueCount={1,1,0,0,0,0,0};
        assertArrayEquals(trueCount,countSeed);
    }

    @Test
    void seedUpdateFlippedStartingCard(){
        int[] countSeed;
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);
        countSeed=owner.getCountSeed();
        int[] trueCount={2,0,0,0,0,0,0};
        assertArrayEquals(trueCount,countSeed);
    }
}