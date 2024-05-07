package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class TUI extends UnicastRemoteObject implements ViewUpdate  {
    public TUI() throws RemoteException {
    }

    @Override
    public void joinLobby(Client client){
        String myNickname;
        ServerInterface server = client.getServerInterface();
        title();
        ConsolePrinter.consolePrinter(
          "Welcome! Select your action:\n (n)-> Create a new game \n (j)-> Join the first available game \n (jid) Join a game by game ID \n (r) Reconnect to a Game"
        );

        String selection = new Scanner(System.in).nextLine();

        switch(selection) {
            case "n": {
                ConsolePrinter.consolePrinter("Set max number of players: ");
                int maxNumPlayers = Integer.parseInt(new Scanner(System.in).nextLine());
                ConsolePrinter.consolePrinter("Set nickname: ");
                myNickname = new Scanner(System.in).nextLine();
                client.setMyNickname(myNickname);

                try {
                    server.createGame(myNickname, maxNumPlayers, client);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "j":{
                ConsolePrinter.consolePrinter("Set nickname: ");
                myNickname = new Scanner(System.in).nextLine();
                client.setMyNickname(myNickname);

                try {
                    server.joinFirstAvailable(myNickname, client);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "jid":{
                ConsolePrinter.consolePrinter("Set nickname: ");
                myNickname = new Scanner(System.in).nextLine();
                client.setMyNickname(myNickname);
                ConsolePrinter.consolePrinter("Set the gameID: ");
                int gameID = Integer.parseInt(new Scanner(System.in).nextLine());

                try {
                    server.joinGameByID(myNickname, gameID, client);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "r":{
                ConsolePrinter.consolePrinter("Set your nickname: ");
                myNickname = new Scanner(System.in).nextLine();
                client.setMyNickname(myNickname);
                ConsolePrinter.consolePrinter("Set the previous gameID: ");
                int gameID = Integer.parseInt(new Scanner(System.in).nextLine());

                try {
                    server.reconnect(myNickname, gameID, client);
                } catch (NotBoundException | IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }

    }

    @Override
    public void show_All(GameImmutable gameImmutable, String nickname) {
        ConsolePrinter.consolePrinter(gameImmutable.toString(nickname));
    }

    @Override
    public void show_maxNumPlayersSet(int max) {
        String message = String.format("New max number of players for game: %d players maximum", max);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void show_createdGame(int gameID) {
        String message = String.format("Game created, with GameID: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void show_youJoinedGame(int gameID) {
        String message = String.format("You joined game %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void show_noAvailableGame() {
        String message = "No game available, try again \n";
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void show_addedNewPlayer(String pNickname) {
        String message = String.format("Player \"%s\" joined the game", pNickname);
        ConsolePrinter.consolePrinter(message);
    }

    @Override
    public void show_areYouReady() {
        String selection;
        ConsolePrinter.consolePrinter("Press the key (Y) when you are ready to start the game!");

        do{
            selection = new Scanner(System.in).nextLine();
        }while (!selection.equals("Y"));

    }

    @Override
    public void preparation() {
        ConsolePrinter.consolePrinter("We are waiting for all players to be ready...");
    }

    @Override
    public void myRunningTurn(Client client) {}/*
        ServerInterface server = client.getServerInterface();
        ConsolePrinter.consolePrinter(
                "It's your turn! Select your action:\n" +
                        "(p)-> put a card on the board (index in the hand, )\n" +
                        "(l) leave game \n"+
                        "(ch) send a public message \n"+
                        "(cp) send a private message \n"+
                        "(sc) see the public chat \n"+
                        "(sp) see the private chat\n"
        );
        String selection = new Scanner(System.in).nextLine();
        switch (selection){
            case "p" -> {

            }
            case "l"-> {
                //server.leave(client.getNickname(),client.);
            }
            case "ch" ->{
                ConsolePrinter.consolePrinter("Insert message: ");
                String txt = new Scanner(System.in).nextLine();
                try {
                    server.sendMessage(txt,client.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "cp" ->{
                ConsolePrinter.consolePrinter("Insert message: ");
                String txt = new Scanner(System.in).nextLine();
                ConsolePrinter.consolePrinter("Insert reciever: ");
                String reciever = new Scanner(System.in).nextLine();
                try {
                    server.sendPrivateMessage(txt,client.getNickname(),reciever);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "sc" ->{
                try {
                    server.getPublicChatLog(client.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "sp" ->{
                ConsolePrinter.consolePrinter("Insert name of the persone you want to see the chat with: ");
                String reciever = new Scanner(System.in).nextLine();
                try {
                    server.getPrivateChatLog(client.getNickname(),reciever);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        }
*/
    @Override
    public void notMyTurn(Client client) {}/*
        ServerInterface server = client.getServerInterface();
        ConsolePrinter.consolePrinter(
                "It's not your turn.\n (ch) send a public message \n (cp) send a private message \n (sc) see the public chat \n (sp) see the private chat\n"
        );
        String selection = new Scanner(System.in).nextLine();
        switch (selection){
            case "ch" ->{
                ConsolePrinter.consolePrinter("Insert message: ");
                String txt = new Scanner(System.in).nextLine();
                try {
                    server.sendMessage(txt,client.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "cp" ->{
                ConsolePrinter.consolePrinter("Insert message: ");
                String txt = new Scanner(System.in).nextLine();
                ConsolePrinter.consolePrinter("Insert reciever: ");
                String reciever = new Scanner(System.in).nextLine();
                try {
                    server.sendPrivateMessage(txt,client.getNickname(),reciever);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "sc" ->{
                try {
                    server.getPublicChatLog(client.getNickname());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case "sp" ->{
                ConsolePrinter.consolePrinter("Insert name of the persone you want to see the chat with: ");
                String reciever = new Scanner(System.in).nextLine();
                try {
                    server.getPrivateChatLog(client.getNickname(),reciever);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }*/

    private void title(){
        ConsolePrinter.consolePrinter("""
                
                 ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗
                ██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝
                ██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗
                ██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║
                ╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║
                 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝
                                                                                                                                   \s
                """);
    }
}
