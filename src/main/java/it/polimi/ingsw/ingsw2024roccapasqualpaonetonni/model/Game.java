package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;

import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private GameStatus status;
    private int maxNumberOfPlayer;
    private Queue<Player> players;
    private Queue<Player> winner;
    private Player firstPlayer;
    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;

    public Game(){
        players = new LinkedList<>();
        winner = new LinkedList<>();
        firstPlayer = null;
        status = GameStatus.PREPARATION;
        this.maxNumberOfPlayer=0;

        gameBoardDeck = null;
        gameDrawableDeck = null;
    }
    public void setNumberOfPlayer(int number){
        this.maxNumberOfPlayer=number;
    }
    public void addPlayer (Player px) throws GameAlreadyFullException{
        if(players.size() < maxNumberOfPlayer){
            players.add(px);
        }
        else {
            throw new GameAlreadyFullException("The game is full");
        }

    }

    public void setFirstPlayer(Player fp){
        this.firstPlayer=fp;
    }

    public Queue<Player> getPlayers() {
        return players;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }
    public Player getCurrentPlayer(){
        return players.peek();
    }

    public DrawableDeck getGameDrawableDeck(){
        return gameDrawableDeck;
    }
    public BoardDeck getGameBoardDeck(){
        return  gameBoardDeck;
    }

    public Player nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);

        return players.peek();
    }

    public int checkPlayerTotalPoint(Player p){

        return p.getCurrentPoints(); // + funzione per i punti aggiuntivi
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

    public void createTable(){
        //create cards in the game
        gameBoardDeck = new BoardDeck();
        gameDrawableDeck = new DrawableDeck();
    }


}
