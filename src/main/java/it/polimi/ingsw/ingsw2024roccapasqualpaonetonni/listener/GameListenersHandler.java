package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ConnectionType;

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

    public void notify_setMaxNumPlayers(int gameId, int max) {
        for(GameListener listener: listenersMap.keySet()){
            listenersMap.get(listener).sendMaxNumPlayersSet(gameId,max);
        }
    }

    public void notify_createdGame(int gameId) {
        for(GameListener listener: listenersMap.keySet()){
            listenersMap.get(listener).sendCreatedGame(gameId);
        }
    }

    public void notify_addPlayer(Player newPlayer, String pNickname, int gameId) {
        for(GameListener listener : listenersMap.keySet()) {
            if (listener.equals(newPlayer)) {
                listenersMap.get(listener).sendYouJoinedGame(gameId, pNickname);
            }
            else {
                listenersMap.get(listener).sendAddedNewPlayer(pNickname);
            }
        }
    }

    public void notify_noAvailableGame(Player player) {
        for(GameListener listener : listenersMap.keySet()) {
            if (listener.equals(player)) {
                listenersMap.get(listener).sendNoAvailableGame();
            }
        }
    }

    public void notify_gameFull(Player player) {
        for(GameListener listener : listenersMap.keySet()) {
            if (listener.equals(player)) {
                listenersMap.get(listener).sendFullGame();
            }
        }
    }

    public void notify_playerAlredyIn(Player player) {
        for(GameListener listener : listenersMap.keySet()) {
            if (listener.equals(player)) {
                listenersMap.get(listener).sendNameAlreadyInGame();
            }
        }
    }

    public void notify_askPlayersReady() {
        for(GameListener listener : listenersMap.keySet()) {
            listenersMap.get(listener).sendAskPlayersReady();
        }
    }

    public void notify_removePlayer(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendPlayerRemoved(p);
        }
    }
    public void notify_reconnectPlayer(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendReconnectedPlayer(nickname);
        }
    }
    public void notify_reconnectionImpossible(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendReconnectionImpossible(nickname);
        }
    }
    public void notify_disconnectedPlayer(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendDisconnectedPlayer(nickname);
        }
    }
    public void notify_disconnectionImpossible(String nickname) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendDisconnectedPlayer(nickname);
        }
    }
    public void notify_setFirstPlayer(Player first) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendFirstPlayerSet(first);
        }
    }
    public void notify_setStatus(GameStatus status) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendStatusSet(status);
        }
    }
    public void notify_setLastStatus(GameStatus status) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendStatusSetToLastStatus(status);
        }
    }
    public void notify_resetLastStatus() {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendLastStatusReset();
        }
    }
    public void notify_nextPlayer(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendNextTurn(p);
        }
    }
    public void notify_lastTurn() {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendLastTurn();
        }
    }
    public void notify_playerIsReadyToStart(Player p) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendPlayerIsReady(p);
        }
    }
    public void notify_setGameDrawableDeck(DrawableDeck d) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendDrawableDeckSet(d);
        }
    }
    public void notify_setGameBoardDeck(BoardDeck bd) {
        for(GameListener listener : listenersMap.keySet()){
            listenersMap.get(listener).sendBoardDeckSet(bd);
        }
    }

}
