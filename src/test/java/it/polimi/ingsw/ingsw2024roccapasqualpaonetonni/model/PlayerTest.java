package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;

    @Test
    void playerTest() {
        int color = 1;
        player = new Player("fede", color);

        assertNotNull(player);
        assertEquals(color, player.getColorPlayer());
        assertFalse(player.getReadyToStart());
    }
}