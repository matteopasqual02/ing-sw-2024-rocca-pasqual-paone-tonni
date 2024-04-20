package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.List;
/*
this class handles the listeners of the game class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change that has occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class GameListenersHandler extends ListenersHandler{
    public GameListenersHandler(){
        super();
    }
    public void notify_setMaxNumPlayers(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_addPlayer(Game model) {
        for(GameListener listener : listeners){
            listener.fullGame(new GameImmutable(model));
        }
    }
    public void notify_gameFull(Game model) {
        for(GameListener listener : listeners){
            listener.fullGame(new GameImmutable(model));
        }
    }
    public void notify_playerAlredyIn(Game model) {
        for(GameListener listener : listeners){
            listener.nameAlreadyInGame(new GameImmutable(model));
        }
    }
    public void notify_removePlayer(Game model) {
        for(GameListener listener : listeners){
            listener.removedPlayer(new GameImmutable(model));
        }
    }
    public void notify_reconnectPlayer(Game model) {
        for(GameListener listener : listeners){
            listener.reconnectedPlayer(new GameImmutable(model));
        }
    }
    public void notify_reconnectionImpossible(Game model) {
        for(GameListener listener : listeners){
            //listener.reconnectionImpossible(new GameImmutable(model));
        }
    }
    public void notify_disconnectedPlayer(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_disconnectionImpossible(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setFirstPlayer(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setStatus(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setLastStatus(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_resetLastStatus(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_nextPlayer(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_lastTurn(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_playerIsReadyToStart(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setGameDrawableDeck(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setGameBoardDeck(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }

}
