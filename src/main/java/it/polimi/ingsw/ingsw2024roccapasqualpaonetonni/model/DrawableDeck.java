package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.GoldCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.ResourceCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.StartingCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;

import java.io.Serializable;
import java.util.Map;
import java.util.Queue;

/**
 * The type Drawable deck.
 */
public class DrawableDeck implements Serializable {
    /**
     * The Decks.
     */
    private final Map<String, Queue<Card>> decks;

    /**
     * Instantiates a new Drawable deck.
     *
     * @param decks the decks
     */
    public DrawableDeck(Map<String, Queue<Card>> decks){
        this.decks = decks;
    }

    /**
     * Get decks map.
     *
     * @return the map
     */
    public Map<String, Queue<Card>>  getDecks(){
        return decks;
    }

    /**
     * Draw first resource.
     *
     * @return the resource card
     * @throws DeckEmptyException the deck empty exception
     */
    public ResourceCard drawFirstResource() throws DeckEmptyException {
        Queue<Card> cards = decks.get("resources");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (ResourceCard) cards.poll();
        } else {
            // If the resource queue is null or empty, throw exception
            throw new DeckEmptyException("The deck is empty");
        }
    }

    /**
     * Draw first gold .
     *
     * @return the gold card
     * @throws DeckEmptyException the deck empty exception
     */
    public GoldCard drawFirstGold() throws DeckEmptyException {
        Queue<Card> cards = decks.get("gold");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (GoldCard) cards.poll();
        } else {
            // If the resource queue is null or empty, throw exception
            throw new DeckEmptyException("The deck is empty");
        }
    }

    /**
     * Draw first objective .
     *
     * @return the objective card
     * @throws DeckEmptyException the deck empty exception
     */
    public ObjectiveCard drawFirstObjective() throws DeckEmptyException {
        Queue<Card> cards = decks.get("objective");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (ObjectiveCard) cards.poll();
        } else {
            // If the resource queue is null or empty, throw exception
            throw new DeckEmptyException("The deck is empty");
        }
    }

    /**
     * Draw first starting.
     *
     * @return the starting card
     * @throws DeckEmptyException the deck empty exception
     */
    public StartingCard drawFirstStarting() throws DeckEmptyException {
        Queue<Card> cards = decks.get("starting");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (StartingCard) cards.poll();
        } else {
            // If the resource queue is null or empty, throw exception
            throw new DeckEmptyException("The deck is empty");
        }
    }
}
