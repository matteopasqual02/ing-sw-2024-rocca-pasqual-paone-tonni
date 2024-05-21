package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.PlayingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.NoCardException;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils.createCardsFromJson;
import static org.junit.jupiter.api.Assertions.*;

class BoardDeckTest {
    private String path = "src/main/java/it/polimi/ingsw/ingsw2024roccapasqualpaonetonni/utils/DataBase";

    private Game model = new Game(1);

    @Test
    void setCardCardsTest() throws IOException {
        Map<String, List<Card>> cardMap;
        {
            try {
                cardMap = createCardsFromJson(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        BoardDeck boardDeck1 = new BoardDeck(model);
        BoardDeck boardDeck2;
        ResourceCard[] RCards = new ResourceCard[2];
        GoldCard[] GCards = new GoldCard[2];
        ObjectiveCard[] OCards = new ObjectiveCard[2];
        RCards[0] = (ResourceCard) cardMap.get("resources").get(0);
        RCards[1] = (ResourceCard) cardMap.get("resources").get(1);
        GCards[0] = (GoldCard) cardMap.get("gold").get(0);
        GCards[1] = (GoldCard) cardMap.get("gold").get(1);
        OCards[0] = (ObjectiveCard) cardMap.get("objective").get(0);
        OCards[1] = (ObjectiveCard) cardMap.get("objective").get(1);

        assertNull(boardDeck1.getCommonObjective(3));
        assertNull(boardDeck1.getCard(5));

        assertTrue(boardDeck1.isEmpty());
        boardDeck2 = new BoardDeck(RCards, GCards, OCards, model);
        assertFalse(boardDeck2.isEmpty());
        assertTrue(boardDeck1.isEmpty());

        boardDeck1.setResourceCards(RCards[0], 0);
        boardDeck1.setResourceCards(RCards[1], 1);
        boardDeck1.setGoldCards(GCards[0], 0);
        boardDeck1.setGoldCards(GCards[1], 1);
        boardDeck1.setObjectiveCards(OCards[0], 0);
        boardDeck1.setObjectiveCards(OCards[1], 1);

        assertEquals(boardDeck1.getCard(1), boardDeck2.getCard(1));
        assertEquals(boardDeck1.getCard(2), boardDeck2.getCard(2));
        assertEquals(boardDeck1.getCard(3), boardDeck2.getCard(3));
        assertEquals(boardDeck1.getCard(4), boardDeck2.getCard(4));
        assertEquals(boardDeck1.getCommonObjective(0), boardDeck2.getCommonObjective(0));
        assertEquals(boardDeck1.getCommonObjective(1), boardDeck2.getCommonObjective(1));
    }

    @Test
    void getDrawableDeckTest() throws IOException {
        BoardDeck boardDeck1 = new BoardDeck(model);
        assertNull(boardDeck1.getDrawableDeck());
    }

    @Test
    void drawTest() throws IOException {
        Map<String, List<Card>> cardsMap = null;
        while (cardsMap == null) {
            try {
                cardsMap = JSONUtils.createCardsFromJson(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        BoardDeck boardDeck = new BoardDeck(model);

        Boolean exceptionThrown = false;
        try {
            boardDeck.draw(1);
        }
        catch (NoCardException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        exceptionThrown = false;
        try {
            boardDeck.draw(2);
        }
        catch (NoCardException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        exceptionThrown = false;
        try {
            boardDeck.draw(3);
        }
        catch (NoCardException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        exceptionThrown = false;
        try {
            boardDeck.draw(4);
        }
        catch (NoCardException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        exceptionThrown = false;

        //set the BoardDeck
        try {
            boardDeck.setObjectiveCards(decks.drawFirstObjective(), 0);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }
        try {
            boardDeck.setObjectiveCards(decks.drawFirstObjective(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }


        try {
            boardDeck.setResourceCards(decks.drawFirstResource(), 0);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }

        try {
            boardDeck.setResourceCards(decks.drawFirstResource(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }

        try {
            boardDeck.setGoldCards(decks.drawFirstGold(), 0);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }

        try {
            boardDeck.setGoldCards(decks.drawFirstGold(), 1);
        } catch (DeckEmptyException e) {
            e.printStackTrace();
        }


        model.setGameDrawableDeck(decks);
        model.setGameBoardDeck(boardDeck);

        // empty resources deck
        boolean again = true;
        while (again) {
            try {
                decks.drawFirstResource();
            }
            catch (Exception e) {
                again = false;
            }
        }

        PlayingCard card = null;
        try {
            card = boardDeck.draw(1);
            System.out.println(card);
            card = boardDeck.draw(1);
            System.out.println(card);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (card != null) {
            // check that the second card is a gold
            assertTrue(card.getIdCard() >= 40 && card.getIdCard() < 80);
        }

        // empty gold deck
        again = true;
        while (again) {
            try {
                decks.drawFirstGold();
            }
            catch (Exception e) {
                again = false;
            }
        }

        card = null;
        try {
            card = boardDeck.draw(3);
            System.out.println(card);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        exceptionThrown = false;
        try {
            card = boardDeck.draw(3);
        }
        catch (NoCardException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}