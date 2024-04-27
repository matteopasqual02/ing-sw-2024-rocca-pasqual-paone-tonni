package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.ListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.PlayerListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.CardNotInHandException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
    private final String nickname;
    private final int colorPlayer;
    private int currentPoints;
    private final int[] countSeed;
    private final PlayerBoard board;
    private final List<PlayingCard> hand;
    private ObjectiveCard goal;
    private final ObjectiveCard[] firstGoals;
    private StartingCard startingCard;
    private boolean readyToStart;
    private boolean connected;

    private GameListener myListener;
    private final PlayerListenersHandler playerListenersHandler;

    public Player(String name, int color){
        this.nickname=name;
        this.colorPlayer=color;
        this.currentPoints=0;
        this.countSeed = new int[7];
        this.board= new PlayerBoard(this);
        this.hand= new LinkedList<>();
        this.goal=null;
        this.firstGoals=new ObjectiveCard[2];
        this.startingCard=null;
        this.readyToStart = false;
        this.connected=true;
        playerListenersHandler = new PlayerListenersHandler();

    }

    public void setPlayerListeners(List<GameListener> currentGameListeners) {
        playerListenersHandler.resetPlayerListeners(currentGameListeners);
    }

    public void getListener() {
        return myListener;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }

    public int getColorPlayer(){
        return colorPlayer;
    }

    public Boolean getReadyToStart(){
        return readyToStart;
    }

    public Boolean getIsConnected(){
        return connected;
    }

    public void setIsConnected(Boolean b){
        connected = b;
        playerListenersHandler.notify_setIsConnected(this);
    }

    public String getNickname() {
        return nickname;
    }

    public ObjectiveCard getGoal(){
        return goal;
    }

    public PlayerBoard getBoard(){
        return board;
    }

    public void drawGoals(DrawableDeck d) throws DeckEmptyException{
        firstGoals[0]=d.drawFirstObjective();
        firstGoals[1]=d.drawFirstObjective();
        playerListenersHandler.notify_drawPersonalGoals(firstGoals,this);
    }
    public void chooseGoal(int choice){
        if(choice==0){
            goal=firstGoals[0];
        }
        else {
            goal=firstGoals[1];
        }
        playerListenersHandler.notify_chooseGoal(goal,this);
    }
    public void drawStarting(DrawableDeck d) throws DeckEmptyException {
        startingCard=d.drawFirstStarting();
        playerListenersHandler.notify_drawStarting(startingCard,this);
    }
    public void drawGoldFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstGold());
        playerListenersHandler.notify_drawGoldFromDeck(hand.getLast(),this);
    }
    public void drawResourcesFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstResource());
        playerListenersHandler.notify_drawResourceFromDeck(hand.getLast(),this);

    }
    public void drawFromBoard(int position, BoardDeck b) throws NoCardException {
        hand.add(b.draw(position));
        playerListenersHandler.notify_drawFromBoard(hand.getLast(),this);

    }
    public int[] getCountSeed() {
        return countSeed;
    }

    public void addStarting(){
        board.addStartingCard(startingCard);
        playerListenersHandler.notify_addStarting(board,this);
    }
    public void setStartingCard(StartingCard card){
        this.startingCard=card;
    }
    public StartingCard getStartingCard(){return startingCard;}

    public void addToBoard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            removeFromHand(cardToAdd);
        }
        catch(CardNotInHandException e) {
            playerListenersHandler.notify_cardNotInHand(cardToAdd,this);
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
            playerListenersHandler.notify_addToBoard(board,this);
        }
        catch(InvalidPlaceException e) {
            playerListenersHandler.notify_invalidPlace(this);
        }
        catch(ConditionsNotMetException e) {
            playerListenersHandler.notify_conditionsNotMet(this);
        }
    }

    public void increasePoints(int newPoints) {

        currentPoints = currentPoints + newPoints;
        playerListenersHandler.notify_increasePoints(currentPoints,this);
    }

    public void updateSeedCount(int[] change) {
        for (int i = 0; i < 7; i++) {
            countSeed[i]+= change[i];
        }
        playerListenersHandler.notify_updateSeedCount(countSeed,this);
    }

    private void removeFromHand(PlayingCard p) throws CardNotInHandException{
        if(hand.contains(p)){
            hand.remove(p);
            playerListenersHandler.notify_removeFromHand(p,this);
        }
        else {throw new CardNotInHandException("Card Doesn't exists in player hand");}
        playerListenersHandler.notify_cardNotInHand(p,this);
    }


    //needed for testing
    public List<PlayingCard> getHand(){
        return hand;
    }
    public ObjectiveCard[] getObjectiveBeforeChoice(){
        return firstGoals;
    }

}
