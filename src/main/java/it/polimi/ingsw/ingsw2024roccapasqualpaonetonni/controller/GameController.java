package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener.ListenersHandler;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GameController implements GameControllerInterface,Runnable{
    private final Game model;
    private final Random random;
    private final String path;

    //we have to decide weather to make it transient
    private ListenersHandler listenersHandler;
    public GameController(int id) {
        model = new Game(id);
        random = new Random();
        //new Thread(this).start();
        path = "src/main/java/it/polimi/ingsw/ingsw2024roccapasqualpaonetonni/utils/DataBase";
        listenersHandler = new ListenersHandler();
    }

    @SuppressWarnings("BusyWait")
    public void run() {
        while (!Thread.interrupted()) {
            //some code here
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//---------------------------------PLAYER SECTION
    public int getID(){
        return model.getGameId();
    }
    @Override
    public void addPlayer(String nickname){
        Player px;
        int player_number = model.getPlayers().size()+1;
        px = new Player(nickname,player_number);
        try {
            model.addPlayer(px);
            listenersHandler.notify_addedPlayer(this.model);

        }catch (GameAlreadyFullException ex1){
            listenersHandler.notify_fullGame(this.model);
        }
        catch (PlayerAlreadyInException ex2){
            listenersHandler.notify_nameAlreadyInGame(this.model);}

        model.playerIsReadyToStart(px);
    }
    public Queue<Player> getAllPlayer(){
        return model.getPlayers();
    }
    public Player getCurrentPlayer(){
        return model.getCurrentPlayer();
    }
    @Override
    public Boolean isCurrentPlaying(Player p){
        return getCurrentPlayer().equals(p);
    }
    @Override
    public void setMaxNumberOfPlayer(int num) throws RemoteException {
        model.setMaxNumberOfPlayer(num);
        listenersHandler.notify_maxPlayers(this.model);
    }
    public int getMaxNumberOfPlayer(){
        return model.getMaxNumberOfPlayer();
    }
    public void nextTurn(){
        model.nextPlayer();
        listenersHandler.notify_nextTurn(this.model);
    }
    public void reconnectPlayer(String nickname) {
        model.reconnectPlayer(nickname);
        listenersHandler.notify_reconnectedPlayer(this.model);
        model.setStatus(model.getLastStatus());
        model.resetLastStatus();
    }
    public void disconnectPlayer(String nickname) {
        model.disconnectPlayer(nickname);
        listenersHandler.notify_disconnectedPlayer(this.model);
        model.setLastStatus();
        model.setStatus(GameStatus.WAITING_RECONNECTION);
    }
    @Override
    public void removePlayer(Player player){

        model.removePlayer(player);
        listenersHandler.notify_removedPlayer(this.model);
    }
    public GameStatus getGameStatus(){
        return model.getGameStatus();
    }
    public GameStatus getLastStatus(){
        return  model.getLastStatus();
    }
    public Boolean playersAreReady(){
        return model.arePlayerReady();
    }


//---------------------------------TABLE AND INIT SECTION
    @Override
    public Boolean createTable(){
        if(model.arePlayerReady()) {
            Map<String, List<Card>> cardsMap = null;
            try {
                cardsMap = JSONUtils.createCardsFromJson(path);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Map<String, Queue<Card>> shuffledDecks = new HashMap<>();
            for (Map.Entry<String, List<Card>> entry : cardsMap.entrySet()) {
                String type = entry.getKey(); // Get the card type
                List<Card> cards = entry.getValue(); // Get the list of cards for this type

                // Shuffle the list of cards
                Collections.shuffle(cards);

                // Create a new deck as a Queue
                Queue<Card> deck = new LinkedList<>(cards);

                // Put the shuffled deck into the map
                shuffledDecks.put(type, deck);
            }
            //create decks
            DrawableDeck decks = new DrawableDeck(shuffledDecks);
            BoardDeck boardDeck = new BoardDeck();

            //set the BoardDeck
            boardDeck.setObjectiveCards(decks.drawFirstObjective(),0);
            boardDeck.setObjectiveCards(decks.drawFirstObjective(),1);
            boardDeck.setResourceCards(decks.drawFirstResource(),0);
            boardDeck.setResourceCards(decks.drawFirstResource(),1);
            boardDeck.setGoldCards(decks.drawFirstGold(),0);
            boardDeck.setGoldCards(decks.drawFirstGold(),1);

            model.setGameDrawableDeck(decks);
            model.setGameBoardDeck(boardDeck);

            //random first player
            randomFirstPlayer();

            //run TurnZero
            turnZero();

            model.setStatus(GameStatus.RUNNING);
            listenersHandler.notify_tableCreated(this.model);
            return true;
        }
        else {
            listenersHandler.notify_playersNotReady(this.model);
            return false;
        }
    }
    private void randomFirstPlayer(){
        int first = random.nextInt(4);

        for(int i=0; i<first; i++){
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
        listenersHandler.notify_firstPlayerSet(this.model);
    }
    private void turnZero() {
        for(Player player : getAllPlayer()){
            player.drawStarting(model.getGameDrawableDeck());
            player.drawGoals(model.getGameDrawableDeck());
            player.drawResourcesFromDeck(model.getGameDrawableDeck());
            player.drawResourcesFromDeck(model.getGameDrawableDeck());
            player.drawGoldFromDeck(model.getGameDrawableDeck());
        }
    }


//---------------------------------ADD CARD SECTION
    public void addCard(PlayingCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))){
            // listener you cannot draw in this phase
            return;
        }
        if(flip){
            cardToAdd.flip();
        }
        getCurrentPlayer().addToBoard(cardToAdd,cardOnBoard,cornerToAttach);
        checkPoints20Points();
        listenersHandler.notify_cardAdded(this.model);
    }
    public void addStartingCard(Boolean flip){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) || model.getGameStatus().equals(GameStatus.LAST_TURN))){
            // listener you cannot draw in this phase
            return;
        }
        if(flip){
            getCurrentPlayer().getStartingCard().flip();
        }
        getCurrentPlayer().addStarting();
        listenersHandler.notify_startingCardAdded(this.model);
    }
    public void choosePlayerGoal(int choice){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING))){
            // listener you cannot draw in this phase
            return;
        }
        getCurrentPlayer().chooseGoal(choice);
        listenersHandler.notify_goalChosen(this.model);
    }


