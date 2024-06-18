package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCountCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectivePatternCard;

import java.util.Map;

/**
 * The type Card factory.
 */
public class CardFactory {
    /**
     * Create playing card.
     *
     * @param type       the type
     * @param id         the id
     * @param attributes the attributes
     * @return the playing card
     */
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

    /**
     * Create objective card.
     *
     * @param type       the type
     * @param id         the id
     * @param attributes the attributes
     * @return the objective card
     */
    public static ObjectiveCard createObjectiveCard(String type, int id, Map<String, JsonElement> attributes) {
        if (type.equalsIgnoreCase("Objective_Count")) {
            return createObjectiveCountCard(id, attributes);
        } else if (type.equalsIgnoreCase("Objective_Pattern")) {
            return createObjectivePatternCard(id, attributes);
        } else {
            throw new IllegalArgumentException("Invalid card type: " + type);
        }
    }

    /**
     * Create resource card.
     *
     * @param id         the id
     * @param attributes the attributes
     * @return the resource card
     */
    private static ResourceCard createResourceCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to ResourcesCard and create the card
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
        for (int i = 0; i < corners.length; i++) {
            Corner c;
            if (cornersNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersNames[i]));
                corners[i] = c;
            }
        }

        return new ResourceCard(id, seed, corners, points);
    }

    /**
     * Create gold card.
     *
     * @param id         the id
     * @param attributes the attributes
     * @return the gold card
     */
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
        for (int i = 0; i < corners.length; i++) {
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

        return new GoldCard(id, seed, corners, points, pointCondition, placeCondition);
    }

    /**
     * Create starting card.
     *
     * @param id         the id
     * @param attributes the attributes
     * @return the starting card
     */
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
        for (int i = 0; i < cornersFront.length; i++) {
            Corner c;
            if (cornersFrontNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersFrontNames[i]));
                cornersFront[i] = c;
            }
        }

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
        for (int i = 0; i < cornersBack.length; i++) {
            Corner c;
            if (cornersBackNames[i] != null) {
                c = new Corner(i, Seed.getByName(cornersBackNames[i]));
                cornersBack[i] = c;
            }
        }

        return new StartingCard(id, center, cornersFront, cornersBack);
    }

    /**
     * Create objective count card objective count card.
     *
     * @param id         the id
     * @param attributes the attributes
     * @return the objective count card
     */
    private static ObjectiveCountCard createObjectiveCountCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to ObjectiveCard and create the card
        Gson gson = new Gson();
        JsonArray jArray;

        int points = attributes.get("points").getAsInt();

        jArray = attributes.get("types").getAsJsonArray();
        int[] types = gson.fromJson(jArray, int[].class);

        return new ObjectiveCountCard(id, points, types);
    }

    /**
     * Create objective pattern card objective pattern card.
     *
     * @param id         the id
     * @param attributes the attributes
     * @return the objective pattern card
     */
    private static ObjectivePatternCard createObjectivePatternCard(int id, Map<String, JsonElement> attributes) {
        // Extract attributes specific to ObjectiveCard and create the card
        JsonArray jArray;

        int points = attributes.get("points").getAsInt();

        jArray = attributes.get("pattern").getAsJsonArray();
        String[][] patternMatrixString = new String[jArray.size()][];
        for (int i = 0; i < jArray.size(); i++) {
            JsonArray jArray1 = jArray.get(i).getAsJsonArray();
            if (jArray1.isJsonNull()) {
                patternMatrixString[i] = null;
            }
            else {
                String[] patternString = new String[jArray1.size()];
                for (int j = 0; j < jArray1.size(); j++) {
                    JsonElement element1 = jArray1.get(j);
                    if (element1.isJsonNull()) {
                        patternString[j] = null;
                    }
                    else {
                        patternString[j] = element1.getAsString();
                    }
                }
                patternMatrixString[i] = patternString;
            }
        }
        Seed[][] pattern = new Seed[patternMatrixString.length][];
        for (int i = 0; i < pattern.length; i++) {
            if (patternMatrixString[i] != null) {
                pattern[i] = new Seed[patternMatrixString[i].length];

                for (int j = 0; j < pattern[i].length; j++) {
                    Seed s;
                    if (patternMatrixString[i][j] != null) {
                        s = Seed.getByName(patternMatrixString[i][j]);
                        pattern[i][j] = s;
                    }
                    else {
                        pattern[i][j] = null;
                    }
                }
            }
            else {
                pattern[i] = null;
            }
        }

        return new ObjectivePatternCard(id, points, pattern);
    }
}
