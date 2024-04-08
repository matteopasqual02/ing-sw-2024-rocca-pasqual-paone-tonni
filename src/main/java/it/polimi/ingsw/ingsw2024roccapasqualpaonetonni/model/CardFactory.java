package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.Gson;

public class CardFactory {
    public static PlayingCard createPlayingCard(String type, int id, Map<String, JsonElement> attributes) {
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

    public static ObjectiveCard createObjectiveCard(String type, int id, Map<String, JsonElement> attributes) {
        if (type.equalsIgnoreCase("Objective")) {
            return createObjectiveCard(id, attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
    }

    private static ResourceCard createResourceCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to ResourcesCard and create the card
        Gson gson = new Gson();
        JsonArray jArray;
        String color = attributes.get("color").getAsString();
        Seed seed = Seed.getByName(color);
        int points = attributes.get("points").getAsInt();
        jArray = attributes.get("corners").getAsJsonArray();
        String[] cornersNames = new String[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            JsonElement element = jArray.get(i);
            if (element.isJsonNull()) {
                cornersNames[i] = null; // Represent null value in String[] array as null
            } else {
                cornersNames[i] = element.getAsString(); // Convert non-null values to strings
            }
        }
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

    private static GoldCard createGoldCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to GoldCard and create the card
        Gson gson = new Gson();
        JsonArray jArray;
        String color = attributes.get("color").getAsString();
        Seed seed = Seed.getByName(color);
        int points = attributes.get("points").getAsInt();
        jArray = attributes.get("corners").getAsJsonArray();
        String[] cornersNames = new String[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            JsonElement element = jArray.get(i);
            if (element.isJsonNull()) {
                cornersNames[i] = null;
            } else {
                cornersNames[i] = element.getAsString();
            }
        }
        Corner[] corners = {null, null, null, null};
        for (int i = 0; i < 4; i++) {
            Corner c;
            if (cornersNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersNames[i]));
                corners[i] = c;
            }
        }

        String pointCondition;
        JsonElement pointConditionElement = attributes.get("point_condition");
        if (pointConditionElement != null && !pointConditionElement.isJsonNull()) {
            pointCondition = pointConditionElement.getAsString();
        } else {
            pointCondition = null; // or any default value you prefer
        }

        jArray = attributes.get("place_condition").getAsJsonArray();
        int[] placeCondition = gson.fromJson(jArray, int[].class);

        // Create the GoldCard object with the extracted attributes
        return new GoldCard(id, seed, corners, points, pointCondition, placeCondition);
    }

    private static StartingCard createStartingCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to StartingCard and create the card
        Gson gson = new Gson();
        JsonArray jArray;
        jArray = attributes.get("center").getAsJsonArray();
        int[] centerInt = gson.fromJson(jArray, int[].class);
        Boolean[] center = new Boolean[centerInt.length];
        for (int i = 0; i < centerInt.length; i++) {
            center[i] = centerInt[i] != 0;
        }
        jArray = attributes.get("corners_front").getAsJsonArray();
        String[] cornersFrontNames = new String[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            JsonElement element = jArray.get(i);
            if (element.isJsonNull()) {
                cornersFrontNames[i] = null;
            } else {
                cornersFrontNames[i] = element.getAsString();
            }
        }
        Corner[] cornersFront = {null, null, null, null};
        jArray = attributes.get("corners_back").getAsJsonArray();
        String[] cornersBackNames = new String[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            JsonElement element = jArray.get(i);
            if (element.isJsonNull()) {
                cornersBackNames[i] = null;
            } else {
                cornersBackNames[i] = element.getAsString();
            }
        }
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

    private static ObjectiveCard createObjectiveCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to ObjectiveCard and create the card
        Gson gson = new Gson();
        JsonArray jArray;
        int points = attributes.get("points").getAsInt();
        boolean isCount = ((int) attributes.get("is_count").getAsInt()) == 1;
        Seed type = null;
        jArray = attributes.get("pattern_cards").getAsJsonArray();
        String[] patternCardsString = new String[jArray.size()];
        for (int i = 0; i < jArray.size(); i++) {
            JsonElement element = jArray.get(i);
            if (element.isJsonNull()) {
                patternCardsString[i] = null;
            } else {
                patternCardsString[i] = element.getAsString();
            }
        }
        Seed[] patternCards = {null, null};
        for (int i = 0; i < 2; i++) {
            Seed s;
            if (patternCardsString[i] != null) {
                s = Seed.getByName(patternCardsString[i]);
                patternCards[i] = s;
            }
        }
        String shape;
        JsonElement shapeElement = attributes.get("shape");
        if (shapeElement != null && !shapeElement.isJsonNull()) {
            shape = shapeElement.getAsString();
        }
        else {
            shape = null;
        }

        return new ObjectiveCard(id, points, isCount, type, patternCards, shape);
    }
}
