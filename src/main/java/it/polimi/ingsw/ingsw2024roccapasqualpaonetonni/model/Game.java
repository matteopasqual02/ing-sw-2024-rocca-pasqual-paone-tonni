package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

/**
 * The type Game.
 */
public class Game implements Serializable {
    /**
     * The Game id.
     */
    private final int gameId;
    /**
     * The Status.
     */
    private final GameStatus[] status; //current and previous status needed for reconnection
    /**
     * The Max number of player.
     */
    private int maxNumberOfPlayer;
    /**
     * The Players.
     */
    private final Queue<Player> players;
    /**
     * The Players disconnected.
     */
    private final List<Player> playersDisconnected;
    /**
     * The Winner.
     */
    private final Queue<Player> winner;
    /**
     * The First player.
     */
    private Player firstPlayer;
    /**
     * The Game board deck.
     */
    private BoardDeck gameBoardDeck;
    /**
     * The Game drawable deck.
     */
    private DrawableDeck gameDrawableDeck;
    /**
     * The Chat.
     */
    private final Chat chat;
    /**
     * The Ready.
     */
    private final List<String> ready;

    /**
     * The Game listeners handler.
     */
    private final GameListenersHandler gameListenersHandler;

    /**
     * Instantiates a new Game.
     *
     * @param id the id
     */
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

