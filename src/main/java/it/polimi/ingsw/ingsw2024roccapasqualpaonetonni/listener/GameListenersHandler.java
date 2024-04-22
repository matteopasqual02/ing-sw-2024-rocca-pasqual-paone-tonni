package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;

import java.io.Serializable;
import java.util.List;
/*
this class handles the listeners of the game class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change that has occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class GameListenersHandler extends ListenersHandler implements Serializable {
    public GameListenersHandler(){
        super();
    }

    public void notify_setMaxNumPlayers(int gameId,int max) {
        for(GameListener listener : listeners){
            listener.maxNumPlayersSet(gameId,max);
        }
    }

    public void notify_addPlayer(Player newPlayer) {
        for(GameListener listener : listeners){
            listener.addedPlayer(newPlayer);
        }
    }

    public void notify_gameFull() {
        for(GameListener listener : listeners){
            listener.fullGame();
        }
    }

    public void notify_playerAlredyIn() {
        for(GameListener listener : listeners){
            listener.nameAlreadyInGame();
        }
    }
    public void notify_removePlayer(Player p) {
        for(GameListener listener : listeners){
            listener.playerRemoved(p);
        }
    }
    public void notify_reconnectPlayer(String nickname) {
        for(GameListener listener : listeners){
            listener.reconnectedPlayer(nickname);
        }
    }
    public void notify_reconnectionImpossible(String nickname) {
        for(GameListener listener : listeners){
            listener.reconnectionImpossible(nickname);
        }
    }
    public void notify_disconnectedPlayer(String nickname) {
        for(GameListener listener : listeners){
            listener.disconnectedPlayer(nickname);
        }
    }
    public void notify_disconnectionImpossible(String nickname) {
        for(GameListener listener : listeners){
            listener.disconnectionImpossible(nickname);
        }
    }
    public void notify_setFirstPlayer(Player first) {
        for(GameListener listener : listeners){
            listener.firstPlayerSet(first);
        }
    }
    public void notify_setStatus(GameStatus status) {
        for(GameListener listener : listeners){
            listener.statusSet(status);
        }
    }
    public void notify_setLastStatus(GameStatus status) {
        for(GameListener listener : listeners){
            listener.statusSetToLastStatus(status);
        }
    }
    public void notify_resetLastStatus() {
        for(GameListener listener : listeners){
            listener.lastStatusReset();
        }
    }
    public void notify_nextPlayer(Player p) {
        for(GameListener listener : listeners){
            listener.nextTurn(p);
        }
    }
    public void notify_lastTurn() {
        for(GameListener listener : listeners){
            listener.lastTurn();
        }
    }
    public void notify_playerIsReadyToStart(Player p) {
        for(GameListener listener : listeners){
            listener.playerIsReady(p);
        }
    }
    public void notify_setGameDrawableDeck(DrawableDeck d) {
        for(GameListener listener : listeners){
            listener.drawableDeckSet(d);
        }
    }
    public void notify_setGameBoardDeck(BoardDeck bd) {
        for(GameListener listener : listeners){
            listener.boardDeckSet(bd);
        }
    }

}
