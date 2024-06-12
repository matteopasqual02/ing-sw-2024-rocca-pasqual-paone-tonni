package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.ClientGenericMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.MainMessageCreateGame;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.MainMessageJoinFirstAvailable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The type Client request handler.
 */
public class ClientRequestHandler extends Thread implements NotifierInterface {
    /**
     * The Input stream.
     */
    private final ObjectInputStream inputStream;
    /**
     * The Output stream.
     */
    private final ObjectOutputStream outputStream;

    /**
     * The Game controller interface.
     */
    private GameControllerInterface gameControllerInterface;
    /**
     * The Processing queue.
     */
    private final BlockingQueue<ClientGenericMessage> processingQueue;


    /**
     * Instantiates a new Client request handler.
     *
     * @param socket the socket
     * @throws IOException the io exception
     */
    public ClientRequestHandler(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        processingQueue = new LinkedBlockingQueue<>();
        //link the server's output to client
    }

    /**
     * Interrupt thread.
     */
    public void interruptThread() {
        this.interrupt();
    }

    /**
     * Run.
     */
    @Override
    public void run(){
        var messageThread = new Thread(this::takeExecute);
        messageThread.start();

        try{
            ClientGenericMessage message;
            while(!this.isInterrupted()){
                try{
                    message = (ClientGenericMessage) inputStream.readObject();
                    processingQueue.add(message);

                }catch (IOException | ClassNotFoundException e) {
                    ConsolePrinter.consolePrinter("[SOCKET] no more with the client");

                }

            }

        }finally {
            messageThread.interrupt();
        }

    }

    /**
     * Take execute.
     */
    private void takeExecute(){
        ClientGenericMessage message;

        try{
            while(!this.isInterrupted()){
                message = processingQueue.take();
                if (message.isForMainController()){
                    gameControllerInterface = message.launchMessage(MainController.getInstance(), this);
                    if (message instanceof MainMessageCreateGame) {
                        try {
                            sendYouJoinedGame(gameControllerInterface.getGameId());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (message instanceof MainMessageJoinFirstAvailable && gameControllerInterface!=null) {
                        try {
                            sendYouJoinedGame(gameControllerInterface.getGameId());
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(gameControllerInterface == null){
                        sendNoAvailableGame();
                    }
                }
                else{
                    message.launchMessage(gameControllerInterface);
                }
            }
        }
        catch (InterruptedException ignored){

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Message done.
     *
     * @throws IOException the io exception
     */
    private void messageDone() throws IOException {
        synchronized (outputStream) {
            outputStream.flush();
            outputStream.reset();
        }
    }

    /**
     * Send all.
     *
     * @param gameImmutable the game immutable
     * @throws IOException     the io exception
     * @throws RemoteException the remote exception
     */
    @Override
    public void sendAll(GameImmutable gameImmutable) throws IOException, RemoteException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNotifyAll(gameImmutable));
            messageDone();
        }
    }

    /**
     * Send ping.
     *
     * @throws IOException the io exception
     */
    @Override
    public void sendPing() throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessagePing());
            messageDone();
        }
    }

