package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class PlayerBoardTest {

    @Test
    void PlayerGetTest(){
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        assertEquals(owner,board1.getPlayer());
    }
    @Test
    void addStartingCoordinatesCardTest(){
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);

        board1.addStartingCard(cardToAdd);
        int[] coordinates = cardToAdd.getCoordinates();
        int[] twenty={20,20};
        assertArrayEquals(twenty,coordinates);


    }
    @Test
    void addStartingCardNotNullTest(){
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);

        board1.addStartingCard(cardToAdd);
        assertEquals(cardToAdd,owner.getStartingCard());

    }
    @Test
    void addResourceCardCorner4Test() throws InvalidPlaceException {

        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);

        board1.addCard(card_to_add,cardToAdd,4,owner.getCountSeed());
        int[] coordinates = card_to_add.getCoordinates();
        int[] twenty={21,21};
        assertArrayEquals(twenty,coordinates);


    }
    @Test
    void addResourceCardCorner2Test() throws InvalidPlaceException {

        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);

        board1.addCard(card_to_add,cardToAdd,2,owner.getCountSeed());
        int[] coordinates = card_to_add.getCoordinates();
        int[] twenty={19,21};
        assertArrayEquals(twenty,coordinates);


    }

    @Test
    void addResourceCardCorner1Test() throws InvalidPlaceException {

        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);

        board1.addCard(card_to_add,cardToAdd,1,owner.getCountSeed());
        int[] coordinates = card_to_add.getCoordinates();
        int[] twenty={19,19};
        assertArrayEquals(twenty,coordinates);


    }
    @Test
    void addResourceCardCorner3Test() throws InvalidPlaceException {

        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = new Corner(3,GREEN);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);

        board1.addCard(card_to_add,cardToAdd,3,owner.getCountSeed());
        int[] coordinates = card_to_add.getCoordinates();
        int[] twenty={21,19};
        assertArrayEquals(twenty,coordinates);


    }
    @Test
    void addResourceCardOnNullCornerTest() throws InvalidPlaceException {

        //starting card placed in the middle
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,GREEN);
        Corner cf3 = null;
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,EMPTY);
        Corner cb3 = new Corner(3,EMPTY);
        Corner cb4 = new Corner(4,EMPTY);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);

        //resource card to be added on it
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(cardToAdd);

        assertThrows(InvalidPlaceException.class,
                ()->{
                    board1.addCard(card_to_add,cardToAdd,3,owner.getCountSeed());
                });


    }

    @Test
    void addResourceCardOn2CornersTest() throws InvalidPlaceException {

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
        Corner c1 = new Corner(1,EMPTY);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,EMPTY);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add1 = new ResourceCard(10,GREEN,cc,1);

        //second resource card to be added on it
        Corner c21 = new Corner(1,EMPTY);
        Corner c22 = new Corner(2,GREEN);
        Corner c23 = new Corner(3,GREEN);
        Corner c24 = new Corner(4,EMPTY);
        Corner[] c2c ={c1,c2,c3,c4};
        ResourceCard card_to_add2 = new ResourceCard(11,BLUE,c2c,0);

        //third resource card to be added on previous 2
        Corner c31 = new Corner(1,EMPTY);
        Corner c32 = new Corner(2,GREEN);
        Corner c33 = new Corner(3,GREEN);
        Corner c34 = new Corner(4,EMPTY);
        Corner[] c3c ={c1,c2,c3,c4};
        ResourceCard card_to_add3 = new ResourceCard(12,BLUE,c3c,1);

        //putting starting card in the middle
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);
        board1.addCard(card_to_add1,start,3,owner.getCountSeed());
        board1.addCard(card_to_add2,start,4,owner.getCountSeed());
        board1.addCard(card_to_add3,card_to_add1,4,owner.getCountSeed());

        int[] coordinates = card_to_add3.getCoordinates();
        int[] twenty={22,20};
        assertArrayEquals(twenty,coordinates);

    }

    @Test
    void AddingCardCloseToTheBorderTest() throws InvalidPlaceException {
        //this test evaluates adding a card on the position 39, in which the checks cover the border and have to go through the condition of checking an out of bounds index

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

        // factory;
        HashMap<String,Object> attributes =new HashMap<String, Object>();
        String[] cs={"green","empty","blue","red"};
        ResourceCard card;
        PlayingCard cardOnBoard;

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);

        //attributes that will be the same for every card because I want to test the basic version
        attributes.put("color","green");
        attributes.put("points",1);
        attributes.put("corners",cs);

        //card= (ResourceCard) CardFactory.createPlayingCard("Resources",1,attributes);
        cardOnBoard=start;
        for(int i=0;i<19;i++){
            card= (ResourceCard) CardFactory.createPlayingCard("Resources",i,attributes);
            board1.addCard(card,cardOnBoard,4,owner.getCountSeed());
            cardOnBoard=card;
        }
        int[] coordinates = cardOnBoard.getCoordinates();
        int[] twenty={39,39};
        assertArrayEquals(twenty,coordinates);

    }

    @Test
    void AddingCardOverTheBorderWithAPositiveIncreaseTest() throws InvalidPlaceException {

        //ho cambiato tante cose per fare questo test perchè la posizione non veniva aggiornata bene
        //non funziona ancora bene perche devo tenere il >= della posizione
        //era un caso limite dell'aggiunta solo in diagonale, se aggiungessi un'altra alla stessa dim non so se andrebbe bene
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

        // factory;
        HashMap<String,Object> attributes =new HashMap<String, Object>();
        String[] cs={"green","empty","blue","red"};
        ResourceCard card;
        PlayingCard cardOnBoard;

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);

        //attributes that will be the same for every card because I want to test the basic version
        attributes.put("color","green");
        attributes.put("points",1);
        attributes.put("corners",cs);

        //card= (ResourceCard) CardFactory.createPlayingCard("Resources",1,attributes);
        cardOnBoard=start;
        for(int i=0;i<22;i++){
            card= (ResourceCard) CardFactory.createPlayingCard("Resources",i,attributes);
            board1.addCard(card,cardOnBoard,4,owner.getCountSeed());
            cardOnBoard=card;
        }
        //this part puts a card not in diagonal to check different kinds of cards over the 40 border, it can be removed
        card= (ResourceCard) CardFactory.createPlayingCard("Resources",40,attributes);
        board1.addCard(card,cardOnBoard,1,owner.getCountSeed());
        cardOnBoard=card;

        int[] coordinates = cardOnBoard.getCoordinates();
        int[] twenty={41,41};
        assertArrayEquals(twenty,coordinates);

    }

    //bisogna fare il test sull'increase negativo
    @Test
    void AddingCardOverTheBorderWithANegativeIncreaseTest() throws InvalidPlaceException {

    }

}