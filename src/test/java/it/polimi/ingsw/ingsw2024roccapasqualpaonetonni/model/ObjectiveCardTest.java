package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {
    private static StartingCard getStartingCard() {
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
        return new StartingCard(1,c,cf,cb);
    }

    @Test
    void CountColorObjective() throws InvalidPlaceException, ConditionsNotMetException {
        //count points with RED objective
        StartingCard start = getStartingCard();

        //resource card to be added on it
        Corner c1 = new Corner(1,RED);
        Corner c2 = new Corner(2,RED);
        Corner c3 = new Corner(3,BLUE);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);
        Seed[] psCard ={null,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,true,RED,psCard,null);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(4,pointsReached);
    }


    @Test
    void CountObjectObjective() throws InvalidPlaceException, ConditionsNotMetException {
        //count points with scroll objective
        StartingCard start = getStartingCard();

        //resource card to be added on it
        Corner c1 = new Corner(1,SCROLL);
        Corner c2 = new Corner(2,SCROLL);
        Corner c3 = new Corner(3,SCROLL);
        Corner c4 = new Corner(4,SCROLL);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);
        Seed[] psCard ={null,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,true,SCROLL,psCard,null);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(4,pointsReached);
    }

    @Test
    void CountMixedObjective() throws InvalidPlaceException, ConditionsNotMetException {
        //count points with mixed objective
        StartingCard start = getStartingCard();

        //resource card to be added on it
        Corner c1 = new Corner(1,SCROLL);
        Corner c2 = new Corner(2,FEATHER);
        Corner c3 = new Corner(3,POTION);
        Corner c4 = new Corner(4,POTION);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);
        Seed[] psCard ={null,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,3,true,MIXED,psCard,null);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(3,pointsReached);
    }

    @Test
    void DiagonalPatternDown() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,GREEN,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,false,null,psCard,"down");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        board1.addCard(card_to_add2,card_to_add1,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add2,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(2,pointsReached);
    }

    @Test
    void DoubleDiagonalPatternDown() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,GREEN,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);
        ResourceCard card_to_add4 = new ResourceCard(12,GREEN,cf,1);
        ResourceCard card_to_add5 = new ResourceCard(12,GREEN,cf,1);
        ResourceCard card_to_add6 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,false,null,psCard,"down");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        board1.addCard(card_to_add2,card_to_add1,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add2,3,owner.getCountSeed());
        board1.addCard(card_to_add4,card_to_add3,3,owner.getCountSeed());
        board1.addCard(card_to_add5,card_to_add4,3,owner.getCountSeed());
        board1.addCard(card_to_add6,card_to_add5,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(4,pointsReached);
    }

    @Test
    void DiagonalPatternUp() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,GREEN,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,null};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,2,false,null,psCard,"up");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,2,owner.getCountSeed());
        board1.addCard(card_to_add2,card_to_add1,2,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add2,2,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(2,pointsReached);
    }

    @Test
    void SEPatternL() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,RED,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,RED,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,RED};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,3,false,null,psCard,"se");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,2,owner.getCountSeed());
        board1.addCard(card_to_add2,start,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add2,3,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(3,pointsReached);
    }

    @Test
    void SWPatternL() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,RED,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,RED,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,RED};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,3,false,null,psCard,"sw");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,2,owner.getCountSeed());
        board1.addCard(card_to_add2,start,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add2,4,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(3,pointsReached);
    }

    @Test
    void NWPatternL() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,RED,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,RED,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,RED};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,3,false,null,psCard,"nw");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,2,owner.getCountSeed());
        board1.addCard(card_to_add2,start,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add1,1,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(3,pointsReached);
    }

    @Test
    void NEPatternL() throws InvalidPlaceException, ConditionsNotMetException{
        Boolean[] c = {true,true,true,true};
        Corner cf1 = new Corner(1,RED);
        Corner cf2 = new Corner(2,RED);
        Corner cf3 = new Corner(3,RED);
        Corner cf4 = new Corner(4,RED);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        StartingCard start = new StartingCard(1,c,cf,cf);
        ResourceCard card_to_add1 = new ResourceCard(10,RED,cf,1);
        ResourceCard card_to_add2 = new ResourceCard(11,RED,cf,1);
        ResourceCard card_to_add3 = new ResourceCard(12,GREEN,cf,1);

        Seed[] psCard ={GREEN,RED};
        ObjectiveCard objectiveCard = new ObjectiveCard(110,3,false,null,psCard,"ne");

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,2,owner.getCountSeed());
        board1.addCard(card_to_add2,start,3,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add1,2,owner.getCountSeed());
        int pointsReached = objectiveCard.pointCard(board1);

        assertEquals(3,pointsReached);
    }
}