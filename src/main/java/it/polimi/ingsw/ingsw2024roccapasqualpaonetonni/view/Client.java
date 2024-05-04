package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Client extends UnicastRemoteObject implements GameListener, Runnable {
    ServerInterface serverStub;
    ConnectionType connection;
    private int myGameId = 0;
    private String myNickname = null;

    public Client(ConnectionType conSel) throws IOException {
        connection = conSel;
        switch (conSel){
            case RMI -> {
                serverStub = new RMIServerStub();
                new Thread(this).start();
            }
            case SOCKET -> {
                serverStub = new SocketClient(this);
                new Thread(this).start();
            }
        }
    }
/*
    @Override
    public void run() {

        //ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set max number of players: "));
        //int num = Integer.parseInt(new Scanner(System.in).nextLine());
        System.out.println("Set max number of players: ");
        String num = scanner.nextLine();
        //ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert nickname: "));
        //String nickname = new Scanner(System.in).nextLine();
        System.out.println("Welcome! Please, insert your username: ");
        String nickname = scanner.nextLine();
        try {
            //client.createGame(nickname,num,this);
            client.joinFirstAvailable(nickname,this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set new max number of players: "));
        int max = Integer.parseInt(new Scanner(System.in).nextLine());
        try {
            client.setNumberOfPlayers(max);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
*/
    @Override
    public void run() {
        joinLobby();
        choice();
    }

    public void choice(){
        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Select your action:\n1) Send a public message\n2) Send a private message\n3)View public chat\n4/View private chat\n "));
        int selection = Integer.parseInt(new Scanner(System.in).nextLine());
        switch (selection){
            case 1: {
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert message: "));
                String message = new Scanner(System.in).nextLine();
                try {
                    serverStub.sendMessage(message,myNickname);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                choice();
            }
            case 2:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert reciever username: "));
                String reciever = new Scanner(System.in).nextLine();
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert message: "));
                String message = new Scanner(System.in).nextLine();
                try {
                    serverStub.sendPrivateMessage(message,myNickname,reciever);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                choice();
            }
            case 3:{
                try {
                    serverStub.getPublicChatLog(myNickname);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                choice();
            }
            case 4:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert reciever username: "));
                String otherName = new Scanner(System.in).nextLine();
                try {
                    serverStub.getPrivateChatLog(myNickname,otherName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            choice();
        }
    }
    public void joinLobby(){
        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Welcome! Select your action:\n1) Create a new game\n2) Join the first available game\n3) Join a game by game ID "));
        int selection = Integer.parseInt(new Scanner(System.in).nextLine());

        switch(selection) {
            case 1: {
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set max number of players: "));
                int maxNumPlayers = Integer.parseInt(new Scanner(System.in).nextLine());

                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                myNickname = new Scanner(System.in).nextLine();

                try {
                    serverStub.createGame(myNickname, maxNumPlayers, this);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case 2:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                myNickname = new Scanner(System.in).nextLine();
                try {
                    serverStub.joinFirstAvailable(myNickname, this);
                    serverStub.sendPrivateMessage("ciao a","b","a");
                    serverStub.getPublicChatLog(myNickname);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case 3:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                myNickname = new Scanner(System.in).nextLine();
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set gameID: "));
                int gameID = Integer.parseInt(new Scanner(System.in).nextLine());
                try {
                    serverStub.joinGameByID(myNickname, gameID, this);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    @Override
    public ServerInterface getServerStub(){
        return this.serverStub;
    }

    @Override
    public ConnectionType getConnectionType() {
        return connection;
    }

    @Override
    public String getNickname() {
        return myNickname;
    }

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
        joinLobby();
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
