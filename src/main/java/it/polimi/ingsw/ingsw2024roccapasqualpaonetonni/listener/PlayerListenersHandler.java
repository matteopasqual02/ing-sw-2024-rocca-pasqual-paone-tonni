package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;

import java.io.Serializable;
import java.util.List;
/*
this class handles the listeners of the player class in the model: the listeners are elements related to each client, when a change
occurs in the model (the controller does something) one of these methods gets called in order to show the
change to each of the clients. These methods call other methods on the actual single listener giving it the change occurred

these are not hte methods that directly notify the clients, they are the ones that call those methods on all of the clients.
 */
public class PlayerListenersHandler extends ListenersHandler implements Serializable {

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
    public void notify_setIsConnected(Player p) {
        for(GameListener listener : listeners){
            listener.playerIsConnected(p);
        }
    }
    public void notify_drawPersonalGoals(ObjectiveCard[] goals, Player p) {
        for(GameListener listener : listeners){
            listener.drewPersonalGoals(goals,p);
        }
    }
    public void notify_chooseGoal(ObjectiveCard goal, Player p) {
        for(GameListener listener : listeners){
            listener.personalGoalChosen(goal, p);
        }
    }
    public void notify_drawStarting(StartingCard card, Player p) {
        for(GameListener listener : listeners){
            listener.startingCardDrew(card,p);
        }
    }
    public void notify_drawGoldFromDeck(PlayingCard card, Player p) {
        for(GameListener listener : listeners){
            listener.goldDrawn(card,p);
        }
    }
    public void notify_drawResourceFromDeck(PlayingCard card, Player p) {
        for(GameListener listener : listeners){
            listener.resourceDrawn(card,p);
        }
    }
    public void notify_drawFromBoard(PlayingCard card, Player p) {
        for(GameListener listener : listeners){
            listener.drewFromBoard(card, p);
        }
    }
    public void notify_addStarting(PlayerBoard board, Player p) {
        for(GameListener listener : listeners){
            listener.startAdded(board,p);
        }
    }
    /*public void notify_setStartingCard(Game model) {
        for(GameListener listener : listeners){
            listener.playerJoined(new GameImmutable(model));
        }
    }*/
    public void notify_cardNotInHand(PlayingCard card, Player p) {
        for(GameListener listener : listeners){
            listener.cardNotInHand(card,p);
        }
    }
    public void notify_addToBoard(PlayerBoard board, Player p) {
        for(GameListener listener : listeners){
            listener.cardAdded(board,p);
        }
    }
    public void notify_invalidPlace(Player p){
        for(GameListener listener : listeners){
            listener.choseInvalidPlace(p);
        }
    }
    public void notify_conditionsNotMet(Player p){
        for(GameListener listener : listeners){
            listener.conditionsNotMet(p);
        }
    }
    public void notify_increasePoints(int points,Player p) {
        for(GameListener listener : listeners){
            listener.pointsIncreased(points,p);
        }
    }
    public void notify_updateSeedCount(int[] countSeed,Player p) {
        for(GameListener listener : listeners){
            listener.seedCountUpdated(countSeed,p);
        }
    }
    public void notify_removeFromHand(PlayingCard card,Player p) {
        for(GameListener listener : listeners){
            listener.cardRemovedFromHand(card,p);
        }
    }
}
