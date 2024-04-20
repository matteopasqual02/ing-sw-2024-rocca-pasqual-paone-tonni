package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Game;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;

import java.util.ArrayList;
import java.util.List;
/*
this class handles the listeners of the player class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class PlayerListenersHandler extends ListenersHandler{

    public PlayerListenersHandler(){
        super();
    }
    public void resetPlayerListeners(List<GameListener> gameListeners){
        listeners=null;
        for(GameListener lis : gameListeners){
            addListener(lis);
        }
    }
    public void notify_setReadyToStart(Player p) {
        for(GameListener listener : listeners){
            listener.playerReady(p);
        }
    }
    public void notify_setIsConnected(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_drawGoals(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_chooseGoal(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_drawStarting(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_drawGoldFromDeck(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_drawResourceFromDeck(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_drawFromBoard(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_addStarting(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_setStartingCard(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_addToBoard(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_increasePoints(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_updateSeedCount(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
    public void notify_removeFromHand(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }
}
