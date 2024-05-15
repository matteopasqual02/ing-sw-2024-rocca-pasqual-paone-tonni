package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.*;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * The type Game immutable.
 */
public class GameImmutable implements Serializable {
    /**
     * The Game id.
     */
    private final int gameId;
    /**
     * The Max number of players.
     */
    private final int maxNumberOfPlayers;
    /**
     * The Players.
     */
    private final Queue<Player> players;
    /**
     * The Winners.
     */
    private final Queue<Player> winners;
    /**
     * The Status.
     */
    private final GameStatus status;
    /**
     * The Board deck.
     */
    private BoardDeck boardDeck;
    /**
     * The Drawable deck.
     */
    private DrawableDeck drawableDeck;
    /**
     * The Chat.
     */
    private final Chat chat;

    /**
     * Instantiates a new Game immutable.
     */
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

    /**
     * Instantiates a new Game immutable.
     *
     * @param modelToCopy the model to copy
     */
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

    /**
     * Gets game id.
     *
     * @return the game id
     */
    public synchronized int getGameId() {
        return gameId;
    }

    /**
     * Gets max number of players.
     *
     * @return the max number of players
     */
    public synchronized int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public synchronized Queue<Player> getPlayers() {
        return players;
    }

    /**
     * Refresh player.
     *
     * @param player the player
     */
    public synchronized void refreshPlayer(Player player){
        Player modify=null;
        for (Player p: players){
            if(p.getNickname().equals(player.getNickname())){
                modify=p;
            }
        }
        players.remove(modify);
        players.add(player);
    }

    /**
     * Gets winners.
     *
     * @return the winners
     */
    public synchronized Queue<Player> getWinners() {
        return winners;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public synchronized GameStatus getStatus() {
        return status;
    }

    /**
     * Gets chat.
     *
     * @return the chat
     */
    public synchronized Chat getChat() {
        return chat;
    }

    /**
     * Gets board deck.
     *
     * @return the board deck
     */
    public synchronized BoardDeck getBoardDeck() {
        return boardDeck;
    }

    /**
     * Gets drawable deck.
     *
     * @return the drawable deck
     */
    public synchronized DrawableDeck getDrawableDeck() {
        return drawableDeck;
    }

    /**
     * Get all points int [ ].
     *
     * @return the int [ ]
     */
    public synchronized int[] getAllPoints(){
        int[] points = new int[players.size()];
        for(Player p : players){
            points[p.getColorPlayer()-1] = p.getCurrentPoints();
        }
        return points;
    }

    /**
     * Sets drawable deck.
     *
     * @param d the d
     */
    public synchronized void setDrawableDeck(DrawableDeck d) {
        this.drawableDeck=d;
    }

    /**
     * Sets board deck.
     *
     * @param b the b
     */
    public synchronized void setBoardDeck(BoardDeck b) {
        this.boardDeck = b;
    }

    /**
     * To string string.
     *
     * @param nickname the nickname
     * @return the string
     */
    public synchronized String toString(String nickname){
        StringBuilder stringBuilder = new StringBuilder();

        Player player = players.stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        if (player==null) return null;
        int color = player.getColorPlayer()-1;
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
        stringBuilder.append("\tHAND 1\t\t\tHAND 2\t\t\tHAND 3\t\t\t\tPRIVATE GOAL");
        if(player.getBoard().getBoardMatrix()[player.getBoard().getDim_x()/2][player.getBoard().getDim_y()/2]==null){
            stringBuilder.append("\t\t\t\t\t\t\t\tSTARTING\n");
        }
        else {
            stringBuilder.append("\n");
        }
        for(int i=0;i<3;i++){
            int finalI = i;
            player.getHand().forEach(playingCard -> stringBuilder.append(playingCard.toString(false, finalI)).append("\t"));
            stringBuilder.append("\t|\t");

            if(player.getGoal()==null){
                stringBuilder.append(player.getObjectiveBeforeChoice()[0].toString(finalI)).append("\t");
                stringBuilder.append(player.getObjectiveBeforeChoice()[1].toString(finalI)).append("\t");
            }else {
                stringBuilder.append(player.getGoal().toString(finalI)).append("\t");
            }

            if(player.getBoard().getBoardMatrix()[player.getBoard().getDim_x()/2][player.getBoard().getDim_y()/2]==null){
                stringBuilder.append("\t|\t").append(player.getStartingCard().toString(false,finalI)).append("\n");
            }
            else {
                stringBuilder.append("\n");
            }
        }


        stringBuilder.append("\nMY BOARD:\n");
        stringBuilder.append(player.getBoard().toString());

        stringBuilder.append("\nCOMMON DECKS:\n");
        stringBuilder.append("\tBOARD 1\t\t\tBOARD 2\t\t\tBOARD 3\t\t\tBOARD 4\t\t\t\t\tRESOURCES\t\tGOLD\t\t\t\tCOMMON OBJECTIVES\n");
        for(int k=0;k<3;k++) {
            for (int i = 1; i < 5; i++) {
                stringBuilder.append(boardDeck.getCard(i).toString(false,k)).append("\t");
            }
            stringBuilder.append("\t|\t");
            ResourceCard resourceCard = (ResourceCard) drawableDeck.getDecks().get("resources").peek();
            if (resourceCard != null) {
                stringBuilder.append(resourceCard.toString(true,k)).append("\t");
            }
            GoldCard goldCard = (GoldCard) drawableDeck.getDecks().get("gold").peek();
            if (goldCard != null) {
                stringBuilder.append(goldCard.toString(true,k)).append("\t");
            }
            stringBuilder.append("\t|\t");
            for (int i=0;i<2;i++){
                stringBuilder.append(boardDeck.getCommonObjective(i).toString(k)).append("\t");
            }
            stringBuilder.append("\n");
        }

        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort(Comparator.comparingInt(Player::getCurrentPoints).reversed());

        stringBuilder.append("\nPOINTS:\n");
        for (Player playerI : sortedPlayers) {
            Ansi.Color colorI = Seed.getById(playerI.getColorPlayer() - 1).getByAnsi();
            stringBuilder.append("NICKNAME:\t").append(
                    ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(playerI.getNickname()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\t");
            stringBuilder.append("POINTS:\t").append(
                    ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(playerI.getCurrentPoints()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\t");
            stringBuilder.append("COLOR:\t").append(
                    ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(Seed.getById(playerI.getColorPlayer() - 1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
            ).append("\n");
        }
        return stringBuilder.toString();
    }

}
