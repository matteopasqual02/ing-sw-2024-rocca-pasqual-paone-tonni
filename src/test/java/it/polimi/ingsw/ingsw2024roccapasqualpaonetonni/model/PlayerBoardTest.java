package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class PlayerBoardTest {

    @Test
    void PlayerGetTest(){
        Player owner=new Player("a",1);
        PlayerBoard board1 = new PlayerBoard(owner);
        assertEquals(owner,board1.getPlayer());
    }
    /*void addCardTest(){
        ResourceCard cardToAdd=new ResourceCard(1,1,);
        PlayingCard cardOnBoard;

    }*/


}