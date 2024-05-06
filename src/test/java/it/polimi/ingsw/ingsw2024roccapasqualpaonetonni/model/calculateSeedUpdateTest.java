package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;
import static org.junit.jupiter.api.Assertions.*;

class calculateSeedUpdateTest {
    @Test
    void seedUpdateResourceCorner4Card() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={4,1,1,0,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,PURPLE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,4,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }

    @Test
    void seedUpdateFlippedResourceCard() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={2,0,0,1,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,RED);//this corner will be covered by the new card so red is not in the final seedcount
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,PURPLE,cc,1);
        card_to_add.flip();

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,4,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }

    @Test
    void seedUpdateGoldCard() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={4,1,3,1,0,0,0};
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
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        Corner c21 = new Corner(1,PURPLE);
        Corner c22 = new Corner(2,GREEN);
        Corner c23 = new Corner(3,RED);
        Corner c24 = new Corner(4,RED);
        Corner[] c2c ={c21,c22,c23,c24};
        int[] placeCond = {0,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,c2c,3,null,placeCond);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,4,owner.getCountSeed());
        board1.addCard(card_to_add1,cardToAdd,3,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }

    @Test
    void seedUpdateOnTheBorder() throws InvalidPlaceException, ConditionsNotMetException {
        int[] trueCount={22,21,1,0,0,0,0};
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
        HashMap<String, JsonElement> attributes =new HashMap<>();
        ResourceCard card;
        PlayingCard cardOnBoard;

        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        board1.addStartingCard(start);

        //attributes that will be the same for every card because I want to test the basic version
        String color = "green";
        attributes.put("color", new JsonPrimitive(color));
        int points = 1;
        attributes.put("points", new JsonPrimitive(points));
        String[] cs={"green","empty","blue","red"};
        JsonArray jArray = new JsonArray();
        for (String s: cs) {
            if (s == null) {
                jArray.add(new JsonPrimitive((String) null));
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners", jArray);

        //card= (ResourceCard) CardFactory.createPlayingCard("Resources",1,attributes);
        cardOnBoard=start;
        for(int i=0;i<20;i++){
            card= (ResourceCard) CardFactory.createPlayingCard("Resources",i, attributes);
            board1.addCard(card,cardOnBoard,4,owner.getCountSeed());
            cardOnBoard=card;
        }
        int [] countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }


    @Test
    void seedUpdateResourceCorner3Card() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={3,1,1,1,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,PURPLE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,3,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }
    @Test
    void seedUpdateResourceCorner2Card() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={3,1,1,1,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,PURPLE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,2,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }
    @Test
    void seedUpdateResourceCorner1Card() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={4,1,1,1,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,PURPLE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,1,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }
    @Test
    void seedUpdateResourceOn2CornersCard() throws InvalidPlaceException, ConditionsNotMetException {
        int[] countSeed;
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        int[] trueCount={6,3,2,0,0,0,0};
        Boolean[] c = {true,true,false,false};
        Corner cf1 = new Corner(1,EMPTY);
        Corner cf2 = new Corner(2,EMPTY);
        Corner cf3 = new Corner(3,EMPTY);
        Corner cf4 = new Corner(4,EMPTY);
        Corner cb1 = new Corner(1,EMPTY);
        Corner cb2 = new Corner(2,GREEN);
        Corner cb3 = new Corner(3,GREEN);
        Corner cb4 = new Corner(4,PURPLE);
        Corner[] cf ={cf1,cf2,cf3,cf4};
        Corner[] cb ={cb1,cb2,cb3,cb4};
        StartingCard cardToAdd=new StartingCard(1,c,cf,cb);
        cardToAdd.flip();
        //resource card to be added on it
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,GREEN);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        ResourceCard card_to_add = new ResourceCard(10,GREEN,cc,1);

        ResourceCard card_to_add1 = new ResourceCard(11,GREEN,cc,1);
        ResourceCard card_to_add2 = new ResourceCard(12,GREEN,cc,1);

        //putting starting card in the middle
        board1.addStartingCard(cardToAdd);
        board1.addCard(card_to_add,cardToAdd,4,owner.getCountSeed());
        board1.addCard(card_to_add1,cardToAdd,3,owner.getCountSeed());
        board1.addCard(card_to_add2,card_to_add1,4,owner.getCountSeed());

        countSeed=owner.getCountSeed();

        assertArrayEquals(trueCount,countSeed);
    }
}