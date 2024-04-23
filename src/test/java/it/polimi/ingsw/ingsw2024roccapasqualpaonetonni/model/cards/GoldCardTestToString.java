package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Corner;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.*;

class GoldCardTestToString {

    @Test
    void TestGold3 () {
        Corner c1 = new Corner(1,null);
        Corner c2 = new Corner(2,null);
        Corner c3 = new Corner(3,null);
        Corner c4 = new Corner(4,null);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {2,1,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,1,"feather",placeCond);

        String carta= card_to_add1.toString(false);
        System.out.println(carta);
    }

    @Test
    void TestGold4 () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {3,1,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        String carta= card_to_add1.toString(false);
        System.out.println(carta);
    }

    @Test
    void TestGold5 () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {5,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        String carta= card_to_add1.toString(false);
        System.out.println(carta);
    }

    @Test
    void TestGold3R () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {2,1,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        String carta= card_to_add1.toString(true);
        System.out.println(carta);
    }

    @Test
    void TestGold4R () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {3,1,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        String carta= card_to_add1.toString(true);
        System.out.println(carta);
    }

    @Test
    void TestGold5R () {
        Corner c1 = new Corner(1,BLUE);
        Corner c2 = new Corner(2,GREEN);
        Corner c3 = new Corner(3,RED);
        Corner c4 = new Corner(4,RED);
        Corner[] cc ={c1,c2,c3,c4};
        int[] placeCond = {5,0,0,0};
        GoldCard card_to_add1 = new GoldCard(50,GREEN,cc,3,null,placeCond);

        String carta= card_to_add1.toString(true);
        System.out.println(carta);
    }

}