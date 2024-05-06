package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class Game implements Serializable {
    private final int gameId;
    private final GameStatus[] status; //current and previous status needed for reconnection
    private int maxNumberOfPlayer;
    private final Queue<Player> players;
    private final List<Player> playersDisconnected;
    private final Queue<Player> winner;
    private Player firstPlayer;
    private BoardDeck gameBoardDeck;
    private DrawableDeck gameDrawableDeck;
    private final Chat chat;
    private final List<String> ready;

    private final GameListenersHandler gameListenersHandler;

    public Game(int id){
        players = new LinkedList<>();
        winner = new LinkedList<>();
        playersDisconnected = new LinkedList<>();
        firstPlayer = null;
        status = new GameStatus[2];
        status[0] = GameStatus.PREPARATION;
        status[1] = null;
        this.maxNumberOfPlayer=0;
        this.gameId = id;
        ready = new ArrayList<>();

        gameBoardDeck = null;
        gameDrawableDeck = null;

        chat = new Chat();
        gameListenersHandler = new GameListenersHandler();
    }

    public void addListeners(GameListener me, NotifierInterface notifier){
        try {
            gameListenersHandler.addListener(me, notifier);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeListener(GameListener me){
        synchronized (gameListenersHandler) {
            gameListenersHandler.removeListener(me);
            /*for(Player p: players){
                p.setPlayerListeners(gameListenersHandler.getListener());
            }*/
        }
    }

//---------------------------------PLAYER SECTION
    public int getGameId(){
        return gameId;
    }

    public void setMaxNumberOfPlayer(int number){
        this.maxNumberOfPlayer = number;
        gameListenersHandler.notify_setMaxNumPlayers(gameId,maxNumberOfPlayer);
    }

    public int getMaxNumberOfPlayer(){
        return maxNumberOfPlayer;
    }

    public synchronized void addPlayer(Player px) throws GameAlreadyFullException, PlayerAlreadyInException {
        if (!players.contains(px)) {
            if (players.size() < maxNumberOfPlayer) {
                players.add(px);
                /*for(Player p: players){
                    p.setPlayerListeners(gameListenersHandler.getListener());
                }*/
                if (players.size() == 1) {
                    gameListenersHandler.notify_createdGame(this.gameId);
                }
                gameListenersHandler.notify_addPlayer(px.getNickname(), this.gameId);
            }
            else {
                gameListenersHandler.notify_gameFull(px);
                throw new GameAlreadyFullException("The game is full");
            }
        }
        else {
            gameListenersHandler.notify_playerAlreadyIn(px);
            throw new PlayerAlreadyInException("The player is already in the game");
        }

    }

    public void noAvailableGame(Player px) {
        gameListenersHandler.notify_noAvailableGame(px.getNickname());
    }

    public synchronized void removePlayer(Player p){
        players.remove(p);
        if(status[0].equals(GameStatus.RUNNING) || status[0].equals(GameStatus.LAST_TURN)){
            status[0] = GameStatus.ENDED;
        }
        //here before calling this method the client should call removeListener to remove itself from the listeners list, or the server should
        gameListenersHandler.notify_removePlayer(p.getNickname());
    }

    public synchronized void reconnectPlayer(String nickname) {
        Player p = players.stream().filter(player -> Objects.equals(player.getNickname(), nickname)).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(true);
            playersDisconnected.remove(p);
            ArrayList<Player> copiedList = new ArrayList<>(players);
            int first = copiedList.getFirst().getColorPlayer();
            int[] indx = new int[maxNumberOfPlayer];
            for(int i = 0; i < maxNumberOfPlayer; i++) {
                indx[(first + i) % maxNumberOfPlayer - 1] = i;
            }
            copiedList.add(indx[p.getColorPlayer() - 1], p);
            players.clear();
            players.addAll(copiedList);

            gameListenersHandler.notify_reconnectPlayer(nickname);
        }
        else {
            gameListenersHandler.notify_reconnectionImpossible(nickname);
        }
    }

    public synchronized void disconnectPlayer(String nickname) {
        Player p = players.stream().filter(player -> Objects.equals(player.getNickname(), nickname)).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(false);
            playersDisconnected.add(p);
            players.remove(p);
            gameListenersHandler.notify_disconnectedPlayer(nickname);
        }
        else {
            gameListenersHandler.notify_disconnectionImpossible(nickname);
        }
    }

    public synchronized int numberDisconnectedPlayers() {
        return playersDisconnected.size();
    }

    public synchronized void setFirstPlayer(Player fp){
        this.firstPlayer=fp;
        gameListenersHandler.notify_setFirstPlayer(fp);
    }

    public void setStatus(GameStatus status) {
        this.status[0] = status;
        gameListenersHandler.notify_setStatus(status);
    }

    public void startGame() {
        gameListenersHandler.notify_gameStarted(this.firstPlayer.getListener());
    }

    public void setLastStatus() {
        status[1] =status[0];
        gameListenersHandler.notify_setLastStatus(status[0]);
    }

    public void resetLastStatus() {
        status[1] = null;
        gameListenersHandler.notify_resetLastStatus();
    }

    public GameStatus getGameStatus(){
        return status[0];
    }

    public GameStatus getLastStatus(){
        return status[1];
    }

    public Queue<Player> getPlayers() {
        return new LinkedList<>(players);
    }

    public int getPlayerNum() {
        return players.size();
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
            gameListenersHandler.notify_lastTurn();
        }
        gameListenersHandler.notify_nextPlayer(newCurrent);
    }

    public void gameReady() {
        gameListenersHandler.notify_All(new GameImmutable(this));
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
    /*
    public void playerIsReadyToStart(Player p){
        p.setReadyToStart();
        gameListenersHandler.notify_playerIsReadyToStart(p);
    }

    public Boolean arePlayerReady(){
        return players.stream().filter(Player::getReadyToStart).count() == players.size()
                && players.size() == maxNumberOfPlayer;
    }
    */
    public void setPlayerReady(String nickname){
        boolean flag = false;
        for(String nn : ready){
            if (nickname.equals(nn)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            ready.add(nickname);
        }
    }

    public int getReadyPlayersNum() {
        return ready.size();
    }

    public void askPlayersReady() {
        gameListenersHandler.notify_askPlayersReady();
    }

//---------------------------------DECK SECTION
    public void setGameDrawableDeck(DrawableDeck deck) {
        this.gameDrawableDeck = deck;
        gameListenersHandler.notify_setGameDrawableDeck(deck);
    }

    public void setGameBoardDeck(BoardDeck deck) {
        this.gameBoardDeck = deck;
        gameListenersHandler.notify_setGameBoardDeck(deck);
    }

//---------------------------------CHAT SECTION

    public void sendMessage(String txt, String nickname){
        Message message = new Message(txt,nickname);
        chat.addMessage(message);
        gameListenersHandler.notify_messageSent(message);
    }
    public void sendPrivateMessage(String senderName, String recieverName, String txt){
        PrivateMessage message = new PrivateMessage(txt,senderName,recieverName);
        chat.addPrivateMessage(senderName,recieverName,message);
        gameListenersHandler.notify_privateMessageSent(message);
    }
    public void getPublicChatLog(String requesterName) {
        gameListenersHandler.notify_publicChatLog(requesterName,chat.getAllMessages());
    }
    public void getPrivateChatLog(String yourName, String otherName){
        gameListenersHandler.notify_privateChatLog(yourName,otherName,chat.getPrivateChat(yourName,otherName));
    }
    public Chat getChat(){
        return chat;
    }


}
