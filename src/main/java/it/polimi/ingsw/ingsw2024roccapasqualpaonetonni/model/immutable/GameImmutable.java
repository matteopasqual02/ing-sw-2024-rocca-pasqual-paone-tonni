package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GameImmutable {
    private final int gameId;
    private final int maxNumberOfPlayers;
    private final Queue<Player> players;
    private final Queue<Player> winners;
    private final GameStatus status;
    private final BoardDeck boardDeck;
    private final DrawableDeck drawableDeck;
    private final Chat chat;

    public GameImmutable(){
        gameId=0;
        maxNumberOfPlayers=0;
        players=null;
        winners=null;
        status=null;
        boardDeck=null;
        drawableDeck=null;
        chat=null;
    }
    public GameImmutable(Game modelToCopy){
        gameId = modelToCopy.getGameId();
        maxNumberOfPlayers = modelToCopy.getMaxNumberOfPlayer();
        players = new LinkedList<>(modelToCopy.getPlayers());
        winners = new LinkedList<>(modelToCopy.getWinners());
        status = modelToCopy.getGameStatus();
        chat = modelToCopy.getChat();
        boardDeck = modelToCopy.getGameBoardDeck();
        drawableDeck = modelToCopy.getGameDrawableDeck();
    }

    public int getGameId() {
        return gameId;
    }
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
    public Queue<Player> getPlayers() {
        return players;
    }
    public Queue<Player> getWinners() {
        return winners;
    }
    public GameStatus getStatus() {
        return status;
    }
    public Chat getChat() {
        return chat;
    }
    public BoardDeck getBoardDeck() {
        return boardDeck;
    }
    public DrawableDeck getDrawableDeck() {
        return drawableDeck;
    }

    public int[] getAllPoints(){
        int[] points = new int[players.size()];
        for(Player p : players){
            points[p.getColorPlayer()-1] = p.getCurrentPoints();
        }
        return points;
    }

}
