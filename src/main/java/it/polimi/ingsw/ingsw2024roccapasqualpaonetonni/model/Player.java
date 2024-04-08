package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.CardNotInHandException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;

import java.util.*;

public class Player {
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
    private boolean connected ;
    public Player(String name,int color){

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
    public void setReadyToStart(){
        readyToStart=true;
    }
    public Boolean getIsConnected(){
        return connected;
    }
    public void setIsConnected(Boolean b){
        connected = b;
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

    public void drawGoals(DrawableDeck d){
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
    }
    public void drawStarting(DrawableDeck d){
        startingCard=d.drawFirstStarting();
    }
    public void drawGoldFromDeck(DrawableDeck d){
        hand.add(d.drawFirstGold());
    }
    public void drawResourcesFromDeck(DrawableDeck d){
        hand.add(d.drawFirstResource());
    }
    public void drawFromBoard(int position, BoardDeck b, DrawableDeck d){
        hand.add(b.draw(position,d));
    }
    public int[] getCountSeed() {
        return countSeed;
    }

    public void addStarting(){
        board.addStartingCard(startingCard);
    }
    public void setStartingCard(StartingCard card){ this.startingCard=card;}
    public StartingCard getStartingCard(){return startingCard;}

    public void addToBoard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            removeFromHand(cardToAdd);
        }
        catch(CardNotInHandException e) {

        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
        }
        catch(InvalidPlaceException e) {

        }
        catch(ConditionsNotMetException e) {

        }
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
        }
        else {throw new CardNotInHandException("Card Doesn't exists in player hand");}
    }


    //needed for testing
    public List<PlayingCard> getHand(){
        return hand;
    }
    public ObjectiveCard[] getObjectiveBeforeChoice(){
        return firstGoals;
    }

}