    /**
     * Generic error.
     *
     * @param s the s
     * @throws IOException the io exception
     */
    @Override
    public void genericError(String s) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageError(s));
            messageDone();
        }
    }

    /**
     * Winners.
     *
     * @param list the list
     * @throws IOException the io exception
     */
    @Override
    public void winners(List<Player> list) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageWinners(list));
            messageDone();
        }
    }

    /**
     * Send max num players set.
     *
     * @param gameId the game id
     * @param max    the max
     * @throws IOException            the io exception
     */
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageMaxNum(max));
            messageDone();
        }
    }

    /**
     * Send created game.
     *
     * @param gameId the game id
     * @throws IOException the io exception
     */
    @Override
    public void sendCreatedGame(int gameId) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageCreatedGame(gameId));
            messageDone();
        }
    }

    /**
     * Send you joined game.
     *
     * @param gameId the game id
     * @throws IOException the io exception
     */
    @Override
    public void sendYouJoinedGame(int gameId) throws IOException {
        if (gameControllerInterface != null) {
            synchronized (outputStream) {
                outputStream.writeObject(new MainMessageJoinedGame(gameControllerInterface.getGameId()));
                messageDone();
            }
        }
    }

    /**
     * Send added new player.
     *
     * @param pNickname the p nickname
     * @throws IOException the io exception
     */
    @Override
    public void sendAddedNewPlayer(String pNickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageNewPlayerJoined(pNickname));
            messageDone();
        }
    }

    /**
     * Send no available game.
     *
     * @throws IOException the io exception
     */
    @Override
    public void sendNoAvailableGame() throws IOException{
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageNoAvailableGame());
            messageDone();
        }
    }

    /**
     * Send ask players ready.
     *
     * @throws IOException the io exception
     */
    @Override
    public void sendAskPlayersReady() throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageAskPlayerReady());
            messageDone();
        }
    }

    /**
     * Send full game.
     */
    @Override
    public void sendFullGame() {
        synchronized (outputStream) {
        }
    }

    /**
     * Send name already in game.
     */
    @Override
    public void sendNameAlreadyInGame() {
        synchronized (outputStream) {}
    }

    /**
     * Send player removed.
     *
     * @param pNickname the p nickname
     */
    @Override
    public void sendPlayerRemoved(String pNickname) {
        synchronized (outputStream) {}
    }

    /**
     * Send next turn.
     *
     * @param nickname the nickname
     * @throws IOException the io exception
     */
    @Override
    public void sendNextTurn(String nickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNextTurn(nickname));
            messageDone();
        }
    }

    /**
     * Send last turn.
     */
    @Override
    public void sendLastTurn() {
        synchronized (outputStream) {}
    }

    /**
     * Send reconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendReconnectedPlayer(String nickname) {
        synchronized (outputStream) {}
    }

    /**
     * Send reconnection impossible.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendReconnectionImpossible(String nickname) {
        synchronized (outputStream) {}
    }

    /**
     * Send disconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void sendDisconnectedPlayer(String nickname) {
        synchronized (outputStream) {}
    }


    /**
     * Send status set.
     *
     * @param status the status
     * @throws IOException the io exception
     */
    @Override
    public void sendStatusSet(GameStatus status) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageStatusSet(status));
            messageDone();
        }
    }

    /**
     * Send status set to last status.
     *
     * @param status the status
     */
    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        synchronized (outputStream) {}
    }

    /**
     * Send last status reset.
     */
    @Override
    public void sendLastStatusReset() {
        synchronized (outputStream) {}
    }

    /**
     * Send start added.
     *
     * @param p the p
     * @throws IOException the io exception
     */
    @Override
    public void sendStartAdded(Player p) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageStartAdded(p));
            messageDone();
        }
    }

    /**
     * Send card added.
     *
     * @param p the p
     * @throws IOException the io exception
     */
    @Override
    public void sendCardAdded(Player p, Double coord0, Double coord1,int cardID) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageCardAdded(p,coord0,coord1,cardID));
            messageDone();
        }
    }

    /**
     * Send personal goal chosen.
     *
     * @param p the p
     * @throws IOException the io exception
     */
    @Override
    public void sendPersonalGoalChosen(Player p) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageGoalChosen(p));
            messageDone();
        }
    }

    /**
     * Send resource drawn.
     *
     * @param p the p
     * @param d the d
     * @throws IOException the io exception
     */
    @Override
    public void sendResourceDrawn(Player p, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewResources(p, d));
            messageDone();
        }
    }

    /**
     * Send gold drawn.
     *
     * @param p the p
     * @param d the d
     * @throws IOException the io exception
     */
    @Override
    public void sendGoldDrawn(Player p, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewGold(p, d));
            messageDone();
        }
    }

    /**
     * Send drew from board.
     *
     * @param p the p
     * @param b the b
     * @param d the d
     * @throws IOException the io exception
     */
    @Override
    public void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewBoard(p, b, d));
            messageDone();
        }
    }

    /**
     * Send you were removed.
     *
     * @param pNickname the p nickname
     * @throws IOException the io exception
     */
    @Override
    public void sendYouWereRemoved(String pNickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessagePlayerRemoved(pNickname));
            messageDone();
        }
    }

    /**
     * You were reconnected.
     */
    @Override
    public void youWereReconnected() {
        synchronized (outputStream) {}
    }

    /**
     * Send message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    @Override
    public void sendMessage(Message message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNewMessage(message));
            messageDone();
        }
    }

    /**
     * Send private message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatNewPrivateMessage(message));
            messageDone();
        }
    }

    /**
     * Send public chat log.
     *
     * @param requesterName the requester name
     * @param allMessages   the all messages
     * @throws IOException the io exception
     */
    @Override
    public void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatPublicLog(requesterName, allMessages));
            messageDone();
        }
    }

    /**
     * Send private chat log.
     *
     * @param yourName    your name
     * @param otherName   the other name
     * @param privateChat the private chat
     * @throws IOException the io exception
     */
    @Override
    public void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatPrivateLog(yourName, otherName, privateChat));
            messageDone();
        }
    }
}
