package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.GameListener;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Client implements GameListener, Runnable {
    ServerInterface client;
    Scanner scanner = new Scanner(System.in);
    //non dovrea fare la throw remoteexception, l'ho messa li solo per far andare questa prima prova
    public Client(ConnectionType conSel) {
        switch (conSel){
            /*devo in base al caso far si che le azioni fattibili siano quelle di client o di socket
            cosi ho un solo oggetto di azioni che possono essere azioni socket o azioni rmi a seconda della scelta di connessione*/
            case RMI -> {
                client = new RMIServerStub();

                //versione rudimentale senza nulla (tui,gui,thread ecc..) solo per vedere se funziona la base

                new Thread(this).start();
            }
            //case SOCKET ->
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
    public void run(){
        joinLobby();
        try {
            System.out.println("Game joined, your gameID is: "+client.getID());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void joinLobby(){

        ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Welcome! Select your action:\n1) Create a new game\n2) Join the first available game\n3) Join a game by game ID "));
        int selection = Integer.parseInt(new Scanner(System.in).nextLine());
        //System.out.println("Welcome! Select your action:\n1) Create a new game\n2) Join the first available game\n3) Join a game by game ID ");
        //int selection = scanner.nextInt();
        switch (selection ){
            case 1: {
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set max number of players: "));
                int num = Integer.parseInt(new Scanner(System.in).nextLine());
                //System.out.println("Set max number of players: ");
                //int num = scanner.nextInt();
                //scanner.nextLine();
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();
                //System.out.println("Insert your username: ");
                //String nickname = scanner.nextLine();
                try {
                    client.createGame(nickname, num, this);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            case 2:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();
                //System.out.println("Insert your username: ");
                //String nickname = scanner.nextLine();
                try {
                    Boolean possible = client.joinFirstAvailable(nickname, this);
                    if(possible==false){
                        joinLobby();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            case 3:{
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set nickname: "));
                String nickname = new Scanner(System.in).nextLine();
                //System.out.println("Insert your username: ");
                //String nickname = scanner.nextLine();
                ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Set gameID: "));
                int gameID = Integer.parseInt(new Scanner(System.in).nextLine());
                //System.out.println("Insert gameID: ");
                //int gameID = scanner.nextInt();
                //scanner.nextLine();
                try {
                    Boolean possible = client.joinGameByID(nickname,gameID, this);
                    if(possible==false){
                        joinLobby();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (NotBoundException e) {
                    throw new RuntimeException(e);
                }
                return;
        }

        }
    }


    //in questi metodi che arrivano del listener a seconda di se sono in rmi o socket (controllo da fare li dentro) dovro
    //cambiare il modo in cui faccio vedere gli update alla tui
    @Override
    public void maxNumPlayersSet(int gameId, int max) {
        //ConsolePrinter.consolePrinter("New max number from game");
        // Definizione della stringa con i parametri
        String message = String.format("New max number from game: %d,%d", gameId,max);
        // Stampare il messaggio utilizzando il metodo consolePrinter
        ConsolePrinter.consolePrinter(message);

    }

    @Override
    public void addedPlayer(Player newPlayer) {

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
