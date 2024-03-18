package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private int idGame;
    private Queue<Player> players=new LinkedList<>();
    private int numberOfPlayer;
    private Queue<Player> winner =  new LinkedList<>();
    private Player firstPlayer;
    private boolean isLastRound;

    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;

    public Game(int idGame, Player p1, int numberOfPlayer ){
        this.idGame = idGame;
        players.add(p1);
        this.numberOfPlayer = numberOfPlayer;
        winner = null;
        firstPlayer = p1;
        isLastRound = false;

        //constructor DrawableDeck how to use json?

        ResourceCard[] rc = new ResourceCard[2];
        rc[0]= gameDrawableDeck.drawFirstResource();
        rc[1]= gameDrawableDeck.drawFirstResource();
        GoldCard[] gc = new GoldCard[2];
        gc[0]= gameDrawableDeck.drawFirstGold();
        gc[1]= gameDrawableDeck.drawFirstGold();
        ObjectiveCard[] oc = new ObjectiveCard[2];
        oc[0]= gameDrawableDeck.drawFirstObjective();
        oc[1]= gameDrawableDeck.drawFirstObjective();
        gameBoardDeck = new BoardDeck(rc,gc,oc);
    }

    public void addPlayer (Player px) throws GameAlreadyFullException{
        if(players.size() < numberOfPlayer){
            players.add(px);
        }
        else {
            throw new GameAlreadyFullException("The game is full");
        }

    }

    public void nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);
    }

    public int checkPlayerTotalPoint(Player px) {
        return px.getGoalPoints() + px.getCurrentPoints();
    }

    public void checkWinner(){
        int max=0;
        for (Player cplayer : players ){
            if(checkPlayerTotalPoint(cplayer) == max){
                winner.add(cplayer);
            }
            else if(checkPlayerTotalPoint(cplayer) > max) {
                winner.clear();
                winner.add(cplayer);
                max= checkPlayerTotalPoint(cplayer);
            }
        }
    }


}
