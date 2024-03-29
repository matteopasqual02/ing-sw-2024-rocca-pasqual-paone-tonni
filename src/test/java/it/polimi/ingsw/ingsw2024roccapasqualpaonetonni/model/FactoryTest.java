package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception.InvalidPlaceException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.EMPTY;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Seed.GREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryTest {
    @Test
    void FactoryTest() throws InvalidPlaceException {

        HashMap<String,Object> attributes =new HashMap<String, Object>();
        String[] cs={"green","empty","blue","red"};
        ResourceCard card;

        attributes.put("color","green");
        attributes.put("points",1);
        attributes.put("corners",cs);

        card= (ResourceCard) CardFactory.createPlayingCard("Resources",1,attributes);
        assertEquals(GREEN,card.getSeed());
        assertEquals(1,card.getPoints());

    }
}
