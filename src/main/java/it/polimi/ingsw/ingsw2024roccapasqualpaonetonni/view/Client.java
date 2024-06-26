package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.BoardDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.DrawableDeck;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.GameStatus;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Message;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.PrivateMessage;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServerStub;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ServerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main.MainStaticMethod;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client.SocketClient;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultModelValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.ScannerTUI;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI.TUI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Objects;

/**
 * The type Client.
 */
public class Client extends UnicastRemoteObject implements GameListener{
    /**
     * The Server.
     */
    private ServerInterface server;
    /**
     * The View.
     */
    private ViewUpdate view;
    /**
     * My game id.
     */
    private int myGameId;
    /**
     * My nickname.
     */
    private String myNickname;
    /**
     * The State.
     */
    private GameStatus state;
    /**
     * My turn.
     */
    private Boolean myTurn;
    /**
     * The Current immutable.
     */
    private GameImmutable currentImmutable;
    /**
     * Player is in a game.
     */
    private Boolean inGame;
    /**
     * Pong Thread.
     */
    private transient final PingPongThreadClient pongThread;

    /**
     * Instantiates a new Client.
     *
     * @param connectionType the connection type
     * @param viewType       the view type
     * @throws IOException the io exception
     */
    public Client(EnumConnectionType connectionType, EnumViewType viewType) throws IOException {
        this.myGameId = 0;
        this.myNickname = null;
        this.server = null;
        this.currentImmutable = null;
        this.state = null;
        this.inGame = false;
        this.myTurn = false;

        this.pongThread = new PingPongThreadClient();

        switch (connectionType){
            case RMI -> server = new RMIServerStub();
            case SOCKET -> server = new SocketClient(this);
            case null, default -> {
                return;
            }
        }

        switch (viewType){
            case TUI ->{
                view = new TUI();
                new ScannerTUI(this);
                MainStaticMethod.clearCMD();
            }
            case GUI ->{
                view = new GUI(this);
                MainStaticMethod.clearCMD();
            }
        }

        view.joinLobby();
    }


