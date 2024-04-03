package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.*;

import java.util.*;

public class Game {
    private GameStatus status;
    private int maxNumberOfPlayer;
    private Queue<Player> players;
    private Queue<Player> winner;
    private Player firstPlayer;
    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;
    private Chat chat;

    public Game(){
        players = new LinkedList<>();
        winner = new LinkedList<>();
        firstPlayer = null;
        status = GameStatus.PREPARATION;
        this.maxNumberOfPlayer=0;

        gameBoardDeck = null;
        gameDrawableDeck = null;

        chat = new Chat();
    }
    public void setNumberOfPlayer(int number){
        this.maxNumberOfPlayer=number;
    }
    public void addPlayer (Player px) throws GameAlreadyFullException, PlayerAlreadyInException {
        if(!players.contains(px)){
            if(players.size() < maxNumberOfPlayer){
                players.add(px);
            }
            else {
                throw new GameAlreadyFullException("The game is full");
            }
        }
        else {
            throw new PlayerAlreadyInException("The player is alrady in");
        }

    }
    public void removePlayer(Player p){
        players.remove(p);
        if(status.equals(GameStatus.RUNNING) || status.equals(GameStatus.LAST_TURN)){
            status = GameStatus.ENDING;
        }
    }
    public void setFirstPlayer(Player fp){
        this.firstPlayer=fp;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }
    public GameStatus getStatus(){
        return status;
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
        return gameBoardDeck;
    }
    public Player nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);

        return players.peek();
    }

//---------------------------------POINT SECTION
    public int checkPlayerTotalPoint(Player p){

        return p.getCurrentPoints()
                + p.getGoal().pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective()[0].pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective()[1].pointCard(p.getBoard())
                ;
    }
    public void checkWinner(){
       int max=0;
        for (Player cplayer : players ){
            int p_point = checkPlayerTotalPoint(cplayer);
            //2 players with equal point
            if(p_point == max){
                winner.add(cplayer);
            }
            //winner
            else if(p_point > max) {
                winner.clear();
                winner.add(cplayer);
                max= p_point;
            }
        }
    }

//---------------------------------READY SECTION
    public void playerIsReadyToStart(Player p){
        p.setReadyToStart();
    }
    public Boolean arePlayerReady(){
        return players.stream().filter(Player::getreadytostart).count() == players.size()
                && players.size() == maxNumberOfPlayer;
    }
}
