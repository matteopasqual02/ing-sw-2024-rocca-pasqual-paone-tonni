package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.List;

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
