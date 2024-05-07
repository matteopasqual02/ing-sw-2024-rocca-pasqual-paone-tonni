package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.fusesource.jansi.Ansi.ansi;

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
        int color = player.getColorPlayer();
        Seed seedPlayer = Seed.getById(color);

        if(seedPlayer!=null){
            stringBuilder.append("NICKNAME:\t").append(
                    ansi().fg(seedPlayer.getByAnsi()).bg(Ansi.Color.DEFAULT).a(player.getNickname()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\t");
            stringBuilder.append("COLOR:\t").append(
                    ansi().fg(seedPlayer.getByAnsi()).bg(Ansi.Color.DEFAULT).a(seedPlayer).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\t");
            stringBuilder.append("POINTS:\t").append(
                    ansi().fg(seedPlayer.getByAnsi()).bg(Ansi.Color.DEFAULT).a(player.getCurrentPoints()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\n");
        }

        stringBuilder.append("AVAILABLE SEED:\t");
        int[] countSeed = player.getCountSeed();
        for (int i = 0; i < 7; i++) {
            Seed seed= Seed.getById(i);
            if(seed!=null){
                stringBuilder.append(Seed.getById(i)).append("  ").append(
                        ansi().fg(seed.getByAnsi()).bg(Ansi.Color.DEFAULT).a(countSeed[i]).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
                ).append("   ");
            }
        }

        stringBuilder.append("\nMY HAND:\n");
        player.getHand().forEach(playingCard -> stringBuilder.append(playingCard.toString()).append("\n"));
        stringBuilder.append("\nMY OBJECTIVE:\n");
        stringBuilder.append(player.getObjectiveBeforeChoice()[0].toString()).append("\n");
        stringBuilder.append(player.getObjectiveBeforeChoice()[1].toString()).append("\n");
        stringBuilder.append("\nMY STARTING:\n");
        stringBuilder.append(player.getStartingCard().toString());

        stringBuilder.append("\nMY BOARD:\n");
        stringBuilder.append(player.getBoard().toString());

        stringBuilder.append("\nCOMMON BOARD:\n");
        for (int i=1;i<5;i++){
            stringBuilder.append(boardDeck.getCard(i).toString());
        }
        ResourceCard resourceCard = (ResourceCard) drawableDeck.getDecks().get("resources").peek();
        if (resourceCard!=null){
            stringBuilder.append(resourceCard.toString(true)).append("\n");
        }
        GoldCard goldCard = (GoldCard) drawableDeck.getDecks().get("gold").peek();
        if (goldCard!=null){
            stringBuilder.append(goldCard.toString(true)).append("\n");
        }

        stringBuilder.append("\nCOMMON OBJECTIVE:\n");
        for (int i=0;i<2;i++){
            stringBuilder.append(boardDeck.getCommonObjective(i).toString()).append("\n");
        }


        return stringBuilder.toString();
    }

}
