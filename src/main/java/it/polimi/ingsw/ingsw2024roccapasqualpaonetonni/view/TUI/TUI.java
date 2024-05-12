package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class TUI extends UnicastRemoteObject implements ViewUpdate  {
    public TUI() throws RemoteException {
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
        ConsolePrinter.consolePrinter("Press the key (Y) when you are ready to start the game!");
    }
    @Override
    public void preparation() {
        ConsolePrinter.consolePrinter("We are waiting for all players to be ready...");
    }

    @Override
    public void joinLobby(){
        title();
        ConsolePrinter.consolePrinter(
                """ 
                Welcome! Select your action:
                    (/new + playerNumbers + Nickname)-> Create a new game
                    (/join + Nickname)-> Join the first available game
                    (/joinById + Nickname + gameID) Join a game by game ID
                    (/reconnect + Nickname + gameID) Reconnect to a Game
                """
        );
    }
    @Override
    public void myRunningTurn() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn. Remember firstly you place a card then you can draw
                    (/addStarting + flipped(true,false)) -> to add the starting card
                    (/choseGoal + choice(1,2)) -> to chose my personal goal
                    (/addCard + handIndex + idCardToAttach + position (1,2,3,4) + flipped (true,false)) -> to add a new card
                    (/drawGold) -> to draw a gold card
                    (/drawResources) -> to draw a resource card
                    (/drawBoard + position (1,2,3,4)) -> to draw from common board
                    (/chat + message) -> send a public message
                    (/chatPrivate + receiver + message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
             
                """
        );
    }
    @Override
    public void notMyTurn() {
        ConsolePrinter.consolePrinter(
                """
                It's not your turn. You can
                    (/chat + message) -> send a public message
                    (/chatPrivate + receiver + message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                    
                """
        );
    }
    @Override
    public void invalidMessage() {
        ConsolePrinter.consolePrinter("Invalid Input " );
    }

    @Override
    public void invalidMessage(String s) {
        ConsolePrinter.consolePrinter("Invalid: " + s );
    }

    private void title(){
        System.out.println(Ansi.ansi().fg(42).a("""
                
                 ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗
                ██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝
                ██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗
                ██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║
                ╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║
                 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝
                                                                                                                                   \s
                """).fg(Ansi.Color.DEFAULT));
    }
    private void gameOver(){
        System.out.println(Ansi.ansi().fg(42).a("""
                
                 ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗\s
                ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗
                ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝
                ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                 ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
                                                                                         \s
                                                                                                                                                                                                    \s
                """).fg(Ansi.Color.DEFAULT));
    }
    private void winner(){
        System.out.println(Ansi.ansi().fg(42).a("""
                                
                ██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗\s
                ██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗
                ██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝ \s
                ██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗ \s
                ╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║ \s
                 ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝ \s
                                                                         
                                                                                                                                                                                                    \s
                """).fg(Ansi.Color.DEFAULT));
    }
}

