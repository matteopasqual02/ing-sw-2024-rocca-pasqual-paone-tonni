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

public class ClientRequestHandler extends Thread implements NotifierInterface {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private GameControllerInterface gameControllerInterface;
    private final BlockingQueue<ClientGenericMessage> processingQueue;


    public ClientRequestHandler(Socket socket) throws IOException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        processingQueue = new LinkedBlockingQueue<>();
        //link the server's output to client
    }

    public void interruptThread() {
        this.interrupt();
    }

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
                    // consolePrinter("[SOCKET] no more with the client");
                }

            }

        }finally {
            messageThread.interrupt();
        }

    }

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

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void messageDone() throws IOException {
        synchronized (outputStream) {
            outputStream.flush();
            outputStream.reset();
        }
    }

    @Override
    public void sendAll(GameImmutable gameImmutable) throws IOException, RemoteException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNotifyAll(gameImmutable));
            messageDone();
        }
    }

    @Override
    public void sendPing() throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessagePing());
            messageDone();
        }
    }

    @Override
    public void genericError(String s) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageError(s));
            messageDone();
        }
    }

    @Override
    public void winners(List<Player> list) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageWinners(list));
            messageDone();
        }
    }

    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) throws IOException, ClassNotFoundException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageMaxNum(max));
            messageDone();
        }
    }

    @Override
    public void sendCreatedGame(int gameId) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageCreatedGame(gameId));
            messageDone();
        }
    }

    @Override
    public void sendYouJoinedGame(int gameId) throws IOException {
        if (gameControllerInterface != null) {
            synchronized (outputStream) {
                outputStream.writeObject(new MainMessageJoinedGame(gameControllerInterface.getGameId()));
                messageDone();
            }
        }
    }

    @Override
    public void sendAddedNewPlayer(String pNickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageNewPlayerJoined(pNickname));
            messageDone();
        }
    }

    @Override
    public void sendNoAvailableGame() throws IOException{
        synchronized (outputStream) {
            outputStream.writeObject(new MainMessageNoAvailableGame());
            messageDone();
        }
    }

    @Override
    public void sendAskPlayersReady() throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageAskPlayerReady());
            messageDone();
        }
    }

    @Override
    public void sendFullGame() {
        synchronized (outputStream) {
        }
    }

    @Override
    public void sendNameAlreadyInGame() {
        synchronized (outputStream) {}
    }

    @Override
    public void sendPlayerRemoved(String pNickname) {
        synchronized (outputStream) {}
    }

    @Override
    public void sendNextTurn(String nickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNextTurn(nickname));
            messageDone();
        }
    }

    @Override
    public void sendLastTurn() {
        synchronized (outputStream) {}
    }

    @Override
    public void sendReconnectedPlayer(String nickname) {
        synchronized (outputStream) {}
    }

    @Override
    public void sendReconnectionImpossible(String nickname) {
        synchronized (outputStream) {}
    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {
        synchronized (outputStream) {}
    }



    @Override
    public void sendStatusSet(GameStatus status) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageStatusSet(status));
            messageDone();
        }
    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {
        synchronized (outputStream) {}
    }

    @Override
    public void sendLastStatusReset() {
        synchronized (outputStream) {}
    }

    @Override
    public void sendStartAdded(Player p) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageStartAdded(p));
            messageDone();
        }
    }

    @Override
    public void sendCardAdded(Player p) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageCardAdded(p));
            messageDone();
        }
    }

    @Override
    public void sendPersonalGoalChosen(Player p) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageGoalChosen(p));
            messageDone();
        }
    }

    @Override
    public void sendResourceDrawn(Player p, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewResources(p, d));
            messageDone();
        }
    }

    @Override
    public void sendGoldDrawn(Player p, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewGold(p, d));
            messageDone();
        }
    }

    @Override
    public void sendDrewFromBoard(Player p, BoardDeck b, DrawableDeck d) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageDrewBoard(p, b, d));
            messageDone();
        }
    }

    @Override
    public void sendYouWereRemoved(String pNickname) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessagePlayerRemoved(pNickname));
            messageDone();
        }
    }

    @Override
    public void youWereReconnected() {
        synchronized (outputStream) {}
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageNewMessage(message));
            messageDone();
        }
    }

    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatNewPrivateMessage(message));
            messageDone();
        }
    }

    @Override
    public void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatPublicLog(requesterName, allMessages));
            messageDone();
        }
    }

    @Override
    public void sendPrivateChatLog(String yourName, String otherName, List<PrivateMessage> privateChat) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(new ServerMessageChatPrivateLog(yourName, otherName, privateChat));
            messageDone();
        }
    }
}
