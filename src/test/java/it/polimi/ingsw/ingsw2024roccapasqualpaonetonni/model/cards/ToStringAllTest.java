package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.GameController;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

public class ToStringAllTest {
    @Test
    void TestAll() throws RemoteException {
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

        System.out.println("\n\nObjective:\n");
        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("objective")) {
            System.out.println(card.toString());
        }

        System.out.println("Central Board\n");

        PlayingCard playingCard1 = gameController.getGame().getGameBoardDeck().getCard(1);
        System.out.println(playingCard1.toString());

        PlayingCard playingCard2 = gameController.getGame().getGameBoardDeck().getCard(2);
        System.out.println(playingCard2.toString());

        PlayingCard playingCard3 = gameController.getGame().getGameBoardDeck().getCard(3);
        System.out.println(playingCard3.toString());

        PlayingCard playingCard4 = gameController.getGame().getGameBoardDeck().getCard(4);
        System.out.println(playingCard4.toString());

        ObjectiveCard objectiveCard1 = gameController.getGame().getGameBoardDeck().getCommonObjective(1);
        System.out.println(objectiveCard1.toString());
        ObjectiveCard objectiveCard2 = gameController.getGame().getGameBoardDeck().getCommonObjective(0);
        System.out.println(objectiveCard2.toString());

    }

    @Test
    void TestMultipleAll() throws RemoteException {
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
            PlayingCard playingCard = (PlayingCard) card;
            System.out.println(playingCard.toString());
            playingCard.flip();
            System.out.println(playingCard.toString());
            System.out.println(playingCard.toString(true));
            for (int i=0;i<3;i++){
                System.out.println(playingCard.toString(true,i));
            }
            for (int i=0;i<3;i++){
                System.out.println(playingCard.toString(false,i));
            }
        }

        System.out.println("\n\nStarting:\n");
        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("starting")) {
            PlayingCard playingCard = (PlayingCard) card;
            System.out.println(playingCard.toString());
            playingCard.flip();
            System.out.println(playingCard.toString());
            System.out.println(playingCard.toString(true));
            for (int i=0;i<3;i++){
                System.out.println(playingCard.toString(true,i));
            }
            for (int i=0;i<3;i++){
                System.out.println(playingCard.toString(false,i));
            }
        }

        System.out.println("\n\nObjective:\n");
        for(Card card: gameController.getGame().getGameDrawableDeck().getDecks().get("objective")) {
            System.out.println(card.toString());
        }

        System.out.println("Central Board\n");

        PlayingCard playingCard1 = gameController.getGame().getGameBoardDeck().getCard(1);
        for(int i=1;i<3;i++){
            System.out.println(playingCard1.toString(false,i));
        }

        PlayingCard playingCard2 = gameController.getGame().getGameBoardDeck().getCard(2);
        System.out.println(playingCard2.toString(false));


        PlayingCard playingCard3 = gameController.getGame().getGameBoardDeck().getCard(3);
        for(int i=1;i<3;i++){
            System.out.println(playingCard3.toString(false,i));
        }

        PlayingCard playingCard4 = gameController.getGame().getGameBoardDeck().getCard(4);
        System.out.println(playingCard4.toString(false));

        System.out.println("Drawable deck\n");
        PlayingCard playingCard6 = (PlayingCard) gameController.getGame().getGameDrawableDeck().getDecks().get("resources").peek();
        assert playingCard6 != null;
        System.out.println(playingCard6.toString(true));
        for(int i=1;i<3;i++){
            System.out.println(playingCard6.toString(true,i));
        }

        PlayingCard playingCard5 = (PlayingCard) gameController.getGame().getGameDrawableDeck().getDecks().get("gold").peek();
        assert playingCard5 != null;
        System.out.println(playingCard5.toString(true));
        for(int i=1;i<3;i++){
            System.out.println(playingCard5.toString(true,i));
        }


        ObjectiveCard objectiveCard1 = gameController.getGame().getGameBoardDeck().getCommonObjective(1);
        for(int i=1;i<3;i++){
            System.out.println(objectiveCard1.toString(i));
        }
        ObjectiveCard objectiveCard2 = gameController.getGame().getGameBoardDeck().getCommonObjective(0);
        for(int i=1;i<3;i++){
            System.out.println(objectiveCard2.toString(i));
        }

    }

}
