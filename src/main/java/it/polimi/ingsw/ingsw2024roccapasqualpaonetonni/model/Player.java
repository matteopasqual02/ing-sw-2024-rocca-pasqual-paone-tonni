package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.PlayerListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Player.
 */
public class Player implements Serializable {
    /**
     * The Nickname.
     */
    private final String nickname;
    /**
     * The Color player.
     */
    private final int colorPlayer;
    /**
     * The Current points.
     */
    private int currentPoints;
    /**
     * The Count seed.
     */
    private final int[] countSeed;
    /**
     * The Board.
     */
    private final PlayerBoard board;
    /**
     * The Hand.
     */
    private final List<PlayingCard> hand;
    /**
     * The Goal.
     */
    private ObjectiveCard goal;
    /**
     * The First goals.
     */
    private final ObjectiveCard[] firstGoals;
    /**
     * The Starting card.
     */
    private StartingCard startingCard;
    /**
     * The Ready to start.
     */
    private final boolean readyToStart;
    /**
     * The Player listeners handler.
     */
    private final PlayerListenersHandler playerListenersHandler;

    /**
     * Instantiates a new Player.
     *
     * @param name  the name
     * @param color the color
     */
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

    /**
     * Sets player listeners.
     *
     * @param currentGameListeners the current game listeners
     */
    public void setPlayerListeners(HashMap<String, NotifierInterface> currentGameListeners) {
        synchronized (playerListenersHandler) {
            playerListenersHandler.resetPlayerListeners(currentGameListeners);
        }
    }

    /**
     * Gets current points.
     *
     * @return the current points
     */
    public int getCurrentPoints() {
        return currentPoints;
    }

    /**
     * Get color player int.
     *
     * @return the int
     */
    public int getColorPlayer(){
        return colorPlayer;
    }

    /**
     * Get ready to start boolean.
     *
     * @return the boolean
     */
    public Boolean getReadyToStart(){
        return readyToStart;
    }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get goal objective card.
     *
     * @return the objective card
     */
    public ObjectiveCard getGoal(){
        return goal;
    }

    /**
     * Get board player board.
     *
     * @return the player board
     */
    public PlayerBoard getBoard(){
        return board;
    }

    /**
     * Draw goals.
     *
     * @param d the d
     * @throws DeckEmptyException the deck empty exception
     */
    public void drawGoals(DrawableDeck d) throws DeckEmptyException{
        firstGoals[0]=d.drawFirstObjective();
        firstGoals[1]=d.drawFirstObjective();
    }

    /**
     * Choose goal.
     *
     * @param choice the choice
     */
    public void chooseGoal(int choice){
        if(goal==null && choice>=0 && choice<2){
            goal=firstGoals[choice];
            playerListenersHandler.notify_chooseGoal(this);
            return;
        }
        playerListenersHandler.notify_playerGenericError("Goal invalid Action");

    }

    /**
     * Draw starting.
     *
     * @param d the d
     * @throws DeckEmptyException the deck empty exception
     */
    public void drawStarting(DrawableDeck d) throws DeckEmptyException {
        startingCard=d.drawFirstStarting();
    }

    /**
     * Draw gold from deck.
     *
     * @param d the d
     * @throws DeckEmptyException the deck empty exception
     */
    public void drawGoldFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstGold());
        playerListenersHandler.notify_drawGoldFromDeck(this,d);
    }

    /**
     * Draw resources from deck.
     *
     * @param d the d
     * @throws DeckEmptyException the deck empty exception
     */
    public void drawResourcesFromDeck(DrawableDeck d) throws DeckEmptyException {
        hand.add(d.drawFirstResource());
        playerListenersHandler.notify_drawResourceFromDeck(this,d);

    }

    /**
     * Draw from board.
     *
     * @param position the position
     * @param b        the b
     * @throws NoCardException the no card exception
     */
    public void drawFromBoard(int position, BoardDeck b) throws NoCardException {
        hand.add(b.draw(position));
        playerListenersHandler.notify_drawFromBoard(this,b,b.getDrawableDeck());

    }

    /**
     * Get count seed int [ ].
     *
     * @return the int [ ]
     */
    public int[] getCountSeed() {
        return countSeed;
    }

    /**
     * Add starting.
     */
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

    /**
     * Set starting card.
     *
     * @param card the card
     */
    public void setStartingCard(StartingCard card){
        this.startingCard=card;
    }

    /**
     * Get starting card.
     *
     * @return the starting card
     */
    public StartingCard getStartingCard(){return startingCard;}

    /**
     * Add to board boolean.
     *
     * @param cardToAdd      the card to add
     * @param cardOnBoard    the card on board
     * @param cornerToAttach the corner to attach
     * @return the boolean
     */
    public boolean addToBoard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            removeFromHand(cardToAdd);
        }
        catch(CardNotInHandException e) {
            playerListenersHandler.notify_playerGenericError("Card not in Hand");
            return false;
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
            playerListenersHandler.notify_addToBoard(this);
        }
        catch(InvalidPlaceException e) {
            hand.add(cardToAdd);
            playerListenersHandler.notify_playerGenericError("Card Invalid Place");
            return false;
        }
        catch(ConditionsNotMetException e) {
            hand.add(cardToAdd);
            playerListenersHandler.notify_playerGenericError("Conditions not met");
            return false;
        }

        return true;
    }

    /**
     * Increase points.
     *
     * @param newPoints the new points
     */
    public void increasePoints(int newPoints) {
        currentPoints = currentPoints + newPoints;
    }

    /**
     * Update seed count.
     *
     * @param change the change
     */
    public void updateSeedCount(int[] change) {
        for (int i = 0; i < 7; i++) {
            countSeed[i]+= change[i];
        }
    }

    /**
     * Remove from hand.
     *
     * @param p the p
     * @throws CardNotInHandException the card not in hand exception
     */
    private void removeFromHand(PlayingCard p) throws CardNotInHandException{

        for(PlayingCard playingCard: hand){
            if(playingCard.getIdCard() == p.getIdCard()){
                hand.remove(playingCard);
                return;
            }
        }
        throw new CardNotInHandException("Card Doesn't exists in player hand");
    }


    /**
     * Get hand list.
     *
     * @return the list
     */
//needed for testing
    public List<PlayingCard> getHand(){
        return hand;
    }

    /**
     * Get objective before choice objective card [ ].
     *
     * @return the objective card [ ]
     */
    public ObjectiveCard[] getObjectiveBeforeChoice(){
        return firstGoals;
    }



}
