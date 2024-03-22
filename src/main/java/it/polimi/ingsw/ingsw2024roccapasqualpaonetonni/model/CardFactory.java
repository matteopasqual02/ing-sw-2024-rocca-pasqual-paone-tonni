package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Map;
public class CardFactory {
    public static PlayingCard createPlayingCard(String type, int id, Map<String, Object> attributes) {
        if (type.equalsIgnoreCase("Resources")) {
            return createResourceCard(id, attributes);
        } else if (type.equalsIgnoreCase("Gold")) {
            return createGoldCard(id, attributes);
        } else if (type.equalsIgnoreCase("Starting")) {
            return createStartingCard(id, attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
    }

    public static ObjectiveCard createObjectiveCard(String type, int id, Map<String, Object> attributes) {
        if (type.equalsIgnoreCase("Objective")) {
            return createObjectiveCard(id, attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
    }

    private static ResourceCard createResourceCard(int id, Map<String, Object> attributes) {
        // Extract attributes specific to ResourcesCard and create the card
        String color = (String) attributes.get("color");
        Seed seed = Seed.getByName(color);
        int points = (int) attributes.get("points");
        String[] cornersNames = (String[]) attributes.get("corners");
        Corner[] corners = {null, null, null, null};
        for (int i = 0; i < 4; i++) {
            Corner c;
            if (cornersNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersNames[i]));
                corners[i] = c;
            }
        }
        return new ResourceCard(id, seed, corners, points);
    }

    private static GoldCard createGoldCard(int id, Map<String, Object> attributes) {
        // Extract attributes specific to GoldCard and create the card
        String color = (String) attributes.get("color");
        Seed seed = Seed.getByName(color);
        int points = (int) attributes.get("points");
        String[] cornersNames = (String[]) attributes.get("corners");
        Corner[] corners = {null, null, null, null};
        for (int i = 0; i < 4; i++) {
            Corner c;
            if (cornersNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersNames[i]));
                corners[i] = c;
            }
        }
        String pointCondition = (String) attributes.get("point_condition");
        int[] placeCondition = (int[]) attributes.get("place_condition");

        // Create the GoldCard object with the extracted attributes
        return new GoldCard(id, seed, corners, points, pointCondition, placeCondition);
    }

    private static StartingCard createStartingCard(int id, Map<String, Object> attributes) {
        // Extract attributes specific to StartingCard and create the card
        Boolean[] center = (Boolean[]) attributes.get("center");
        String[] cornersFrontNames = (String[]) attributes.get("corners_front");
        Corner[] cornersFront = {null, null, null, null};
        String[] cornersBackNames = (String[]) attributes.get("corners_back");
        Corner[] cornersBack = {null, null, null, null};
        for (int i = 0; i < 4; i++) {
            Corner c;
            if (cornersFrontNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersFrontNames[i]));
                cornersFront[i] = c;
            }
            if (cornersBackNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersBackNames[i]));
                cornersBack[i] = c;
            }
        }
        return new StartingCard(id, center, cornersFront, cornersBack);
    }

    private static ObjectiveCard createObjectiveCard(int id, Map<String, Object> attributes) {
        // Extract attributes specific to ObjectiveCard and create the card
        int points = (int) attributes.get("points");
        boolean isCount = ((int) attributes.get("is_count")) == 1;
        String typeString = (String) attributes.get("type");
        Seed type = Seed.getByName(typeString);
        String[] patternCardsString = (String[]) attributes.get("pattern_cards");
        Seed[] patternCards = {null, null};
        for (int i = 0; i < 2; i++) {
            Seed s;
            if (patternCardsString[i] != null) {
                s = Seed.getByName(patternCardsString[i]);
                patternCards[i] = s;
            }
        }
        String shape = (String) attributes.get("shape");

        return new ObjectiveCard(id, points, isCount, type, patternCards, shape);
    }
}
