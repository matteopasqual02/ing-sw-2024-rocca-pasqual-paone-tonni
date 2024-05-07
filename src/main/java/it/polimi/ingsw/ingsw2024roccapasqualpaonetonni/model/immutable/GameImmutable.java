package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GameImmutable implements Serializable {
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

    public String toString(String nickname){
        StringBuilder stringBuilder = new StringBuilder();

        Player player = players.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        if (player==null) return null;

        stringBuilder.append("NICKNAME:\t").append(player.getNickname()).append("\n");
        int color = player.getColorPlayer();
        Seed seedPlayer = Seed.getById(color);
        stringBuilder.append("COLOR:\t").append(seedPlayer).append("\n");
        stringBuilder.append("POINTS:\t").append(player.getCurrentPoints()).append("\n");

        stringBuilder.append("AVAILABLE SEED:\n");
        int[] countSeed = player.getCountSeed();
        for (int i = 0; i < 7; i++) {
            stringBuilder.append(Seed.getById(i)).append("  ").append(countSeed[i]).append("   ");
        }

        stringBuilder.append("\nMY HAND:\n\n");
        player.getHand().forEach(playingCard -> stringBuilder.append(playingCard.toString()).append("\n"));
        stringBuilder.append(player.getObjectiveBeforeChoice()[0].toString());
        stringBuilder.append(player.getObjectiveBeforeChoice()[1].toString());
        stringBuilder.append(player.getStartingCard().toString());

        stringBuilder.append("MY BOARD:\n");
        stringBuilder.append(player.getBoard().toString());

        return stringBuilder.toString();
    }

}
