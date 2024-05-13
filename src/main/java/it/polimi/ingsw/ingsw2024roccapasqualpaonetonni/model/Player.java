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
    private final PlayerBoard board;
    private final List<PlayingCard> hand;
    private ObjectiveCard goal;
    private final ObjectiveCard[] firstGoals;
    private StartingCard startingCard;
    private final boolean readyToStart;
    private boolean connected;

    private GameListener myListener;
    private final PlayerListenersHandler playerListenersHandler;

    public Player(String name, int color){
        this.connected=true;
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
        if(goal==null && choice>=0 && choice<2){
            goal=firstGoals[choice];
            playerListenersHandler.notify_chooseGoal(this);
            return;
        }
        playerListenersHandler.notify_playerGenericError("Goal invalid Action");

    }
    public void drawStarting(DrawableDeck d) throws DeckEmptyException {
        startingCard=d.drawFirstStarting();
    }
    public void drawGoldFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstGold());
        playerListenersHandler.notify_drawGoldFromDeck(this,d);
    }
    public void drawResourcesFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstResource());
        playerListenersHandler.notify_drawResourceFromDeck(this,d);

    }
    public void drawFromBoard(int position, BoardDeck b) throws NoCardException {
        hand.add(b.draw(position));
        playerListenersHandler.notify_drawFromBoard(this,b,b.getDrawableDeck());

    }
    public int[] getCountSeed() {
        return countSeed;
    }

    public void addStarting(){
        for(PlayingCard[] playingCards: board.getBoardMatrix()){
            for (PlayingCard playingCard: playingCards){
                if(playingCard!=null){
                    playerListenersHandler.notify_playerGenericError(
                            "Starting Card invalid Action: Card Already Added"
                    );
                    return;
                }
            }
        }
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
            playerListenersHandler.notify_playerGenericError("Card not in Hand");
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
        }
        catch(InvalidPlaceException e) {
            hand.add(cardToAdd);
            playerListenersHandler.notify_playerGenericError("Card Invalid Place");
        }
        catch(ConditionsNotMetException e) {
            hand.add(cardToAdd);
            playerListenersHandler.notify_playerGenericError("Conditions not met");
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

        for(PlayingCard playingCard: hand){
            if(playingCard.getIdCard() == p.getIdCard()){
                hand.remove(playingCard);
                return;
            }
        }
        throw new CardNotInHandException("Card Doesn't exists in player hand");
    }


    //needed for testing
    public List<PlayingCard> getHand(){
        return hand;
    }
    public ObjectiveCard[] getObjectiveBeforeChoice(){
        return firstGoals;
    }


    public void setIsConnected(boolean b) {
        connected = b;
    }
}
