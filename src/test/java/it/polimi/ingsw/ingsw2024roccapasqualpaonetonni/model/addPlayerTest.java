package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class addPlayerTest {

    @Test
    void addPlayerDimension0() { //test che valuta l'aggiunta con dimensione 0 iniziale
        Game game= new Game();
        Player p=new Player("giocatore",1);
        assertThrows(GameAlreadyFullException.class,
                ()->{
            game.addPlayer(p);
                });
    }
    @Test
    //valuta che non si puo aggiungere 2 volte lo stesso player
    void addSamePlayer() throws GameAlreadyFullException, PlayerAlreadyInException {
        Game game=new Game();
        Player p1= new Player("p",1);
        game.setNumberOfPlayer(2);
        game.addPlayer(p1);
        assertThrows(PlayerAlreadyInException.class,
                ()->{
            game.addPlayer(p1);
                });

    }
    @Test
    void addTooManyPlayers() throws GameAlreadyFullException, PlayerAlreadyInException {
        Game game=new Game();
        Player p1= new Player("p",1);
        Player p2= new Player("p2",2);
        game.setNumberOfPlayer(1);
        game.addPlayer(p1);
        assertThrows(GameAlreadyFullException.class,
                ()->{
                    game.addPlayer(p2);
                });
    }
    }
