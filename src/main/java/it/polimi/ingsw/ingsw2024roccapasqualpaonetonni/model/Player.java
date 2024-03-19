package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.ConditionsNotMetException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;

public class Player {
    private String nickname;
    private int colorPlayer;
    private int currentPoints;
    private int[] countSeed =new int[7];
    private PlayerBoard board;
    private PlayingCard[] hand =new PlayingCard[3];
    private ObjectiveCard goal;
    private ObjectiveCard[] firstGoals=new ObjectiveCard[2];
    private StartingCard startingCard;
    public Player(String name,int color){

        this.nickname=name;
        this.colorPlayer=color;
        this.currentPoints=0;
        this.countSeed=null;
        //this.board= new PlayerBoard();
        this.hand=null;
        this.goal=null;
        this.firstGoals=null;
        this.startingCard=null;

    }

    public int getCurrentPoints() {
        return currentPoints;
    }
    public int getColorPlayer(){
        return colorPlayer;
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

    public int[] getCountSeed() {
        return countSeed;
    }

    public void addToBoard(ResourceCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach) {
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, countSeed);
        }
        catch(InvalidPlaceException e) {
            ;
        }
    }
    public void addToBoard(GoldCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, int[] seedCount) {
        try {
            board.addCard(cardToAdd, cardOnBoard, cornerToAttach, seedCount);
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

   //manca il mettere una carta nella board e poi ripescarla dai drawable deck per sostituire la carta mancante nella mano + contare i punti
}
