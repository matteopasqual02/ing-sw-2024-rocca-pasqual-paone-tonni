package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.View;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javafx.scene.control.skin.TableHeaderRow;

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
    }
    public Queue<Player> getAllPlayer(){
        return model.getPlayers();
    }
    public Player getCurrentPlayer(){
        return model.getCurrentPlayer();
    }
    public Player nextTurn(){
        return model.nextPlayer();
    }
    public int getNumberOfPlayer(){
        return model.getPlayers().size();
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
            model.setStatus(GameStatus.RUNNING);
            return true;
        }
        else return false;
    }
    public void randomFirstPlayer(){
        Queue<Player> players = model.getPlayers();
        Player temp;
        int first = random.nextInt(4);

        for(int i=0; i<first; i++){
            model.nextPlayer();
        }

        model.setFirstPlayer(model.getCurrentPlayer());
    }
    private static Queue<Card> createShuffledDeck(List<Card> cards) {
        // Shuffle the list of cards
        Collections.shuffle(cards);

        // Create a new deck as a Queue
        Queue<Card> deck = new LinkedList<>(cards);

        return deck;
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


//---------------------------------DRAW SECTION
    public void drawObjectCard(){
        getCurrentPlayer().drawGoals(model.getGameDrawableDeck());
    }
    public void drawStartingCard(){
        getCurrentPlayer().drawStarting(model.getGameDrawableDeck());
    }
    public void drawResourceFromDeck(){
        getCurrentPlayer().drawResourcesfromDeck(model.getGameDrawableDeck());
    }
    public void drawGoldFromDeck(){
        getCurrentPlayer().drawGoldfromDeck(model.getGameDrawableDeck());
    }
    public void drawfromBoard(int position){
        getCurrentPlayer().drawfromBoard(position,model.getGameBoardDeck(),model.getGameDrawableDeck());
    }


//---------------------------------END SECTION
    private void checkPoints20Points(){
        if(getCurrentPlayer().getCurrentPoints() >= 20){
            model.setStatus(GameStatus.LAST_TURN);
        }
    }
    public void checkWinner(){
        model.checkWinner();
    }
}
