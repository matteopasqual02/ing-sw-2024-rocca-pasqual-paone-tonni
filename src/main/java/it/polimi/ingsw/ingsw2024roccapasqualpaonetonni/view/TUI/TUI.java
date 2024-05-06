package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class TUI implements ViewUpdate {
    @Override
    public void joinLobby(Client client){
        String myNickname = null;
        ServerInterface server = client.getServerInterface();
        int myGameID =0;
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
        client.setMyNickname(myNickname);
    }

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
