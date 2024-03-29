package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.CardNotInHandException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;

import java.util.*;

public class Player {
    private final String nickname;
    private final int colorPlayer;
    private int currentPoints;
    private int[] countSeed;
    private PlayerBoard board;
    private List<Card> hand;
    private ObjectiveCard goal;
    private ObjectiveCard[] firstGoals;
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
    public Boolean getreadytostart(){
        return readyToStart;
    }
    public void setReadyToStart(){
        readyToStart=true;
    }
    public Boolean getisconnected(){
        return connected;
    }
    public void setIsconnected(Boolean b){
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
    public void drawGoldfromDeck(DrawableDeck d){
        hand.add(d.drawFirstGold());
    }
    public void drawResourcesfromDeck(DrawableDeck d){
        hand.add(d.drawFirstResource());
    }
    public void drawfromBoard(int position, BoardDeck b, DrawableDeck d){
        hand.add(b.draw(position,d));
    }
    public int[] getCountSeed() {
        return countSeed;
    }

    public void addStarting(){
        board.addStartingCard(startingCard);
    }
    public StartingCard getStartingCard(){return startingCard;}
    public void setStartingCard(StartingCard card){this.startingCard=card;}

    public void addToBoard(ResourceCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            removeFromHand(cardToAdd);
        }
        catch(CardNotInHandException e) {;
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
        }
        catch(InvalidPlaceException e) {
            ;
        }
    }
    public void addToBoard(GoldCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            removeFromHand(cardToAdd);
        }
        catch(CardNotInHandException e) {
            ;
        }
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
        }
        catch(InvalidPlaceException e) {
            ;
        }
        catch(ConditionsNotMetException e) {
            ;
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
        else {throw new CardNotInHandException("Card Doesn't existsin player hand");}
    }

}
