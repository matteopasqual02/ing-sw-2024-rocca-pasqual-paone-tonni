package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.View;

import java.io.IOException;
import java.util.*;

import java.util.*;

public class GameController implements Runnable{
    private Game model;
    private View view;
    private final Random random = new Random();
    private final String path = "it/polimi/ingsw/ingsw2024roccapasqualpaonetonni/utils/JSONUtils.java";

    public GameController() {
        model = new Game();
        new Thread(this).start();
    }

    public void run() {
        while (!Thread.interrupted()) {

        }
    }

//---------------------------------PLAYER SECTION
    public void addPlayer(String nickname){
        Player px;
        int player_number = model.getPlayers().size()-1;
        px = new Player(nickname,player_number);
        try {
            model.addPlayer(px);

        }catch (GameAlreadyFullException ex1){/*_*/}
        catch (PlayerAlreadyInException ex2){/**/};

        model.playerIsReadyToStart(px);
    }
    public Queue<Player> getAllPlayer(){
        return model.getPlayers();
    }
    private Player getCurrentPlayer(){
        return model.getCurrentPlayer();
    }
    public Boolean isCurrentPlaying(Player p){
        return p.equals(getCurrentPlayer());
    }
    public Player nextTurn(){
        return model.nextPlayer();
    }
    public int getNumberOfPlayer(){
        return model.getPlayers().size();
    }
    public void setNumberOfPlayer(int num){model.setNumberOfPlayer(num);}
    public void removePlayer(Player player){model.removePlayer(player);}
    public GameStatus getGameStatus(){return model.getStatus();}
    public int[] getAllPoints(){
        int[] points = new int[model.getPlayers().size()];
        for(Player p : getAllPlayer()){
            points[p.getColorPlayer()] = p.getCurrentPoints();
        }
        return points;
    }


//---------------------------------TABLE AND INIT SECTION
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

            //random first player
            randomFirstPlayer();

            //run TurnZero
            turnZero();

            model.setStatus(GameStatus.RUNNING);
            return true;
        }
        else return false;
    }
    private void randomFirstPlayer(){
        Queue<Player> players = model.getPlayers();
        Player temp;
        int first = random.nextInt(4);

        for(int i=0; i<first; i++){
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
    }
    private void turnZero() {
        for(Player player : getAllPlayer()){
            player.drawStarting(model.getGameDrawableDeck());
            player.drawGoals(model.getGameDrawableDeck());
            player.drawResourcesfromDeck(model.getGameDrawableDeck());
            player.drawResourcesfromDeck(model.getGameDrawableDeck());
            player.drawGoldfromDeck(model.getGameDrawableDeck());
        }
    }


//---------------------------------ADD CARD SECTION
    public void addCard(ResourceCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip){
        if(flip){
            cardToAdd.flip();
        }
        getCurrentPlayer().addToBoard(cardToAdd,cardOnBoard,cornerToAttach);
    }
    public void addCard(GoldCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach, Boolean flip){
        if(flip){
            cardToAdd.flip();
        }
        getCurrentPlayer().addToBoard(cardToAdd,cardOnBoard,cornerToAttach);
    }
    public void addStartingCard(Boolean flip){
        if(flip){
            getCurrentPlayer().getStartingCard().flip();
        }
        getCurrentPlayer().addStarting();
    }
    public void choosePlayerGoal(int choice){
        getCurrentPlayer().chooseGoal(choice);
    }


//---------------------------------DRAW SECTION
    public void drawResourceFromDeck(){
        if(!model.getGameDrawableDeck().getDecks().get("resource").isEmpty()){
            getCurrentPlayer().drawResourcesfromDeck(model.getGameDrawableDeck());
        }
        else {
            model.setStatus(GameStatus.LAST_TURN);
        }

    }
    public void drawGoldFromDeck(){
        if(!model.getGameDrawableDeck().getDecks().get("gold").isEmpty()) {
            getCurrentPlayer().drawGoldfromDeck(model.getGameDrawableDeck());
        }
        else {
            model.setStatus(GameStatus.LAST_TURN);
        }
    }
    public void drawFromBoard(int position){
        if(
           (position <= 2 && model.getGameBoardDeck().getResourceCards()[position-1]!=null)||
           (position >  2 && model.getGameBoardDeck().getGoldCards()[position-3] !=null)
        ){
            getCurrentPlayer().drawfromBoard(position,model.getGameBoardDeck(),model.getGameDrawableDeck());
        }
    }


//---------------------------------END SECTION
    private void checkPoints20Points(){
        for(Player player: getAllPlayer()){
            if(player.getCurrentPoints() >= 20 && getCurrentPlayer() == model.getFirstPlayer()){
                model.setStatus(GameStatus.LAST_TURN);
            }
        }
    }
    public void checkWinner(){
        model.checkWinner();
    }

//---------------------------------GET SECTION TO DISPLAY IT
    public Game getGame(){return model;}
}
