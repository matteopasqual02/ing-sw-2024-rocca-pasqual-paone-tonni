package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.EMPTY;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTest {
    @Test
    void FactoryResourceTest() throws InvalidPlaceException {

        HashMap<String, JsonElement> attributes =new HashMap<>();
        ResourceCard card;

        String color = "green";
        attributes.put("color", new JsonPrimitive(color));
        int points = 1;
        attributes.put("points", new JsonPrimitive(points));
        String[] cs={"green","empty","blue","red"};
        JsonArray jArray = new JsonArray();
        for (String s: cs) {
            if (s == null) {
                jArray.add(new JsonPrimitive((String) null));
            }
            else {
                jArray.add(new JsonPrimitive(s));
            }
        }
        attributes.put("corners", jArray);

        card = (ResourceCard) CardFactory.createPlayingCard("Resources",1, attributes);
        assertEquals(GREEN,card.getSeed());
        assertEquals(1,card.getPoints());

    }
}
