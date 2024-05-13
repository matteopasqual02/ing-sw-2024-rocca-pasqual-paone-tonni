package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat.Chat;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import static org.fusesource.jansi.Ansi.ansi;

public class GameImmutable implements Serializable {
    private final int gameId;
    private final int maxNumberOfPlayers;
    private final Queue<Player> players;
    private final Queue<Player> winners;
    private final GameStatus status;
    private BoardDeck boardDeck;
    private DrawableDeck drawableDeck;
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

    public synchronized int getGameId() {
        return gameId;
    }
    public synchronized int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }
    public synchronized Queue<Player> getPlayers() {
        return players;
    }
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
    public synchronized Queue<Player> getWinners() {
        return winners;
    }
    public synchronized GameStatus getStatus() {
        return status;
    }
    public synchronized Chat getChat() {
        return chat;
    }
    public synchronized BoardDeck getBoardDeck() {
        return boardDeck;
    }
    public synchronized DrawableDeck getDrawableDeck() {
        return drawableDeck;
    }

    public synchronized int[] getAllPoints(){
        int[] points = new int[players.size()];
        for(Player p : players){
            points[p.getColorPlayer()-1] = p.getCurrentPoints();
        }
        return points;
    }
    public synchronized void setDrawableDeck(DrawableDeck d) {
        this.drawableDeck=d;
    }
    public synchronized void setBoardDeck(BoardDeck b) {
        this.boardDeck = b;
    }

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
        for(int k=0;k<3;k++) {
            for (int i = 1; i < 3; i++) {
                stringBuilder.append(boardDeck.getCard(i).toString(false,k)).append("\t");
            }
            ResourceCard resourceCard = (ResourceCard) drawableDeck.getDecks().get("resources").peek();
            if (resourceCard != null) {
                stringBuilder.append(resourceCard.toString(true,k)).append("\t");
            }
            stringBuilder.append("\t|\t");
            for (int i = 3; i < 5; i++) {
                stringBuilder.append(boardDeck.getCard(i).toString(false,k)).append("\t");
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

        stringBuilder.append("\nOTHERS PLAYER\n");
        for(Player playerI : players ){
            if(!playerI.equals(player)){
                Ansi.Color colorI= Seed.getById(playerI.getColorPlayer()-1).getByAnsi();
                stringBuilder.append("NICKNAME:\t").append(
                        ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(playerI.getNickname()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
                ).append("\t");
                stringBuilder.append("COLOR:\t").append(
                        ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(Seed.getById(playerI.getColorPlayer()-1)).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
                ).append("\t");
                stringBuilder.append("POINTS:\t").append(
                        ansi().fg(colorI).bg(Ansi.Color.DEFAULT).a(player.getCurrentPoints()).fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT)
                ).append("\n");
            }
        }

        return stringBuilder.toString();
    }


}
