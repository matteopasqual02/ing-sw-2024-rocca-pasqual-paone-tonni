package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.TUI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class Client extends UnicastRemoteObject implements GameListener, Runnable {
    ServerInterface server;
    EnumConnectionType connectionType;
    EnumViewType viewType;
    ViewUpdate view;
    private int myGameId;
    private String myNickname;

    public Client(EnumConnectionType connectionType, EnumViewType viewType) throws IOException {
        this.myGameId = 0;
        this.myNickname = null;
        this.server = null;
        this.connectionType = connectionType;
        this.viewType = viewType;

        switch (connectionType){
            case RMI -> {
                server = new RMIServerStub();
                new Thread(this).start();
            }
            case SOCKET -> {
                server = new SocketClient(this);
                new Thread(this).start();
            }
        }
        switch (viewType){
            case GUI -> view = new GUI();
            case TUI -> view = new TUI();
        }
    }

    @Override
    public void run() {
        MainClient.clearCMD();
        view.joinLobby(this);
    }

    //------------------------------------- SET GET ------------------------------------------------------------------------------

    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }
    public String getMyNickname(){
        return myNickname;
    }
    public ServerInterface getServerInterface(){
        return server;
    }

    //-------------------------------------OVERRIDE SECTION -----------------------------------------------------------------------

    @Override
    public void maxNumPlayersSet(int max) {
        String message = String.format("New max number of players for game: %d players maximum", max);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void createdGame(int gameId) {
        myGameId = gameId;
        String message = String.format("Game created, with GameID: %d", myGameId);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void youJoinedGame(int gameId) {
        myGameId = gameId;
        String message = String.format("You joined game %d", myGameId);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void noAvailableGame() {
        String message = String.format("No game available, try again");
        ConsolePrinter.consolePrinter(message);
        view.joinLobby(this);
    }

    @Override
    public void addedNewPlayer(String pNickname) {
        String message = String.format("Player \"%s\" joined the game", pNickname);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void areYouReady() {
        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Press the key (Y) when you are ready to start the game!"));
        /*
        String selection = new Scanner(System.in).nextLine();

        if (selection.equals("Y")) {
            try {
                serverStub.ready(myNickname);
            }
            catch (NotBoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            String message = String.format("You pressed an invalid key, try again!");
            ConsolePrinter.consolePrinter(message);
            areYouReady();
        }
        */
    }

    @Override
    public void playerReady(Player p) {

    }

    @Override
    public void fullGame() {

    }

    @Override
    public void nameAlreadyInGame() {

    }

    @Override
    public void playerRemoved(String p) {

    }

    @Override
    public void nextTurn(Player p) {

    }

    @Override
    public void lastTurn() {

    }

    @Override
    public void reconnectedPlayer(String nickname) {

    }

    @Override
    public void reconnectionImpossible(String nickname) {

    }

    @Override
    public void disconnectedPlayer(String nickname) {

    }

    @Override
    public void disconnectionImpossible(String nickname) {

    }

    @Override
    public void statusSet(GameStatus status) {

    }

    @Override
    public void statusSetToLastStatus(GameStatus status) {

    }

    @Override
    public void lastStatusReset() {

    }

    @Override
    public void playerIsReady(Player p) {

    }

    @Override
    public void firstPlayerSet(Player first) {

    }

    @Override
    public void drawableDeckSet(DrawableDeck d) {

    }

    @Override
    public void boardDeckSet(BoardDeck bd) {

    }

    @Override
    public void newMessage(Message m) throws RemoteException {
        String message = String.format("[%s] %s",m.getSender(),m.getText());
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void chatUpdate(List<Message> allMessages) throws RemoteException {

    }
    @Override
    public void newPrivateMessage(PrivateMessage m) throws RemoteException {
        String message = String.format("[%s] privately sent you: %s",m.getSender(),m.getText());
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void publicChatLog(List<Message> allMessages) throws RemoteException {
        if(allMessages!=null){
            StringBuilder chat = new StringBuilder();
            for(Message m: allMessages){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            ConsolePrinter.consolePrinter(chat);
        }
        else {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("There is no public chat"));
        }
    }

    @Override
    public void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException {
        if(privateChat!=null){
            StringBuilder chat = new StringBuilder();
            for(PrivateMessage m: privateChat){
                chat.append(String.format("[%s] %s\n",m.getSender(),m.getText()));
            }
            ConsolePrinter.consolePrinter(chat);
        }
        else {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("You have no private chat with this player"));
        }
    }


    @Override
    public void startAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void cardAdded(PlayerBoard board, Player p) {

    }

    @Override
    public void choseInvalidPlace(Player p) {

    }

    @Override
    public void conditionsNotMet(Player p) {

    }

    @Override
    public void startingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void drewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void personalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void cardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void resourceDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void goldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void drewFromBoard(PlayingCard card, Player p) {

    }

    @Override
    public void playerIsConnected(Player p) {

    }

    @Override
    public void pointsIncreased(int points, Player p) {

    }

    @Override
    public void seedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void cardRemovedFromHand(PlayingCard card, Player p) {

    }
/*
    @Override
    public void choseInvalidPlace(Player p) {

    }

    @Override
    public void conditionsNotMet(Player p) {

    }

    @Override
    public void startingCardDrew(StartingCard start, Player p) {

    }

    @Override
    public void drewPersonalGoals(ObjectiveCard[] goals, Player p) {

    }

    @Override
    public void personalGoalChosen(ObjectiveCard goal, Player p) {

    }

    @Override
    public void cardNotInHand(PlayingCard card, Player p) {

    }

    @Override
    public void resourceDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void goldDrawn(PlayingCard card, Player p) {

    }

    @Override
    public void drewFromBoard(PlayingCard card, Player p) {

    }

    @Override
    public void playerIsConnected(Player p) {

    }

    @Override
    public void pointsIncreased(int points, Player p) {

    }

    @Override
    public void seedCountUpdated(int[] seedCount, Player p) {

    }

    @Override
    public void cardRemovedFromHand(PlayingCard card, Player p) {

    }


     */

}
