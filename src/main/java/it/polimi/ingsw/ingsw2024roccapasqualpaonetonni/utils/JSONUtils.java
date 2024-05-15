package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.CardFactory;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The type Json utils.
 */
public class JSONUtils {

    /**
     * Create cards from json map.
     *
     * @param filePath the file path
     * @return the map
     * @throws IOException the io exception
     */
// function that takes the json object into a map, with key the type of card and value the list of cards
    public static Map<String, List<Card>> createCardsFromJson(String filePath) throws IOException {
        // Create a map to store lists of cards for each card type
        Map<String, List<Card>> cardMap = new HashMap<>();
        JsonObject jsonObject = parseTxtToJson(filePath);

        // Iterate over each entry in the JSON object (each representing a card type)
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            // Extract the type (card type) from the key name
            String type = entry.getKey();
            // Get the JSON object containing card data for the current card type
            JsonObject cardTypeJson = entry.getValue().getAsJsonObject();

            // If the card type is not already present in the map, add an empty list for it
            if (!cardMap.containsKey(type)) {
                cardMap.put(type, new ArrayList<>());
            }

            // Iterate over each entry in the card type JSON object (each representing a card)
            for (Map.Entry<String, JsonElement> cardEntry : cardTypeJson.entrySet()) {
                // Extract the ID of the card from the key name
                int id = Integer.parseInt(cardEntry.getKey());
                // Get the JSON object containing attributes of the current card
                JsonObject cardData = cardEntry.getValue().getAsJsonObject();

                // Create a map to store attributes of the card
                Map<String, JsonElement> attributes = new HashMap<>();
                // Iterate over each attribute of the card and add it to the attributes map
                for (Map.Entry<String, JsonElement> attributeEntry : cardData.entrySet()) {
                    attributes.put(attributeEntry.getKey(), attributeEntry.getValue());
                }

                // Create a card using the CardFactory based on the extracted type, id, and attributes
                Card card;
                if (type.equalsIgnoreCase("objective_pattern") || type.equalsIgnoreCase("objective_count")) {
                    card = CardFactory.createObjectiveCard(type, id, attributes);
                }
                else {
                    card = CardFactory.createPlayingCard(type, id, attributes);
                }
                // If a card is successfully created, add it to the list for the corresponding card type
                if (card != null) {
                    cardMap.get(type).add(card);
                }
            }
        }


        // put together the different types of objectives in a single objectives list
        List<Card> objectives = new ArrayList<>(cardMap.get("objective_pattern"));
        cardMap.remove("objective_pattern");
        objectives.addAll(cardMap.get("objective_count"));
        cardMap.remove("objective_count");
        cardMap.put("objective", objectives);

        // Return the map containing lists of cards for each card type
        return cardMap;
    }

    /**
     * Parse txt to json json object.
     *
     * @param filePath the file path
     * @return the json object
     * @throws IOException the io exception
     */
// function that transforms the json in the String form into a json object
    public static JsonObject parseTxtToJson(String filePath) throws IOException {
        // Read the content of the TXT file
        String txtContent = readTXTFile(filePath);

        // Parse the TXT content to JSON using Gson
        Gson gson = new Gson();
        return gson.fromJson(txtContent, JsonObject.class);
    }

    /**
     * Read txt file string.
     *
     * @param filePath the file path
     * @return the string
     * @throws IOException the io exception
     */
// function that reads the txt file and turns it into a String
    private static String readTXTFile(String filePath) throws IOException {
        // Read the contents of the TXT file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
