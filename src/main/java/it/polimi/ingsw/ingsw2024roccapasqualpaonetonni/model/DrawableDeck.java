package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;

public class DrawableDeck {
    private Map<String, Queue<Card>> decks;

    public DrawableDeck(Map<String, Queue<Card>> decks){
        this.decks = decks;
    }
    public ResourceCard drawFirstResource()
    {
        Queue<Card> cards = decks.get("resources");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (ResourceCard) cards.poll();
        } else {
            // If the resource queue is null or empty, return null
            return null;
        }
    }
    public GoldCard drawFirstGold()
    {
        Queue<Card> cards = decks.get("gold");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (GoldCard) cards.poll();
        } else {
            // If the resource queue is null or empty, return null
            return null;
        }
    }
    public ObjectiveCard drawFirstObjective()
    {
        Queue<Card> cards = decks.get("objective");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (ObjectiveCard) cards.poll();
        } else {
            // If the resource queue is null or empty, return null
            return null;
        }
    }
    public StartingCard drawFirstStarting()
    {
        Queue<Card> cards = decks.get("starting");
        // Check if the resource queue is not null and not empty
        if (cards != null && !cards.isEmpty()) {
            // Draw and return the first resource card
            return (StartingCard) cards.poll();
        } else {
            // If the resource queue is null or empty, return null
            return null;
        }
    }
}
