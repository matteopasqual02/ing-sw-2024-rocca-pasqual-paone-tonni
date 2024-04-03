package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.CardFactory;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.ResourceCard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.JSONUtils.createCardsFromJson;
import static org.junit.jupiter.api.Assertions.*;

class JSONUtilsTest {

    @Test
    void createDeckLists() throws IOException {
        String path = "src/main/java/it/polimi/ingsw/ingsw2024roccapasqualpaonetonni/utils/DataBase";
        Map<String, List<Card>> cardMap = createCardsFromJson(path);

        HashMap<String,Object> attributes =new HashMap<String, Object>();
        String[] corners={"red", "empty", null, "red"};
        int i = 1;
        attributes.put("color","red");
        attributes.put("points",0);
        attributes.put("corners",corners);
        ResourceCard card = (ResourceCard) CardFactory.createPlayingCard("Resources",i,attributes);

        assertTrue(cardMap.get("resources").contains(card));
    }
}