//---------------------------------DRAW SECTION
    private boolean decksAreAllEmpty() {
        return model.getGameDrawableDeck().getDecks().get("resources").isEmpty()
                && model.getGameDrawableDeck().getDecks().get("gold").isEmpty()
                && model.getGameBoardDeck().getResourceCards()[0] == null
                && model.getGameBoardDeck().getResourceCards()[1] == null
                && model.getGameBoardDeck().getGoldCards()[0] == null
                && model.getGameBoardDeck().getGoldCards()[1] == null;
    }
    public void drawResourceFromDeck(){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) )){
            // listener you cannot draw in this phase
            listenersHandler.notify_cannotDrawHere(this.model);
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);
            listenersHandler.notify_decksAllEmpty(this.model);

        }
        else if(!model.getGameDrawableDeck().getDecks().get("resources").isEmpty()){
            getCurrentPlayer().drawResourcesFromDeck(model.getGameDrawableDeck());
            listenersHandler.notify_resourceDrawn(this.model);
        }
        else {
            // listener change deck
            listenersHandler.notify_changeResourceDeck(this.model);
            return;
        }

    }
    public void drawGoldFromDeck(){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) )){
            // listener you cannot draw in this phase
            listenersHandler.notify_cannotDrawHere(this.model);
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);
            listenersHandler.notify_decksAllEmpty(this.model);

        }
        else if(!model.getGameDrawableDeck().getDecks().get("gold").isEmpty()) {
            getCurrentPlayer().drawGoldFromDeck(model.getGameDrawableDeck());
            listenersHandler.notify_goldDrawn(this.model);
        }
        else {
            // listener change deck
            listenersHandler.notify_changeGoldDeck(this.model);
            return;
        }
    }
    public void drawFromBoard(int position){
        if(!(model.getGameStatus().equals(GameStatus.RUNNING) || model.getGameStatus().equals(GameStatus.WAITING_LAST_TURN) )){
            // listener you cannot draw in this phase
            listenersHandler.notify_cannotDrawHere(this.model);
            return;
        }
        if (decksAreAllEmpty()) {
            model.setStatus(GameStatus.WAITING_LAST_TURN);
            listenersHandler.notify_decksAllEmpty(this.model);

        }
        else if((position <= 2 && model.getGameBoardDeck().getResourceCards()[position-1]!=null) ||
                (position >  2 && model.getGameBoardDeck().getGoldCards()[position-3] !=null)) {
            getCurrentPlayer().drawFromBoard(position,model.getGameBoardDeck(),model.getGameDrawableDeck());
            listenersHandler.notify_drewFromBoard(this.model);
        }
        else {
            // listener change deck
            listenersHandler.notify_changeBoardDeck(this.model);
            return;
        }
    }


//---------------------------------END SECTION
    private void checkPoints20Points(){
        for(Player player: getAllPlayer()){
            // ATTENZIONE: aggiornare il currentPlayer a fine turno, prima di chiamare questa funzione
            if(player.getCurrentPoints() >= 20){
                model.setStatus(GameStatus.WAITING_LAST_TURN);
            }
        }
    }
    public void checkWinner(){
        model.checkWinner();
        model.setStatus(GameStatus.ENDED);
    }

//---------------------------------GET SECTION TO DISPLAY THE PUBLIC PART
    public Game getGame(){

        listenersHandler.notify_gotGame(this.model);
        return model;
    }
    public int[] getAllPoints(){
        int[] points = new int[model.getPlayers().size()];
        for(Player p : getAllPlayer()){
            points[p.getColorPlayer()-1] = p.getCurrentPoints();
        }
        listenersHandler.notify_gotPoints(this.model);
        return points;
    }
    public BoardDeck getBoardDeck(){

        listenersHandler.notify_gotBoardDeck(this.model);
        return model.getGameBoardDeck();
    }
    public Card[] getSeedUpDrawableDeck(){
        Card[] up2Deck = new PlayingCard[2];
        up2Deck[0]= model.getGameDrawableDeck().getDecks().get("resources").peek();
        up2Deck[1]= model.getGameDrawableDeck().getDecks().get("gold").peek();

        return  up2Deck;
    }


}
