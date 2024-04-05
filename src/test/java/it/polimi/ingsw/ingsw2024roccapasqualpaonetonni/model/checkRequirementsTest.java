package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class checkRequirementsTest {
    @Test
    void SatisfiedRequirementsTest() throws InvalidPlaceException, ConditionsNotMetException {
        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,PURPLE);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //gold card with condition
        Corner c21 =null;// new Corner(1,EMPTY);
        Corner c22 =null;// new Corner(2,GREEN);
        Corner c23 = null;//new Corner(3,GREEN);
        Corner c24 = null;//new Corner(4,EMPTY);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] pointCondition = {1,1,1,1};
        GoldCard gold = new GoldCard(11,BLUE,c2c,2,"feather",pointCondition);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        board1.addCard(gold,start,4,owner.getCountSeed());

        int[] coordinates = gold.getCoordinates();
        int[] trueCoord={21,19};
        assertArrayEquals(trueCoord,coordinates);
    }

    @Test
    void failedRequirementsConditionsNotMetMissingPurpleTest() throws InvalidPlaceException, ConditionsNotMetException {
        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //gold card with condition
        Corner c21 =null;// new Corner(1,EMPTY);
        Corner c22 =null;// new Corner(2,GREEN);
        Corner c23 = null;//new Corner(3,GREEN);
        Corner c24 = null;//new Corner(4,EMPTY);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] pointCondition = {1,1,1,1};
        GoldCard gold = new GoldCard(11,BLUE,c2c,2,"feather",pointCondition);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());

        assertThrows(ConditionsNotMetException.class,
                ()->{
                    board1.addCard(gold,start,4,owner.getCountSeed());
                });
    }

    @Test
    void failedRequirementsConditionsNotMetMissingEverythingTest() throws InvalidPlaceException, ConditionsNotMetException {
        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,EMPTY);
        Corner c3 = new Corner(3,EMPTY);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //gold card with condition
        Corner c21 =new Corner(1,RED);;// new Corner(1,EMPTY);
        Corner c22 =new Corner(1,BLUE);;// new Corner(2,GREEN);
        Corner c23 = new Corner(1,GREEN);;//new Corner(3,GREEN);
        Corner c24 = new Corner(1,PURPLE);;//new Corner(4,EMPTY);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] pointCondition = {1,1,1,1};
        GoldCard gold = new GoldCard(11,BLUE,c2c,2,"feather",pointCondition);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());

        assertThrows(ConditionsNotMetException.class,
                ()->{
                    board1.addCard(gold,start,4,owner.getCountSeed());
                });
    }

    @Test
    void flippedGoldTest() throws InvalidPlaceException, ConditionsNotMetException {
        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //gold card with condition
        Corner c21 =null;// new Corner(1,EMPTY);
        Corner c22 =new Corner(1,RED);;// new Corner(2,GREEN);
        Corner c23 = new Corner(1,EMPTY);;//new Corner(3,GREEN);
        Corner c24 = null;//new Corner(4,EMPTY);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] pointCondition = {1,1,1,1};
        GoldCard gold = new GoldCard(11,BLUE,c2c,2,"feather",pointCondition);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        gold.flip();
        board1.addCard(gold,start,4,owner.getCountSeed());
        int[] coordinates = gold.getCoordinates();
        int[] trueCoord={21,19};
        assertArrayEquals(trueCoord,coordinates);
    }

    @Test
    //this test does not work on purpose because I wanted to see what the exception was saying when something is missing
    void ExceptionTest() throws InvalidPlaceException, ConditionsNotMetException {
        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard start=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,BLUE);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //gold card with condition
        Corner c21 =null;// new Corner(1,EMPTY);
        Corner c22 =new Corner(1,RED);;// new Corner(2,GREEN);
        Corner c23 = new Corner(1,EMPTY);;//new Corner(3,GREEN);
        Corner c24 = null;//new Corner(4,EMPTY);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] pointCondition = {1,1,1,1};
        GoldCard gold = new GoldCard(11,BLUE,c2c,2,"feather",pointCondition);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        board1.addCard(gold,start,4,owner.getCountSeed());
        int[] coordinates = gold.getCoordinates();
        int[] trueCoord={21,19};
        assertArrayEquals(trueCoord,coordinates);

        //order green blue red purple
        //i left this test as an error to understand what the exception was saying.
        //The exception here should give that both red and purple are not enough, not just the first one it finds.
    }
}