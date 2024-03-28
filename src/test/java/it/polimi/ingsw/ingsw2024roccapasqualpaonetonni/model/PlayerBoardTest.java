package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.EMPTY;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
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


}