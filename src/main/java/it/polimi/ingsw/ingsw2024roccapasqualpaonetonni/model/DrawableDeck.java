package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.DeckEmptyException;

import java.util.*;

public class DrawableDeck {
    private final Map<String, Queue<Card>> decks;

    public DrawableDeck(Map<String, Queue<Card>> decks){
        this.decks = decks;
    }
    public Map<String, Queue<Card>>  getDecks(){
        return decks;
    }
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
