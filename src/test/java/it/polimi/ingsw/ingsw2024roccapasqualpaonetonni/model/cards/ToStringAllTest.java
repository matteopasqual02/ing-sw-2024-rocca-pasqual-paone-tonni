package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

public class ToStringAllTest {
    @Test
    void TestAllDrawable() throws RemoteException {
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();

        System.out.println("\n\nResource:\n");

        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("resources")) {
            System.out.println(card.toString());
        }

        System.out.println("\n\nGold:\n");
        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("gold")) {
            System.out.println(card.toString());
        }

        System.out.println("\n\nStarting:\n");
        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("starting")) {
            System.out.println(card.toString());
        }
    }

    @Test
    void TestAllBoard() throws RemoteException {
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();

        PlayingCard playingCard1 = gameController.getGame().getGameBoardDeck().getCard(1);
        System.out.println(playingCard1.toString());

        PlayingCard playingCard2 = gameController.getGame().getGameBoardDeck().getCard(2);
        System.out.println(playingCard2.toString());

        PlayingCard playingCard3 = gameController.getGame().getGameBoardDeck().getCard(3);
        System.out.println(playingCard3.toString());

        PlayingCard playingCard4 = gameController.getGame().getGameBoardDeck().getCard(4);
        System.out.println(playingCard4.toString());

    }

    @Test
    void TestAllPlayer() throws RemoteException {
        GameController gameController = new GameController(0);

        gameController.setMaxNumberOfPlayer(4);
        gameController.addPlayer("a");
        gameController.addPlayer("b");
        gameController.addPlayer("c");
        gameController.addPlayer("d");

        gameController.createTable();

        for(Player player: gameController.getGame().getPlayers()) {
            System.out.println("Player: ");
            System.out.println(player.getNickname());
            System.out.println(player.getColorPlayer());

            for(PlayingCard playingCard: player.getHand()) {
                System.out.println(playingCard.toString());
            }
        }
    }

}
