package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Map;
public class CardFactory {
    public static PlayingCard createPlayingCard(String type, Map<String, Object> attributes) {
        if (type.equalsIgnoreCase("Resources")) {
            return createResourceCard(attributes);
        } else if (type.equalsIgnoreCase("Gold")) {
            return createGoldCard(attributes);
        } else if (type.equalsIgnoreCase("Starting")) {
            return createStartingCard(attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
        return null;
    }

    public static ObjectiveCard createObjectiveCard(String type, Map<String, Object> attributes) {
        if (type.equalsIgnoreCase("Objective")) {
            return createObjectiveCard(attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
    }

    private static ResourceCard createResourcesCard(int id, Map<String, Object> attributes) {
        // Extract attributes specific to ResourcesCard and create the card
        String color = (String) attributes.get("color");
        int points = (int) attributes.get("points");
        String[] corners = (String[]) attributes.get("corners");
        return new ResourceCard(id, color, points, corners);
    }

    private static GoldCard createGoldCard(Map<String, Object> attributes) {
        // Extract attributes specific to GoldCard and create the card
        // Example: int goldValue = (int) attributes.get("goldValue");
        return new GoldCard(/* pass attributes here */);
    }

    private static StartingCard createStartingCard(Map<String, Object> attributes) {
        // Extract attributes specific to StartingCard and create the card
        // Example: String someAttribute = (String) attributes.get("someAttribute");
        return new StartingCard(/* pass attributes here */);
    }

    private static ObjectiveCard createObjectiveCard(Map<String, Object> attributes) {
        // Extract attributes specific to ObjectiveCard and create the card
        // Example: int objectiveId = (int) attributes.get("objectiveId");
        return new ObjectiveCard(/* pass attributes here */);
    }
}
