package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.GameAlreadyFullException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.PlayerAlreadyInException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.View;
import javafx.scene.control.skin.TableHeaderRow;

import java.util.*;

public class GameController implements Runnable{
    private Game model;
    private View view;
    private final Random random = new Random();

    public GameController() {
        model = new Game();
        new Thread(this).start();
    }

    public void run() {
        while (!Thread.interrupted()) {

        }
    }

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

    public Boolean createTable(){
        if(model.arePlayerReady()){
            //create dalla factory
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

    public void addCard(ResourceCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach){
        Player currentplayer = model.getCurrentPlayer();
        getCurrentPlayer().addToBoard(cardToAdd,cardOnBoard,cornerToAttach);
    }
    public void addCard(GoldCard cardToAdd, PlayingCard cardOnBoard, int cornerToAttach){
        Player currentplayer = model.getCurrentPlayer();
        getCurrentPlayer().addToBoard(cardToAdd,cardOnBoard,cornerToAttach);
    }

    public void drawObjectCard(){
        getCurrentPlayer().drawGoals(model.getGameDrawableDeck());
    }

    public void drawStartingCard(){
        getCurrentPlayer().drawStarting(model.getGameDrawableDeck());
    }

    public void drawResourcefromdeck(){
        getCurrentPlayer().drawResourcesfromDeck(model.getGameDrawableDeck());
    }

    public void drawGoldfromdeck(){
        getCurrentPlayer().drawGoldfromDeck(model.getGameDrawableDeck());
    }

    public void drawfromBoard(int position){
        getCurrentPlayer().drawfromBoard(position,model.getGameBoardDeck(),model.getGameDrawableDeck());
    }

    private void checkPoints20Points(){
        if(getCurrentPlayer().getCurrentPoints() >= 20){
            model.setStatus(GameStatus.LAST_TURN);
        }
    }

}
