package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.*;

import java.util.*;

public class Game {
    private final int gameId;
    private final GameStatus[] status; //current and previous status needed for reconnection
    private int maxNumberOfPlayer;
    private final Queue<Player> players;
    private final Queue<Player> winner;
    private Player firstPlayer;
    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;
    private final Chat chat;

    public Game(int id){
        players = new LinkedList<>();
        winner = new LinkedList<>();
        firstPlayer = null;
        status = new GameStatus[2];
        status[0] = GameStatus.PREPARATION;
        status[1] = GameStatus.PREPARATION;
        this.maxNumberOfPlayer=0;
        this.gameId = id;

        gameBoardDeck = null;
        gameDrawableDeck = null;

        chat = new Chat();
    }

//---------------------------------PLAYER SECTION
    public int getGameId(){return  gameId;}
    public void setMaxNumberOfPlayer(int number){
        this.maxNumberOfPlayer=number;
    }
    public int getMaxNumberOfPlayer(){
        return maxNumberOfPlayer;
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
        if(status[0].equals(GameStatus.RUNNING) || status[0].equals(GameStatus.LAST_TURN)){
            status[0] = GameStatus.ENDED;
        }
    }
    public void reconnectPlayer(String nickname) {
        Player p = players.stream().filter(player -> Objects.equals(player.getNickname(), nickname)).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(true);
        }
        else {

        }
    }
    public void disconnectPlayer(String nickname) {
        Player p = players.stream().filter(player -> Objects.equals(player.getNickname(), nickname)).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(false);
        }
        else {

        }
    }
    public void setFirstPlayer(Player fp){
        this.firstPlayer=fp;
    }
    public void setStatus(GameStatus status) {
        this.status[0] = status;
    }
    public void setLastStatus() {
        status[1] =status[0];
    }
    public void resetLastStatus() {
        status[1] = null;
    }
    public GameStatus getGameStatus(){
        return status[0];
    }
    public GameStatus getLastStatus(){
        return status[1];
    }
    public Queue<Player> getPlayers() {
        return players;
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
    public void nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);
        Player newCurrent = players.peek();
        if (newCurrent != null && newCurrent.equals(firstPlayer) && status[0].equals(GameStatus.WAITING_LAST_TURN)) {
            status[0] = GameStatus.LAST_TURN;
        }
    }

//---------------------------------POINT SECTION
    public int checkPlayerTotalPoint(Player p){
        return p.getCurrentPoints()
                + p.getGoal().pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective(0).pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective(1).pointCard(p.getBoard())
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

    public Queue<Player> getWinners(){
        return winner;
    }

//---------------------------------READY SECTION
    public void playerIsReadyToStart(Player p){
        p.setReadyToStart();
    }
    public Boolean arePlayerReady(){
        return players.stream().filter(Player::getReadyToStart).count() == players.size()
                && players.size() == maxNumberOfPlayer;
    }

    public void setGameDrawableDeck(DrawableDeck deck) {
        this.gameDrawableDeck = deck;
    }

    public void setGameBoardDeck(BoardDeck deck) {
        this.gameBoardDeck = deck;
    }



}
