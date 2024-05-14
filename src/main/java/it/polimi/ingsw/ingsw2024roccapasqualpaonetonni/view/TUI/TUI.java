package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;


public class TUI extends UnicastRemoteObject implements ViewUpdate  {
    public TUI() throws RemoteException {
    }

    //------------------PREPARATION
    @Override
    public void show_All(GameImmutable gameImmutable, String nickname) {
        if(gameImmutable==null){
            invalidMessage("Game immutable is null");
            return;
        }
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
                    (/new playerNumbers Nickname)-> Create a new game (2-4 players)
                    (/join Nickname)-> Join the first available game
                    (/joinById Nickname gameID) Join a game by game ID
                    (/reconnect Nickname gameID) Reconnect to a Game
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
                    (/drawBoard position ) -> to draw from common board (position can be (1,2,3,4))
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
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
                    (/choseGoal choice) -> to chose my personal goal (choice can be 1 or 2)
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                """
        );
    }
    @Override
    public void myRunningTurnPlaceStarting() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn.
                    (/addStarting flipped) -> to add the starting card (flipped can be true or false)
                    (/addStarting ) -> to add the starting card (flipped set by default as false)
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                """
        );
    }
    @Override
    public void notMyTurn() {
        ConsolePrinter.consolePrinter(
                """
                It's not your turn.
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                """
        );
    }
    @Override
    public void notMyTurnChat() {
        ConsolePrinter.consolePrinter(
                """
                Waiting others player Connection.
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                """
        );
    }

    @Override
    public void myRunningTurnPlaceCard() {
        ConsolePrinter.consolePrinter(
                """
                It's your turn. CornerPosition is X if you want to attach in corner CX
                    (/addCard handIndex idCardToAttach cornerPosition flipped) -> to add a new card (flipped can be true ore false)
                    (/addCard handIndex idCardToAttach cornerPosition ) -> to add a new card (default not flipped)
                    (/chat message) -> send a public message
                    (/chatPrivate receiver message) -> send a private message
                    (/seeChat) -> see all the public chat
                    (/seeChatPrivate + privateChatter) -> see my private chat
                    (/leave) -> to leave the game
                """
        );
    }

    @Override
    public void show_status(String s) {
        ConsolePrinter.consolePrinter("[GAME STATUS]:" +s+ "\n");
    }

    @Override
    public void winners(List<Player> list, String nick) {
        if(list.stream().filter(player -> player.getNickname().equals(nick)).toList().isEmpty()){
            gameOver();
        }
        else {
            winner();
        }

        ConsolePrinter.consolePrinter("WINNERS: \n");
        list.forEach(player -> ConsolePrinter.consolePrinter(player.getNickname()));

    }

    @Override
    public void displayChat(String s) {
        ConsolePrinter.consolePrinter(
                String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).a("CHAT \n")
                        .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(s).a("\n"))
        );
    }


    @Override
    public void invalidMessage(String s) {
        ConsolePrinter.consolePrinter("[ERROR] " + s );
    }

    //------------------PRINTER
    private void title(){
        System.out.println(ansi().fg(42).a("""
                
                 ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗
                ██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝
                ██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗
                ██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║
                ╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║
                 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝
                                                                                                                                   
                """).fg(Ansi.Color.DEFAULT));
    }
    private void gameOver(){
        System.out.println(ansi().fg(42).a("""
                
                 ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗\s
                ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗
                ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝
                ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                 ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
                                                                                                                                                                                                   \s
                """).fg(Ansi.Color.DEFAULT));
    }
    private void winner(){
        System.out.println(ansi().fg(42).a("""
                                
                ██╗    ██╗██╗███╗   ██╗███╗   ██╗███████╗██████╗
                ██║    ██║██║████╗  ██║████╗  ██║██╔════╝██╔══██╗
                ██║ █╗ ██║██║██╔██╗ ██║██╔██╗ ██║█████╗  ██████╔╝
                ██║███╗██║██║██║╚██╗██║██║╚██╗██║██╔══╝  ██╔══██╗
                ╚███╔███╔╝██║██║ ╚████║██║ ╚████║███████╗██║  ██║
                 ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝
                                                                                                                                                                                                    \s
                """).fg(Ansi.Color.DEFAULT));
    }

}