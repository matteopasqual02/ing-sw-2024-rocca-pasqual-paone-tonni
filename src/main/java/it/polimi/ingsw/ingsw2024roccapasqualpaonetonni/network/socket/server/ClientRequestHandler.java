package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.MainController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.NotifierInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages.ClientGenericMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerMessageMaxNum;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerMessageNewMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerMessageNewPrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages.ServerMessagePublicChatLog;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;

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
                    consolePrinter("[SOCKET] no more with the client");
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

                if(message.isForMainController()){
                    gameControllerInterface = message.launchMessage(MainController.getInstance(),this);
                }
                else{
                    message.launchMessage(gameControllerInterface);
                }
            }
        }
        catch (InterruptedException ignored){

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void messageDone() throws IOException {
        outputStream.flush();
        outputStream.reset();
    }
    @Override
    public void sendMaxNumPlayersSet(int gameId, int max) throws IOException, ClassNotFoundException {
        outputStream.writeObject(new ServerMessageMaxNum(max));
        messageDone();
    }

    @Override
    public void sendCreatedGame(int gameId) {

    }

    @Override
    public void sendYouJoinedGame(int gameId, String pNickname) {

    }

    @Override
    public void sendAddedNewPlayer(String pNickname) {

    }

    @Override
    public void sendNoAvailableGame() {

    }

    @Override
    public void sendAskPlayersReady() {

    }

    @Override
    public void sendFullGame() {

    }

    @Override
    public void sendNameAlreadyInGame() {

    }

    @Override
    public void sendPlayerRemoved(String pNickname) {

    }

    @Override
    public void sendNextTurn(Player p) {

    }

    @Override
    public void sendLastTurn() {

    }

    @Override
    public void sendReconnectedPlayer(String nickname) {

    }

    @Override
    public void sendReconnectionImpossible(String nickname) {

    }

    @Override
    public void sendDisconnectedPlayer(String nickname) {

    }

    @Override
    public void sendDisconnectionImpossible(String nickname) {

    }

    @Override
    public void sendStatusSet(GameStatus status) {

    }

    @Override
    public void sendStatusSetToLastStatus(GameStatus status) {

    }

    @Override
    public void sendLastStatusReset() {

    }

    @Override
    public void sendPlayerIsReady(Player p) {

    }

    @Override
    public void sendFirstPlayerSet(Player first) {

    }

    @Override
    public void sendDrawableDeckSet(DrawableDeck d) {

    }

    @Override
    public void sendBoardDeckSet(BoardDeck bd) {

    }

    @Override
    public void sendStartAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void sendCardAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void sendChoseInvalidPlace(Player p) {

    }

    @Override
    public void sendConditionsNotMet(Player p) {

    }

    @Override
    public void sendStartingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void sendDrewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void sendPersonalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void sendCardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void sendResourceDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void sendGoldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void sendDrewFromBoard(PlayingCard card, Player p) {

    }

    @Override
    public void sendPlayerIsConnected(Player p) {

    }

    @Override
    public void sendPointsIncreased(int points, Player p) {

    }

    @Override
    public void sendSeedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void sendCardRemovedFromHand(PlayingCard card, Player p) {

    }

    @Override
    public void sendPlayerReady(Player p) {

    }

    @Override
    public void sendYouWereRemoved(String pNickname) {

    }

    @Override
    public void youWereReconnected() {

    }

    @Override
    public void sendYouAreFirst() {

    }

    @Override
    public void sendItsYourTurn() {

    }

    @Override
    public void sendUpdatedChat(List<Message> allMessages) throws RemoteException {

    }

    @Override
    public void sendMessage(Message message) throws IOException {
        outputStream.writeObject(new ServerMessageNewMessage(message));
        messageDone();
    }

    @Override
    public void sendPrivateMessage(PrivateMessage message) throws IOException {
        outputStream.writeObject(new ServerMessageNewPrivateMessage(message));
        messageDone();
    }

    @Override
    public void sendPublicChatLog(String requesterName, List<Message> allMessages) throws IOException {
        outputStream.writeObject(new ServerMessagePublicChatLog(requesterName,allMessages));
        messageDone();
    }
}
