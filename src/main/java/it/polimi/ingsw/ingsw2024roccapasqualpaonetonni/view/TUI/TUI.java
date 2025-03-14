package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainStaticMethod;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumUpdates;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ViewUpdate;
import org.fusesource.jansi.Ansi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * The type Tui.
 */
public class TUI extends UnicastRemoteObject implements ViewUpdate  {
    /**
     * Instantiates a new Tui.
     *
     * @throws RemoteException the remote exception
     */
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public TUI() throws RemoteException {
    }

    /**
     * Show all.
     *
     * @param gameImmutable the game immutable
     * @param nickname      the nickname
     */
//------------------PREPARATION
    @Override
    public void show_All(GameImmutable gameImmutable, String nickname, EnumUpdates type, boolean myTurn, String playerChangedNickname) {
        if(gameImmutable==null){
            invalidMessage("Game immutable is null", myTurn);
            return;
        }
        ConsolePrinter.consolePrinter(gameImmutable.toString(nickname));
    }

    @Override
    public void show_board(int cardID, String playerChangedNickname) {

    }

    /**
     * Show max num players set.
     *
     * @param max the max
     */
    @Override
    public void show_maxNumPlayersSet(int max) {
        String message = String.format("New max number of players for game: %d players maximum", max);
        ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show created game.
     *
     * @param gameID the game id
     */
    @Override
    public void show_createdGame(int gameID) {
        String message = String.format("Game created, with GameID: %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show you joined game.
     *
     * @param gameID the game id
     */
    @Override
    public void show_youJoinedGame(int gameID) {
        String message = String.format("You joined game %d", gameID);
        ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show no available game.
     */
    @Override
    public void show_noAvailableGame() {
        String message = "No game available, try again \n";
        ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show added new player.
     *
     * @param pNickname the p nickname
     */
    @Override
    public void show_addedNewPlayer(String pNickname) {
        String message = String.format("Player \"%s\" joined the game", pNickname);
        ConsolePrinter.consolePrinter(message);
    }

    /**
     * Show are you ready.
     */
    @Override
    public void show_areYouReady() {
        ConsolePrinter.consolePrinter("Press the key (Y) when you are ready to start the game!");
    }


    /**
     * Join lobby.
     */
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

    /**
     * My running turn draw card.
     */
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

    /**
     * My running turn choose objective.
     */
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

    /**
     * My running turn place starting.
     */
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

    /**
     * Not my turn.
     */
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

    /**
     * Not needed, but here for overriding
     */
    @Override
    public void myTurn() {}

    /**
     * Not my turn chat.
     */
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

    /**
     *
     * @param gameImmutable
     * @param nickname of the owner of the board to update
     */
    @Override
    public void updateOtherBoard(GameImmutable gameImmutable, String nickname) {}

    /**
     * My running turn place card.
     */
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

    /**
     * Show status.
     *
     * @param s the s
     */
    @Override
    public void show_status(String s) {
        ConsolePrinter.consolePrinter("[GAME STATUS]:" +s+ " ");
    }

    /**
     * Show status.
     *
     * @param s the s
     */
    @Override
    public void show_statusLast(String s) {
        ConsolePrinter.consolePrinter("[GAME LAST STATUS]:" +s+ " ");
    }

    /**
     * Winners.
     *
     * @param list the list
     * @param nick the nick
     */
    @Override
    public void winners(List<Player> list, String nick) {
        MainStaticMethod.clearCMD();
        StringBuilder stringBuilder = new StringBuilder();
        boolean win = false;

        stringBuilder.append(ansi().cursor(9,0));
        stringBuilder.append("RESULTS \n");
        for(int i=0;i< list.size();i++){
            int position;
            if(i==0 || list.get(i).getCurrentPoints() != list.get(i-1).getCurrentPoints()){
                position=i+1;
            }
            else {
                position=i;
            }
            if(list.get(i).getNickname().equals(nick) && position==1){
                win=true;
            }
            if(list.get(i).getNickname().equals(nick) && position==1){
                win=false;
            }
            stringBuilder.append(position)
                    .append("\tNick: ").append(list.get(i).getNickname())
                    .append("\tPoints ").append(list.get(i).getCurrentPoints())
                    .append("\n");
        }

        if(win){
            gameOver();
        }
        else {
            winner();
        }

        ConsolePrinter.consolePrinter(stringBuilder);

    }

    @Override
    public void show_generic(String msg) {
        ConsolePrinter.consolePrinter("[INFO]: "+ msg);
    }

    @Override
    public void sendKillView() {
        executorService.submit(()->System.exit(0));
        executorService.shutdown();
    }

    /**
     * Display chat.
     *
     * @param s the s
     */
    @Override
    public void displayChat(String s) {
        ConsolePrinter.consolePrinter(
                String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).a("CHAT \n")
                        .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(s).a("\n"))
        );
    }

    @Override
    public void displayChat(String s, String type, Boolean age) {
        ConsolePrinter.consolePrinter(
                String.valueOf(ansi().fg(Ansi.Color.YELLOW).bg(Ansi.Color.DEFAULT).a("CHAT \n")
                        .fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).a(s).a("\n"))
        );
    }


    /**
     * Invalid message.
     *
     * @param s the s
     */
    @Override
    public void invalidMessage(String s, boolean myTurn) {
        ConsolePrinter.consolePrinter("[ERROR] " + s );
    }

    /**
     * Title.
     */
//------------------PRINTER
    private void title(){
        ConsolePrinter.consolePrinter(ansi().fg(42).a("""
                
                 ██████╗ ██████╗ ██████╗ ███████╗██╗  ██╗    ███╗   ██╗ █████╗ ████████╗██╗   ██╗██████╗  █████╗ ██╗     ██╗███████╗
                ██╔════╝██╔═══██╗██╔══██╗██╔════╝╚██╗██╔╝    ████╗  ██║██╔══██╗╚══██╔══╝██║   ██║██╔══██╗██╔══██╗██║     ██║██╔════╝
                ██║     ██║   ██║██║  ██║█████╗   ╚███╔╝     ██╔██╗ ██║███████║   ██║   ██║   ██║██████╔╝███████║██║     ██║███████╗
                ██║     ██║   ██║██║  ██║██╔══╝   ██╔██╗     ██║╚██╗██║██╔══██║   ██║   ██║   ██║██╔══██╗██╔══██║██║     ██║╚════██║
                ╚██████╗╚██████╔╝██████╔╝███████╗██╔╝ ██╗    ██║ ╚████║██║  ██║   ██║   ╚██████╔╝██║  ██║██║  ██║███████╗██║███████║
                 ╚═════╝ ╚═════╝ ╚═════╝ ╚══════╝╚═╝  ╚═╝    ╚═╝  ╚═══╝╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝
                                                                                                                                   
                """).fg(Ansi.Color.DEFAULT));
    }

    /**
     * Game over.
     */
    private void gameOver(){
        ConsolePrinter.consolePrinter(ansi().fg(42).a("""
                
                 ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗\s
                ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗
                ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝
                ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                 ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
                                                                                                                                                                                                   \s
                """).fg(Ansi.Color.DEFAULT));
    }

    /**
     * Winner.
     */
    private void winner(){
        ConsolePrinter.consolePrinter(ansi().fg(42).a("""
                                
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