    /**
     * Receive input.
     *
     * @param input the input
     * @throws IOException       the io exception
     * @throws NotBoundException the not bound exception
     */
    public synchronized void receiveInput(String input) throws IOException, NotBoundException {
        if(input == null){
            view.invalidMessage("empty input", myTurn);
            return;
        }
        String[] parole = input.split(" ");

        switch (parole[0]) {
            case "/new" -> {
                if(!inGame && parole.length==3){
                    inGame =true;
                    try{
                        int maxNumPlayers= Integer.parseInt(parole[1]);
                        if (maxNumPlayers<2 || maxNumPlayers>4){
                            view.invalidMessage("Invalid number of players", myTurn);
                            return;
                        }
                        myNickname = parole[2];
                        server.createGame(myNickname, maxNumPlayers, this);
                    }
                    catch (IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format", myTurn);
                    }

                }
                else {
                    view.invalidMessage("Command not complete", myTurn);
                }
            }
            case "/join" -> {
                if(!inGame && parole.length==2){
                    inGame =true;
                    try{
                        myNickname = parole[1];
                        server.joinFirstAvailable(myNickname, this);
                    }
                    catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format", myTurn);
                    }

                }
                else {
                    view.invalidMessage("Command not complete", myTurn);
                }
            }
            case "/joinById","/joinbyid" -> {
                if(!inGame && parole.length==3){
                    inGame =true;
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.joinGameByID(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("Command not complete", myTurn);
                }
            }
            case "/reconnect" -> {
                if(!inGame && parole.length==3){
                    inGame =true;
                    try{
                        myNickname = parole[1];
                        int gameId= Integer.parseInt(parole[2]);
                        server.reconnect(myNickname,gameId,this);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format", myTurn);
                    }

                }
                else {
                    view.invalidMessage("Command not complete", myTurn);
                }
            }
            case "Y","y" -> {
                if(state==null || state.equals(GameStatus.PREPARATION)){
                    view.notMyTurnChat();
                    server.ready(myNickname);
                }
                else {
                    view.invalidMessage("You cannot be ready now (or you are already ready)", myTurn);
                }
            }
            case "/addStarting","/addstarting" -> {
                ConsolePrinter.consolePrinter("Client addStarting");
                if(state==GameStatus.RUNNING && myTurn!=null && myTurn){
                    try{
                        if(parole.length==1){
                            server.addStartingCard(myNickname, false);
                            return;
                        }
                        server.addStartingCard(myNickname, Objects.equals(parole[1], "true"));
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Wrong format", myTurn);
                    }

                }
                else {
                    view.invalidMessage("Not my turn (or game is waiting)", Boolean.TRUE.equals(myTurn));
                }
            }
            case "/choseGoal","/chosegoal" -> {
                if(state==GameStatus.RUNNING  && myTurn!=null && myTurn && parole.length==2){
                    Player me = currentImmutable.getPlayers().stream().filter(player -> myNickname.equals(player.getNickname())).toList().getFirst();
                    if(me.getGoal()!=null){
                        view.invalidMessage("Goal already chosen", myTurn);
                    }
                    try {
                        int pos = Integer.parseInt(parole[1]);
                        server.choosePlayerGoal(myNickname,pos-1);
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid Format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("Command not complete", Boolean.TRUE.equals(myTurn));
                }
            }
            case "/addCard", "/addcard" -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN || state==GameStatus.LAST_TURN)
                        && myTurn!=null && parole.length>=4){
                    try{
                        Player me = currentImmutable.getPlayers().stream().filter(player -> myNickname.equals(player.getNickname())).toList().getFirst();
                        if(me==null){return;}
                        int card1 = Integer.parseInt(parole[1])-1;
                        PlayingCard c1 = me.getHand().get(card1);
                        int card2 = Integer.parseInt(parole[2]);
                        PlayingCard[][] board = me.getBoard().getBoardMatrix();
                        int pos = Integer.parseInt(parole[3]);

                        for (PlayingCard[] playingCards : board) {
                            for (PlayingCard playingCard : playingCards) {
                                if (playingCard != null && playingCard.getIdCard() == card2){
                                    if(parole.length==4){
                                        server.addCard(myNickname, c1, playingCard, pos , false);
                                        return;
                                    }
                                    server.addCard(myNickname, c1, playingCard, pos , Objects.equals(parole[4], "true"));
                                    return;
                                }
                            }
                        }
                    }
                    catch(IndexOutOfBoundsException | NumberFormatException e){
                        view.invalidMessage("Invalid number format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("Command not complete or not your turn", Boolean.TRUE.equals(myTurn));
                }
            }
            case "/drawGold","/drawgold" -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN ) && myTurn!=null && myTurn){
                    server.drawGoldFromDeck(myNickname);
                }
                else {
                    view.invalidMessage("Not your Turn or last phase", Boolean.TRUE.equals(myTurn));
                }
            }
            case "/drawResources","/drawresources"  -> {
                if((state==GameStatus.RUNNING || state==GameStatus.WAITING_LAST_TURN )&& myTurn!=null && myTurn){
                    server.drawResourceFromDeck(myNickname);
                }
                else {
                    view.invalidMessage("Not your Turn or last phase", Boolean.TRUE.equals(myTurn));
                }
            }
            case "/drawBoard","/drawboard" -> {
                if (myTurn != null) {
                    if (state == GameStatus.RUNNING || state == GameStatus.WAITING_LAST_TURN) {
                        if (parole.length == 2) {
                            try{
                                int pos = Integer.parseInt(parole[1]);
                                server.drawFromBoard(myNickname,pos);
                            }catch(IndexOutOfBoundsException | NumberFormatException e){
                                view.invalidMessage("Invalid number format", myTurn);
                            }
                        }
                        else {
                            view.invalidMessage("Invalid command", myTurn);
                        }
                    }
                    else {
                        //ConsolePrinter.consolePrinter("Phase: " + state.toString());
                        view.invalidMessage("Last phase, can't draw", myTurn);
                    }
                }
                else {
                    view.invalidMessage("Not your turn", false);
                }
            }
            case "/chat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    try{
                        StringBuilder builder = new StringBuilder();
                        for(int i=1; i<parole.length; i++){
                            builder.append(parole[i]).append(" ");
                        }
                        String msg = builder.toString();
                        server.sendMessage(msg,myNickname);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("The game is waiting", myTurn);
                }
            }
            case "/chatPrivate","/chatprivate" -> {
                if(state!=GameStatus.WAITING_RECONNECTION && parole.length>=2){
                    try{
                        StringBuilder builder = new StringBuilder();
                        for(int i=2; i<parole.length; i++){
                            builder.append(parole[i]).append(" ");
                        }
                        String msg = builder.toString();
                        server.sendPrivateMessage(msg, myNickname, parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("The game is waiting or receiver missing", myTurn);
                }
            }
            case "/seeChat", "/seechat" -> {
                if(state!=GameStatus.WAITING_RECONNECTION){
                    server.getPublicChatLog(myNickname);
                }
                else {
                    view.invalidMessage("The game is waiting", myTurn);
                }
            }
            case "/seeChatPrivate" ,"/seechatprivate"-> {
                if(state!=GameStatus.WAITING_RECONNECTION && parole.length==2){
                    try{
                        server.getPrivateChatLog(myNickname, parole[1]);
                    }catch(IndexOutOfBoundsException e){
                        view.invalidMessage("Invalid format", myTurn);
                    }
                }
                else {
                    view.invalidMessage("The game is waiting or private chatter missing", myTurn);
                }
            }
            case "/leave" -> {
                if(inGame){
                    server.leave(myNickname, myGameId);
                }else {
                    System.exit(0);
                }
            }
            case null, default -> view.invalidMessage("Invalid command", myTurn);
        }
    }

    //-------------------------------------  GET ------------------------------------------------------------------------------


    /**
     * Gets nickname.
     *
     * @return the nickname
     * @throws RemoteException the remote exception
     */
    @Override
    public String getNickname() throws RemoteException {
        return myNickname;
    }

    //-------------------------------------OVERRIDE SECTION -----------------------------------------------------------------------

    /**
     * Max num players set.
     *
     * @param max the max
     */
    @Override
    public void maxNumPlayersSet(int max) {
        view.show_maxNumPlayersSet(max);
    }

    /**
     * Created game.
     *
     * @param gameId the game id
     */
    @Override
    public void createdGame(int gameId) {
        myGameId = gameId;
        view.show_createdGame(gameId);
    }

    /**
     * You joined game.
     *
     * @param gameId the game id
     */
    @Override
    public void youJoinedGame(int gameId) {
        myGameId = gameId;
        view.show_youJoinedGame(gameId);
        this.pongThread.start();
    }

    /**
     * No available game.
     */
    @Override
    public void noAvailableGame() {
        inGame =false;
        view.show_noAvailableGame();
        view.joinLobby();
    }

    /**
     * Added new player.
     *
     * @param pNickname the p nickname
     */
    @Override
    public void addedNewPlayer(String pNickname) {
        view.show_addedNewPlayer(pNickname);
    }

    /**
     * Are you ready
     */
    @Override
    public void areYouReady() {
        MainStaticMethod.clearCMD();
        view.show_areYouReady();
    }

    /**
     * All game.
     *
     * @param gameImmutable the game immutable
     */
    @Override
    public void allGame(GameImmutable gameImmutable) {
        MainStaticMethod.clearCMD();
        if(gameImmutable.getStatus()==GameStatus.PREPARATION){
            view.show_areYouReady();
            return;
        }
        currentImmutable=gameImmutable;
        statusSet(currentImmutable.getStatus());
        myGameId=currentImmutable.getGameId();
        view.show_All(gameImmutable,myNickname,EnumUpdates.ALL, myTurn,"no nickname");
        Player player = currentImmutable.getPlayers().peek();
        if(player==null){
            view.show_generic("error try to restart all");
            return;
        }
        myTurn = myNickname.equals(player.getNickname());
        switchShowTurn(player);
    }

    /**
     * Next turn.
     *
     * @param nickname the nickname
     */
    @Override
    public void nextTurn(String nickname) {
        ConsolePrinter.consolePrinter("New turn: " + nickname);
        myTurn = myNickname.equals(nickname);
        if(currentImmutable==null){return;}
        Player player = currentImmutable.getPlayers().stream().filter(player1 -> nickname.equals(player1.getNickname())).toList().getFirst();
        switchShowTurn(player);
        //ConsolePrinter.consolePrinter(state.toString());
    }

    /**
     * switch to Show Turn
     *
     * @param player the player
     */
    private void switchShowTurn(Player player) {
        if(myTurn){
            view.myTurn();
            if (player.getBoard().getBoardMatrix()[player.getBoard().getDim_x()/2][player.getBoard().getDim_y()/2]==null){
                view.myRunningTurnPlaceStarting();
                return;
            }
            if(player.getGoal()==null){
                view.myRunningTurnChooseObjective();
                return;
            }
            if(player.getHand().size() == DefaultModelValues.Default_Hand_Dimension){
                view.myRunningTurnPlaceCard();
                return;
            }
            if (state!=GameStatus.LAST_TURN) {
                view.myRunningTurnDrawCard();
            }
        }
        else {
            view.notMyTurn();
        }
    }

    /**
     * Start added.
     *
     * @param p the p
     */
    @Override
    public void startAdded(Player p) {
        MainStaticMethod.clearCMD();
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname,EnumUpdates.START, myTurn,p.getNickname());
        if(myTurn){
            view.myRunningTurnChooseObjective();
        }
        else{
            view.notMyTurn();
            view.updateOtherBoard(currentImmutable, p.getNickname());
        }
    }

    /**
     * Card added.
     *
     * @param p the p
     */
    @Override
    public void cardAdded(Player p, int cardID) {
        MainStaticMethod.clearCMD();
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable, myNickname, EnumUpdates.BOARD, myTurn, p.getNickname());
        if(myTurn && state!=GameStatus.LAST_TURN){
            view.myRunningTurnDrawCard();
        }
        else{
            view.notMyTurn();
            view.updateOtherBoard(currentImmutable, p.getNickname());
        }
    }

    /**
     * Personal goal chosen.
     *
     * @param p the p
     */
    @Override
    public void personalGoalChosen(Player p) {
        MainStaticMethod.clearCMD();
        currentImmutable.refreshPlayer(p);
        view.show_All(currentImmutable,myNickname,EnumUpdates.OBJECTIVE, myTurn,p.getNickname());
        if(myTurn){
            view.myRunningTurnPlaceCard();
        }
        else{
            view.notMyTurn();
        }
    }

    /**
     * Status set.
     *
     * @param status the status
     */
    @Override
    public void statusSet(GameStatus status) {
        state=status;
        view.show_status(state.toString());
    }

    /**
     * Resource drawn.
     *
     * @param p the p
     * @param d the d
     */
    @Override
    public void resourceDrawn(Player p, DrawableDeck d) {
        MainStaticMethod.clearCMD();
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            view.show_All(currentImmutable,myNickname,EnumUpdates.BOARDDECK, myTurn,p.getNickname());
        }

    }

    /**
     * Gold drawn.
     *
     * @param p the p
     * @param d the d
     */
    @Override
    public void goldDrawn(Player p, DrawableDeck d) {
        MainStaticMethod.clearCMD();
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            view.show_All(currentImmutable,myNickname,EnumUpdates.BOARDDECK, myTurn,p.getNickname());
        }
    }

    /**
     * Drew from board.
     *
     * @param p the p
     * @param b the b
     * @param d the d
     */
    @Override
    public void drewFromBoard(Player p, BoardDeck b, DrawableDeck d) {
        MainStaticMethod.clearCMD();
        //ConsolePrinter.consolePrinter("Client notified of drewFromBoard");
        if(currentImmutable!=null){
            currentImmutable.refreshPlayer(p);
            currentImmutable.setDrawableDeck(d);
            currentImmutable.setBoardDeck(b);
            view.show_All(currentImmutable,myNickname,EnumUpdates.BOARDDECK, myTurn,p.getNickname());
        }
    }

    /**
     * Generic error.
     *
     * @param s the s
     * @throws RemoteException the remote exception
     */
    @Override
    public void genericError(String s) throws RemoteException {
        view.invalidMessage(s, myTurn);
    }

    /**
     * Winners.
     *
     * @param list the list
     */
    @Override
    public void winners(List<Player> list) {
        MainStaticMethod.clearCMD();
        view.winners(list,myNickname);
    }


    /**
     * Reconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void reconnectedPlayer(String nickname) {
        view.show_generic("Player "+nickname+" has been reconnected");
    }

    /**
     * Reconnection impossible.
     *
     * @param nickname the nickname
     */
    @Override
    public void reconnectionImpossible(String nickname) {
        view.show_generic("Player "+nickname+" reconnection failed");
    }

    /**
     * Disconnected player.
     *
     * @param nickname the nickname
     */
    @Override
    public void disconnectedPlayer(String nickname) {
        view.show_generic("Player "+nickname+" has been disconnected");
    }

    @Override
    public void sendKill(String nickname) {
        if(nickname.equals(myNickname)){
            inGame =false;
            currentImmutable=null;
            state=null;
            view.show_generic("You have left game " + myGameId);
            view.sendKillView();
        }
    }
    /**
     * Status set to last status.
     *
     * @param status the status
     */
    @Override
    public void statusSetToLastStatus(GameStatus status) {
        view.show_statusLast(status.toString());
    }

    /**
     * Last status reset.
     */
    @Override
    public void lastStatusReset() {
        view.show_statusLast("Reset");
    }