    /**
     * Add listeners.
     *
     * @param me       my nickname
     * @param notifier the notifier
     */
    public void addListeners(String me, NotifierInterface notifier){
        synchronized(gameListenersHandler){
            try {
                gameListenersHandler.addListener(me, notifier);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Remove listener.
     *
     * @param name the name
     */
    public void removeListener(String name) {
        synchronized (gameListenersHandler) {
            gameListenersHandler.removeListener(name);
            for (Player p : players) {
                p.setPlayerListeners(gameListenersHandler.getListener());
            }
        }
    }

    /**
     * Get game id int.
     *
     * @return the int
     */
//---------------------------------PLAYER SECTION
    public int getGameId(){
        return gameId;
    }

    /**
     * Set max number of player.
     *
     * @param number the number
     */
    public void setMaxNumberOfPlayer(int number){
        this.maxNumberOfPlayer = number;
        gameListenersHandler.notify_setMaxNumPlayers(gameId,maxNumberOfPlayer);
    }

    /**
     * Get max number of player int.
     *
     * @return the int
     */
    public int getMaxNumberOfPlayer(){
        return maxNumberOfPlayer;
    }

    /**
     * Add player.
     *
     * @param px the px
     * @throws GameAlreadyFullException the game already full exception
     * @throws PlayerAlreadyInException the player already in exception
     */
    public synchronized void addPlayer(Player px) throws GameAlreadyFullException, PlayerAlreadyInException {
        if (!players.contains(px)) {
            if (players.size() < maxNumberOfPlayer) {
                players.add(px);

                for(Player p: players){
                    p.setPlayerListeners(gameListenersHandler.getListener());
                }

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

    /**
     * Gets players disconnected.
     *
     * @return the players disconnected
     */
    public List<Player> getPlayersDisconnected() {
        return playersDisconnected;
    }

    /**
     * Reconnect player.
     *
     * @param nickname the nickname
     */
    public synchronized void reconnectPlayer(String nickname) {
        Player p = playersDisconnected.stream().filter(player -> nickname.equals(player.getNickname())).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(true);

            for (int i=0; i<playersDisconnected.size();i++){
                if(nickname.equals(playersDisconnected.get(i).getNickname())){
                    playersDisconnected.remove(playersDisconnected.get(i));
                    i=maxNumberOfPlayer;
                }
            }
            ArrayList<Player> copiedList = new ArrayList<>(players);

            boolean in = false;
            for(int i=1; i<copiedList.size() && !in; i++){
                if(p.getColorPlayer()<copiedList.get(i).getColorPlayer()){
                    copiedList.add(i, p);
                    in = true;
                }
            }
            if(!in){
                copiedList.add(p);
            }

            players.clear();
            players.addAll(copiedList);

            gameListenersHandler.notify_reconnectPlayer(nickname);
        }
        else {
            gameListenersHandler.notify_reconnectionImpossible(nickname);
        }
    }

    /**
     * Disconnect player.
     *
     * @param nickname the nickname
     */
    public synchronized void disconnectPlayer(String nickname) {
        Player p = players.stream().filter(player -> Objects.equals(player.getNickname(), nickname)).findFirst().orElse(null);
        if(p!=null){
            p.setIsConnected(false);
            playersDisconnected.add(p);
            players.remove(p);
            gameListenersHandler.removeListener(nickname);
            gameListenersHandler.notify_disconnectedPlayer(nickname);
        }
        else {
            gameListenersHandler.notify_disconnectionImpossible(nickname);
        }
    }

    /**
     * Number disconnected players int.
     *
     * @return the int
     */
    public synchronized int numberDisconnectedPlayers() {
        return playersDisconnected.size();
    }

    /**
     * Set first player.
     *
     * @param fp the fp
     */
    public synchronized void setFirstPlayer(Player fp){
        this.firstPlayer=fp;
        //gameListenersHandler.notify_setFirstPlayer(fp);
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(GameStatus status) {
        this.status[0] = status;
        gameListenersHandler.notify_setStatus(status);
    }

    /**
     * Sets last status.
     */
    public void setLastStatus() {
        status[1] =status[0];
        gameListenersHandler.notify_setLastStatus(status[0]);
    }

    /**
     * Reset last status.
     */
    public void resetLastStatus() {
        status[1] = null;
        gameListenersHandler.notify_resetLastStatus();
        gameListenersHandler.notify_nextTurn(players.peek().getNickname());
    }

    /**
     * Get game status .
     *
     * @return the game status
     */
    public GameStatus getGameStatus(){
        return status[0];
    }

    /**
     * Get last status game status.
     *
     * @return the game status
     */
    public GameStatus getLastStatus(){
        return status[1];
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public Queue<Player> getPlayers() {
        return new LinkedList<>(players);
    }

    /**
     * Gets player num.
     *
     * @return the player num
     */
    public int getPlayerNum() {
        return players.size();
    }

    /**
     * Get current player.
     *
     * @return the player
     */
    public Player getCurrentPlayer(){
        return players.peek();
    }

    /**
     * Get game drawable deck drawable deck.
     *
     * @return the drawable deck
     */
    public DrawableDeck getGameDrawableDeck(){
        return gameDrawableDeck;
    }

    /**
     * Get game board deck.
     *
     * @return the board deck
     */
    public BoardDeck getGameBoardDeck(){
        return gameBoardDeck;
    }

    /**
     * Next player.
     */
    public void nextPlayer(){
        Player temp;
        temp = players.poll();
        players.add(temp);
        Player newCurrent = players.peek();
        if(newCurrent != null && firstPlayer!=null && firstPlayer.getNickname().equals(newCurrent.getNickname()) && status[0].equals(GameStatus.LAST_TURN)){
            checkWinner();
            setStatus(GameStatus.ENDED);
            gameListenersHandler.notify_winners(getWinners().stream().toList());
            return;
        }
        if (newCurrent != null && firstPlayer!=null && firstPlayer.getNickname().equals(newCurrent.getNickname()) && status[0].equals(GameStatus.WAITING_LAST_TURN)) {
            setStatus(GameStatus.LAST_TURN);
            gameListenersHandler.notify_lastTurn();
            gameListenersHandler.notify_nextTurn(newCurrent.getNickname());
            return;
        }
        if(newCurrent!=null) {
            gameListenersHandler.notify_nextTurn(newCurrent.getNickname());
            return;
        }
        gameListenersHandler.notify_gameGenericError("Error in Next Turn");
    }

    /**
     * Game ready.
     */
    public void gameReady()  {
        gameListenersHandler.notify_All(this);
    }

    /**
     * Game error.
     *
     * @param s the s
     */
    public void gameError(String s){gameListenersHandler.notify_gameGenericError(s);}

    /**
     * Check player total point int.
     *
     * @param p the p
     * @return the int
     */
//---------------------------------POINT SECTION
    public int checkPlayerTotalPoint(Player p){
        return p.getCurrentPoints()
                + p.getGoal().pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective(0).pointCard(p.getBoard())
                + gameBoardDeck.getCommonObjective(1).pointCard(p.getBoard())
                ;
    }

    /**
     * Check winner.
     */
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

    /**
     * Get winners queue.
     *
     * @return the queue
     */
    public Queue<Player> getWinners(){
        return winner;
    }

    /**
     * Set player ready.
     *
     * @param nickname the nickname
     */
//---------------------------------READY SECTION
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

    /**
     * Gets ready players num.
     *
     * @return the ready players num
     */
    public int getReadyPlayersNum() {
        return ready.size();
    }

    /**
     * Ask players ready.
     */
    public void askPlayersReady() {
        gameListenersHandler.notify_askPlayersReady();
    }

    /**
     * Sets game drawable deck.
     *
     * @param deck the deck
     */
//---------------------------------DECK SECTION
    public void setGameDrawableDeck(DrawableDeck deck) {
        this.gameDrawableDeck = deck;
        //gameListenersHandler.notify_setGameDrawableDeck(deck);
    }

    /**
     * Sets game board deck.
     *
     * @param deck the deck
     */
    public void setGameBoardDeck(BoardDeck deck) {
        this.gameBoardDeck = deck;
        //gameListenersHandler.notify_setGameBoardDeck(deck);
    }

//---------------------------------CHAT SECTION

    /**
     * Send message.
     *
     * @param txt      the txt
     * @param nickname the nickname
     */
    public void sendMessage(String txt, String nickname){
        Message message = new Message(txt,nickname);
        chat.addMessage(message);
        gameListenersHandler.notify_messageSent(message);
    }

    /**
     * Send private message.
     *
     * @param senderName   the sender name
     * @param recieverName the reciever name
     * @param txt          the txt
     */
    public void sendPrivateMessage(String senderName, String recieverName, String txt){
        PrivateMessage message = new PrivateMessage(txt,senderName,recieverName);
        chat.addPrivateMessage(senderName,recieverName,message);
        gameListenersHandler.notify_privateMessageSent(message);
    }

    /**
     * Gets public chat log.
     *
     * @param requesterName the requester name
     */
    public void getPublicChatLog(String requesterName) {
        gameListenersHandler.notify_publicChatLog(requesterName,chat.getAllMessages());
    }

    /**
     * Get private chat log.
     *
     * @param yourName  your name
     * @param otherName the other name
     */
    public void getPrivateChatLog(String yourName, String otherName){
        gameListenersHandler.notify_privateChatLog(yourName,otherName,chat.getPrivateChat(yourName,otherName));
    }

    /**
     * Get chat chat.
     *
     * @return the chat
     */
    public Chat getChat(){
        return chat;
    }

    /**
     * Ping.
     *
     * @param client the client
     * @throws Exception the exception
     */
    public void ping(String client) throws Exception{
        gameListenersHandler.notify_ping(client);
    }
}
