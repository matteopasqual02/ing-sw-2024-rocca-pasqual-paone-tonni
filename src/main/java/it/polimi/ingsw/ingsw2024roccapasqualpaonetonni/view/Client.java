package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Client implements GameListener, Runnable {
    ServerInterface serverStub;
    ConnectionType connection;
    private int myGameId = 0;
    private String myNickname = null;

    //non dovrea fare la throw remoteexception, l'ho messa li solo per far andare questa prima prova
    public Client(ConnectionType conSel) {
        connection = conSel;
        switch (conSel){
            /*devo in base al caso far si che le azioni fattibili siano quelle di client o di socket
            cosi ho un solo oggetto di azioni che possono essere azioni socket o azioni rmi a seconda della scelta di connessione*/
            case RMI -> {
                serverStub = new RMIServerStub();

                //versione rudimentale senza nulla (tui,gui,thread ecc..) solo per vedere se funziona la base

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
    }

    public void joinLobby(){
        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Welcome! Select your action:\n1) Create a new game\n2) Join the first available game\n3) Join a game by game ID "));
        int selection = Integer.parseInt(new Scanner(System.in).nextLine());

        switch(selection) {
            case 1: {
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set max number of players: "));
                int maxNumPlayers = Integer.parseInt(new Scanner(System.in).nextLine());

                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();

                try {
                    serverStub.createGame(nickname, maxNumPlayers, this);
                    serverStub.setMaxNUm(maxNumPlayers);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case 2:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();
                try {
                    serverStub.joinFirstAvailable(nickname, this);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case 3:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set gameID: "));
                int gameID = Integer.parseInt(new Scanner(System.in).nextLine());
                try {
                    serverStub.joinGameByID(nickname, gameID, this);
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
    public void youJoinedGame(int gameId, String pNickname) {
        myGameId = gameId;
        myNickname = pNickname;
        String message = String.format("You joined game %d, with nickname %d", myGameId, myNickname);
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
    public void playerRemoved(Player p) {

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


}