//--------------------------CHAT
    /**
     * New message.
     *
     * @param m the m
     * @throws RemoteException the remote exception
     */
    @Override
    public void newMessage(Message m) throws RemoteException {
        if (view instanceof TUI) {
            view.displayChat(m.toStringTUI());
        }
        else {
            view.displayChat(m.toStringGUI(), "Pub", true);
        }
    }

    /**
     * New private message.
     *
     * @param m the m
     * @throws RemoteException the remote exception
     */
    @Override
    public void newPrivateMessage(PrivateMessage m) throws RemoteException {
        if (view instanceof TUI) {
            view.displayChat(m.toStringTUI());
        }
        else {
            view.displayChat(m.toStringGUI(), "Priv", true);
        }
    }

    /**
     * Public chat log.
     *
     * @param allMessages the all messages
     * @throws RemoteException the remote exception
     */
    @Override
    public void publicChatLog(List<Message> allMessages) throws RemoteException {
        if(allMessages!=null){
            StringBuilder chat = new StringBuilder();
            for(Message m: allMessages) {
                if (view instanceof TUI) {
                    chat.append(m.toStringTUI());
                }
                else {
                    chat.append(m.toStringGUI());
                }
            }
            if (view instanceof TUI) {
                view.displayChat(chat.toString());
            }
            else {
                view.displayChat(chat.toString(), "Pub", false);
            }
        }
        else {
            view.displayChat("No messages available");
        }
    }

    /**
     * Private chat log.
     *
     * @param otherName   the other name
     * @param privateChat the private chat
     * @throws RemoteException the remote exception
     */
    @Override
    public void privateChatLog(String otherName, List<PrivateMessage> privateChat) throws RemoteException {
        if(privateChat!=null){
            StringBuilder chat = new StringBuilder();
            for(PrivateMessage m: privateChat){
                if (view instanceof TUI) {
                    chat.append(m.toStringTUI());
                }
                else {
                    chat.append(m.toStringGUI());
                }
            }
            if (view instanceof TUI) {
                view.displayChat(chat.toString());
            }
            else {
                view.displayChat(chat.toString(), "Priv", false);
            }
        }
        else {
            view.displayChat("No available messages with: " + otherName);
        }
    }


    //--------------------------PING PONG
    /**
     * The type Ping pong thread client.
     */
    private static class PingPongThreadClient extends Thread {
        /**
         * The Pinged.
         */
        private boolean pinged = false;
        /**
         * The Lock.
         */
        private final Object  lock = new Object();

        /**
         * Pinged.
         */
        public void pinged() {
            synchronized (lock) {
                pinged = true;
            }
        }

        /**
         * Run.
         */
        @SuppressWarnings("BusyWait")
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    Thread.sleep(40000);
                }
                catch (InterruptedException e) {
                    ConsolePrinter.consolePrinter("ping pong crashed");
                }
                synchronized (lock) {
                    if (!pinged) {
                        ConsolePrinter.consolePrinter("server dead game interrupted");
                        this.interrupt();
                        System.exit(0);
                    } else {
                        pinged = false;
                    }
                }
            }
        }
    }

    /**
     * Ping.
     */
    @Override
    public void ping() {
        pongThread.pinged();
        try {
            server.pong(this.myNickname);
            //ConsolePrinter.consolePrinter("pinged");
        }
        catch (Exception e) {
            ConsolePrinter.consolePrinter("[ERROR]: ping pong failed");
        }
    }

}
