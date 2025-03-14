package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.*;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectiveCountCard;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective.ObjectivePatternCard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils.createCardsFromJson;
import static org.junit.jupiter.api.Assertions.*;

class JSONUtilsTest {

    @Test
    void createDeckListsResources() throws IOException {
        String path = "DataBase";
        Map<String, List<Card>> cardMap = createCardsFromJson(path);

        HashMap<String, JsonElement> attributes =new HashMap<>();

        // id
        int id = 1;
        // seed
        String color = "red";
        attributes.put("color", new JsonPrimitive(color));
        // points
        int points = 0;
        attributes.put("points", new JsonPrimitive(points));
        // corners
        String[] corners={"red", "empty", null, "red"};
        JsonArray jArray = new JsonArray();
        for (String s: corners) {
            if (s == null) {
                jArray.add(JsonNull.INSTANCE);
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners", jArray);

        ResourceCard card = (ResourceCard) CardFactory.createPlayingCard("Resources", id, attributes);

        boolean flag = false;
        List<Card> cardList = cardMap.get("resources");
        for (int i = 0; i < cardList.size() && !flag; i++) {
            ResourceCard r = (ResourceCard) cardList.get(i);
            flag = checkCardsEqual(r, card);
        }
        assertTrue(flag);
    }

    @ Test
    void createDeckListsGold() throws IOException {
        String path = "DataBase";
        Map<String, List<Card>> cardMap = createCardsFromJson(path);

        HashMap<String, JsonElement> attributes =new HashMap<>();

        // id
        int id = 41;
        // seed
        String color = "red";
        attributes.put("color", new JsonPrimitive(color));
        // points
        int points = 1;
        attributes.put("points", new JsonPrimitive(points));
        // corners
        String[] corners = {null, "empty", "feather", "empty"};
        JsonArray jArray = new JsonArray();
        for (String s: corners) {
            if (s == null) {
                jArray.add(JsonNull.INSTANCE);
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners", jArray);
        // point condition
        String pointCondition = "feather";
        attributes.put("point_condition", new JsonPrimitive(pointCondition));
        // place condition
        int[] placeConditions = {0, 1, 2, 0};
        jArray = new JsonArray();
        for (int i: placeConditions) {
            jArray.add(new JsonPrimitive(i));
        }
        attributes.put("place_condition", jArray);

        GoldCard card = (GoldCard) CardFactory.createPlayingCard("Gold", id, attributes);

        boolean flag = false;
        List<Card> cardList = cardMap.get("gold");
        for (int i = 0; i < cardList.size() && !flag; i++) {
            GoldCard r = (GoldCard) cardList.get(i);
            flag = checkCardsEqual(r, card);
        }
        assertTrue(flag);
    }

    @ Test
    void createDeckListsStarting() throws IOException {
        String path = "DataBase";
        Map<String, List<Card>> cardMap = createCardsFromJson(path);

        HashMap<String, JsonElement> attributes =new HashMap<>();

        // id
        int id = 81;
        // center
        int[] center = {0, 0, 0, 1};
        JsonArray jArray = new JsonArray();
        for (int i: center) {
            jArray.add(new JsonPrimitive(i));
        }
        attributes.put("center", jArray);
        // corners front
        String[] cornersF = {"empty", "green", "empty", "purple"};
        jArray = new JsonArray();
        for (String s: cornersF) {
            if (s == null) {
                jArray.add(JsonNull.INSTANCE);
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners_front", jArray);
        // corners back
        String[] cornersB = {"red", "green", "blue", "purple"};
        jArray = new JsonArray();
        for (String s: cornersB) {
            if (s == null) {
                jArray.add(JsonNull.INSTANCE);
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners_back", jArray);

        StartingCard card = (StartingCard) CardFactory.createPlayingCard("Starting", id, attributes);

        boolean flag = false;
        List<Card> cardList = cardMap.get("starting");
        for (int i = 0; i < cardList.size() && !flag; i++) {
            StartingCard r = (StartingCard) cardList.get(i);
            flag = checkCardsEqual(r, card);
        }
        assertTrue(flag);
    }

    @ Test
    void createDeckListsObjective() throws IOException {
        String path = "DataBase";
        Map<String, List<Card>> cardMap = createCardsFromJson(path);

        HashMap<String, JsonElement> attributes =new HashMap<>();

        // id
        int id = 87;
        // points
        int points = 2;
        attributes.put("points", new JsonPrimitive(points));
        // pattern cards
        Seed[][] pattern = {{null, null, Seed.RED},
                {null, Seed.RED, null},
                {Seed.RED, null, null}};
        JsonArray jArray = new JsonArray();
        JsonArray jArray1;
        for (int i = 0; i < pattern.length; i++) {
            jArray1 = new JsonArray();
            for (int j = 0; j < pattern[i].length; j++) {
                if (pattern[i][j] == null) {
                    jArray1.add(JsonNull.INSTANCE);
                }
                else {
                    jArray1.add(new JsonPrimitive(pattern[i][j].getName()));
                }
            }
            jArray.add(jArray1);
        }
        attributes.put("pattern", jArray);

        ObjectiveCard card = (ObjectiveCard) CardFactory.createObjectiveCard("Objective_Pattern", id, attributes);

        boolean flag = false;
        List<Card> cardList = cardMap.get("objective");
        for (int i = 0; i < cardList.size() && !flag; i++) {
            ObjectiveCard r = (ObjectiveCard) cardList.get(i);
            flag = checkCardsEqual(r, card);
        }
        assertTrue(flag);
    }


    private boolean checkCardsEqual(Card c1, Card c2) {
        if (c1 instanceof ResourceCard && c2 instanceof ResourceCard) {
            ResourceCard card1 = (ResourceCard) c1;
            ResourceCard card2 = (ResourceCard) c2;
            return card1.getIdCard() == card2.getIdCard()
                    && card1.getSeed().equals(card2.getSeed())
                    && ((card1.getCorner(1) == null && card2.getCorner(1) == null)
                        || ((card1.getCorner(1) != null && card2.getCorner(1) != null)
                        && card1.getCorner(1).getSeed().equals(card2.getCorner(1).getSeed())))
                    && ((card1.getCorner(2) == null && card2.getCorner(2) == null)
                        || ((card1.getCorner(2) != null && card2.getCorner(2) != null)
                        && card1.getCorner(2).getSeed().equals(card2.getCorner(2).getSeed())))
                    && ((card1.getCorner(3) == null && card2.getCorner(3) == null)
                        || ((card1.getCorner(3) != null && card2.getCorner(3) != null)
                        && card1.getCorner(3).getSeed().equals(card2.getCorner(3).getSeed())))
                    && ((card1.getCorner(4) == null && card2.getCorner(4) == null)
                        || (card1.getCorner(4).getSeed().equals(card2.getCorner(4).getSeed())))
                    && card1.getPoints() == card2.getPoints();
        }
        else if (c1 instanceof GoldCard && c2 instanceof GoldCard) {
            GoldCard card1 = (GoldCard) c1;
            GoldCard card2 = (GoldCard) c2;
            return card1.getIdCard() == card2.getIdCard()
                    && card1.getSeed().equals(card2.getSeed())
                    && ((card1.getCorner(1) == null && card2.getCorner(1) == null)
                        || ((card1.getCorner(1) != null && card2.getCorner(1) != null)
                        && card1.getCorner(1).getSeed().equals(card2.getCorner(1).getSeed())))
                    && ((card1.getCorner(2) == null && card2.getCorner(2) == null)
                        || ((card1.getCorner(2) != null && card2.getCorner(2) != null)
                        && card1.getCorner(2).getSeed().equals(card2.getCorner(2).getSeed())))
                    && ((card1.getCorner(3) == null && card2.getCorner(3) == null)
                        || ((card1.getCorner(3) != null && card2.getCorner(3) != null)
                        && card1.getCorner(3).getSeed().equals(card2.getCorner(3).getSeed())))
                    && ((card1.getCorner(4) == null && card2.getCorner(4) == null)
                        || (card1.getCorner(4).getSeed().equals(card2.getCorner(4).getSeed())))
                        && card1.getPoints() == card2.getPoints()
                    && ((card1.getPointCondition() == null && card2.getPointCondition() == null)
                        || ((card1.getPointCondition() != null && card2.getPointCondition() != null)
                        && card1.getPointCondition().equals(card2.getPointCondition())))
                    && card1.getPlaceCondition()[0] == card2.getPlaceCondition()[0]
                    && card1.getPlaceCondition()[1] == card2.getPlaceCondition()[1]
                    && card1.getPlaceCondition()[2] == card2.getPlaceCondition()[2]
                    && card1.getPlaceCondition()[3] == card2.getPlaceCondition()[3];

        }
        else if (c1 instanceof StartingCard && c2 instanceof StartingCard) {
            StartingCard card1 = (StartingCard) c1;
            StartingCard card2 = (StartingCard) c2;
            return card1.getCenter()[0] == card2.getCenter()[0]
                    && card1.getCenter()[1] == card2.getCenter()[1]
                    && card1.getCenter()[2] == card2.getCenter()[2]
                    && card1.getCenter()[3] == card2.getCenter()[3]
                    && ((card1.getCorner(1) == null && card2.getCorner(1) == null)
                        || ((card1.getCorner(1) != null && card2.getCorner(1) != null)
                        && card1.getCorner(1).getSeed().equals(card2.getCorner(1).getSeed())))
                    && ((card1.getCorner(2) == null && card2.getCorner(2) == null)
                        || ((card1.getCorner(2) != null && card2.getCorner(2) != null)
                        && card1.getCorner(2).getSeed().equals(card2.getCorner(2).getSeed())))
                    && ((card1.getCorner(3) == null && card2.getCorner(3) == null)
                        || ((card1.getCorner(3) != null && card2.getCorner(3) != null)
                        && card1.getCorner(3).getSeed().equals(card2.getCorner(3).getSeed())))
                    && ((card1.getCorner(4) == null && card2.getCorner(4) == null)
                        || ((card1.getCorner(4) != null && card2.getCorner(4) != null)
                        && card1.getCorner(4).getSeed().equals(card2.getCorner(4).getSeed())));

        }
        else if (c1 instanceof ObjectiveCountCard && c2 instanceof ObjectiveCountCard) {
            ObjectiveCountCard card1 = (ObjectiveCountCard) c1;
            ObjectiveCountCard card2 = (ObjectiveCountCard) c2;
            boolean flag = true;
            for (int i = 0; i < card1.getCountTypes().length && flag; i++) {
                if (card1.getCountTypes()[i] != card2.getCountTypes()[i]) {
                    flag = false;
                }
            }
            return card1.getPoints() == card2.getPoints()
                    && flag;
        }
        else if (c1 instanceof ObjectivePatternCard && c2 instanceof ObjectivePatternCard) {
            ObjectivePatternCard card1 = (ObjectivePatternCard) c1;
            ObjectivePatternCard card2 = (ObjectivePatternCard) c2;
            boolean flag = true;
            for (int i = 0; i < card1.getPattern().length && flag; i++) {
                for (int j = 0; j < card2.getPattern()[i].length && flag; j++) {
                    if (card1.getPattern()[i][j] != card2.getPattern()[i][j]) {
                        flag = false;
                    }
                }
            }
            return card1.getPoints() == card2.getPoints()
                    && flag;
        }
        else {
            return false;
        }
    }
}