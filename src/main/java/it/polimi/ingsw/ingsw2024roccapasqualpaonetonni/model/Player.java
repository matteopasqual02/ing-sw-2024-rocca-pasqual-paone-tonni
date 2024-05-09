package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
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
    private PlayerBoard board;
    private List<PlayingCard> hand;
    private ObjectiveCard goal;
    private final ObjectiveCard[] firstGoals;
    private StartingCard startingCard;
    private boolean readyToStart;

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
        playerListenersHandler = new PlayerListenersHandler();

    }

    public void setPlayerListeners(HashMap<GameListener, NotifierInterface> currentGameListeners) {
        playerListenersHandler.resetPlayerListeners(currentGameListeners);
    }

    public GameListener getListener() {
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
    }
    public void chooseGoal(int choice){
        if(choice==0){
            goal=firstGoals[0];
        }
        else {
            goal=firstGoals[1];
        }
        playerListenersHandler.notify_chooseGoal(this);
    }
    public void drawStarting(DrawableDeck d) throws DeckEmptyException {
        startingCard=d.drawFirstStarting();
        playerListenersHandler.notify_drawStarting(this);
    }
    public void drawGoldFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstGold());
        playerListenersHandler.notify_drawGoldFromDeck(this);
    }
    public void drawResourcesFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstResource());
        playerListenersHandler.notify_drawResourceFromDeck(this);

    }
    public void drawFromBoard(int position, BoardDeck b) throws NoCardException {
        hand.add(b.draw(position));
        playerListenersHandler.notify_drawFromBoard(this);

    }
    public int[] getCountSeed() {
        return countSeed;
    }

    public void addStarting(){
        board.addStartingCard(startingCard);
        playerListenersHandler.notify_addStarting(this);
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
            playerListenersHandler.notify_cardNotInHand(this);
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);

        }
        catch(InvalidPlaceException e) {
            playerListenersHandler.notify_invalidPlace(this);
        }
        catch(ConditionsNotMetException e) {
            playerListenersHandler.notify_conditionsNotMet(this);
        }
        playerListenersHandler.notify_addToBoard(this);
    }

    public void increasePoints(int newPoints) {
        currentPoints = currentPoints + newPoints;
    }

    public void updateSeedCount(int[] change) {
        for (int i = 0; i < 7; i++) {
            countSeed[i]+= change[i];
        }
    }

    private void removeFromHand(PlayingCard p) throws CardNotInHandException{
        if(hand.contains(p)){
            hand.remove(p);
            playerListenersHandler.notify_removeFromHand(this);
        }
        else {
            playerListenersHandler.notify_cardNotInHand(this);
            throw new CardNotInHandException("Card Doesn't exists in player hand");
        }

    }


    //needed for testing
    public List<PlayingCard> getHand(){
        return hand;
    }
    public ObjectiveCard[] getObjectiveBeforeChoice(){
        return firstGoals;
    }


}
