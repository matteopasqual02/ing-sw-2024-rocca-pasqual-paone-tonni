package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private Queue<Player> players;
    private int numberOfPlayer;
    private Queue<Player> winner;
    private Player firstPlayer;
    private boolean isLastRound;

    private GameStatus status;

    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;

    public Game(){
        players = new LinkedList<>();
        this.numberOfPlayer = 0;
        winner = new LinkedList<>();
        firstPlayer = null;
        isLastRound = false;
        status = GameStatus.PREPARATION;

        gameDrawableDeck = new DrawableDeck();
    }

//         ResourceCard[] rc = new ResourceCard[2];
//        rc[0]= gameDrawableDeck.drawFirstResource();
//        rc[1]= gameDrawableDeck.drawFirstResource();
//        GoldCard[] gc = new GoldCard[2];
//        gc[0]= gameDrawableDeck.drawFirstGold();
//        gc[1]= gameDrawableDeck.drawFirstGold();
//        ObjectiveCard[] oc = new ObjectiveCard[2];
//        oc[0]= gameDrawableDeck.drawFirstObjective();
//        oc[1]= gameDrawableDeck.drawFirstObjective();
//        gameBoardDeck = new BoardDeck(rc,gc,oc);

    private void createCard(DrawableDeck gameDrawableDeck){
        //json...
    }

    public void addPlayer (Player px) throws GameAlreadyFullException{
        if(players.size() < numberOfPlayer){
            players.add(px);
        }
        else {
            throw new GameAlreadyFullException("The game is full");
        }

    }

    public Player nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);

        return players.peek();
    }

/*    public void checkWinner(){
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
*/

}
