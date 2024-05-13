package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class TUI extends UnicastRemoteObject implements ViewUpdate  {
    public TUI() throws RemoteException {
    }

    //------------------PREPARATION
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


    //------------------TURN
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
    public void myRunningTurnDrawCard() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn.
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
    public void myRunningTurnChooseObjective() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn.
                    (/choseGoal + choice(1,2)) -> to chose my personal goal
                    (/chat + message) -> send a public message
                    (/chatPrivate + receiver + message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
             
                """
        );
    }
    @Override
    public void myRunningTurnPlaceStarting() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn.
                    (/addStarting + flipped(true,false)) -> to add the starting card
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
                It's not your turn.
                    (/chat + message) -> send a public message
                    (/chatPrivate + receiver + message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                    
                """
        );
    }

    @Override
    public void myRunningTurnPlaceCard() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn.
                    (/addCard + handIndex + idCardToAttach + position (1,2,3,4) + flipped (true,false)) -> to add a new card
                    (/chat + message) -> send a public message
                    (/chatPrivate + receiver + message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
             
                """
        );
    }

    @Override
    public void displayChat(String s) {
        ConsolePrinter.consolePrinter("CHAT \n"+s+"\n");
    }

    //------------------MESSAGE
    @Override
    public void invalidMessage() {
        ConsolePrinter.consolePrinter("[ERROR] Invalid Input. Choose another option !!! " );
    }
    @Override
    public void invalidMessage(String s) {
        ConsolePrinter.consolePrinter("[ERROR] " + s );
    }

    //------------------PRINTER
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