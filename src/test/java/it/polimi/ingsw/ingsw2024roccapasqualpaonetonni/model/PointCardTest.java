package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class PointCardTest {

    @Test
    void checkPointsResourceCardOne() throws InvalidPlaceException, ConditionsNotMetException {
        //assert +1 point on resource card
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

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();
        
        assertEquals(1,pointsReached);
    }

    @Test
    void checkPointsResourceCardZero() throws InvalidPlaceException, ConditionsNotMetException {
        //assert +0 point on resource card
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
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,0);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(0,pointsReached);
    }

    @Test
    void checkPointsGoldCardNull() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +3 point on gold card with no condition
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
        StartingCard start = new StartingCard(1,c,cf,cb);

        //card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(3,pointsReached);


    }

    @Test
    void checkPointsGoldCardFeather() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +1 point on gold cardcard for each feather (it's equal for scroll and potion)
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
        StartingCard start = new StartingCard(1,c,cf,cb);

        // card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,FEATHER);
        Corner c4 = new Corner(4,FEATHER);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,1,"feather",placeCond);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(2,pointsReached);


    }

    @Test
    void checkPointsGoldCardOneCorner() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +2 point on gold card for each corner covered
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
        StartingCard start = new StartingCard(1,c,cf,cb);

        // card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,FEATHER);
        Corner c4 = new Corner(4,FEATHER);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,2,"corners",placeCond);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(2,pointsReached);
    }

    @Test
    void checkPointsGoldCardTwoCorner() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +2 point on gold card for each corner covered
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
        StartingCard start = new StartingCard(1,c,cf,cb);

        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_addr1 = new ResourceCard(10,GREEN,cc,0);
        ResourceCard card_to_addr2 = new ResourceCard(10,GREEN,cc,0);
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,2,"corners",placeCond);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_addr1,start,3,owner.getCountSeed());
        board1.addCard(card_to_addr2,start,4,owner.getCountSeed());
        board1.addCard(card_to_add1,card_to_addr2,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(4,pointsReached);
    }

    @Test
    void checkPointsFlippedResources() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +0 point on resource card flipped
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
        card_to_add1.flip();

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(0,pointsReached);
    }

    @Test
    void checkPointsFlippedGold() throws InvalidPlaceException, ConditionsNotMetException{
        //assert +0 point on gold card flipped
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
        StartingCard start = new StartingCard(1,c,cf,cb);

        //card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);
        card_to_add1.flip();

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = owner.getCurrentPoints();

        assertEquals(0,pointsReached);


    }

}