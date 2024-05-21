package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.ExecutorService;

public class GameController extends GenericController{
    @FXML
    private ImageView resourceCard1;
    @FXML
    private ImageView resourceCard2;
    @FXML
    private ImageView resourceCard3;
    @FXML
    private ImageView goldCard1;
    @FXML
    private ImageView goldCard2;
    @FXML
    private ImageView goldCard3;
    @FXML
    private ImageView myHandImage1;
    @FXML
    private ImageView myHandImage2;
    @FXML
    private ImageView myHandImage3;
    @FXML
    private ImageView commonObjectiveImage1;
    @FXML
    private ImageView commonObjectiveImage2;
    @FXML
    private ImageView secretObjectiveImage1;
    @FXML
    private ImageView secretObjectiveImage2;
    private ExecutorService executor;
    private Client client;
    private GUIApplication application;

    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }
    public void setScene(GameImmutable gameImmutable, String nickname){
        int cardId;
        Player player = gameImmutable.getPlayers().stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        //setting hand
        if(player==null)return;

        cardId = player.getHand().get(0).getIdCard();
        myHandImage1.setImage(new Image(createPath(cardId)));
        cardId = player.getHand().get(1).getIdCard();
        myHandImage2.setImage(new Image(createPath(cardId)));
        cardId = player.getHand().get(2).getIdCard();
        myHandImage3.setImage(new Image(createPath(cardId)));

        //setting secret objectives
        cardId = player.getObjectiveBeforeChoice()[0].getIdCard();
        secretObjectiveImage1.setImage(new Image(createPath(cardId)));
        cardId = player.getObjectiveBeforeChoice()[1].getIdCard();
        secretObjectiveImage2.setImage(new Image(createPath(cardId)));

        //setting common objectives

        cardId = gameImmutable.getBoardDeck().getCommonObjective(0).getIdCard();
        commonObjectiveImage1.setImage(new Image(createPath(cardId)));

        cardId = gameImmutable.getBoardDeck().getCommonObjective(1).getIdCard();
        commonObjectiveImage2.setImage(new Image(createPath(cardId)));

        //setting resource cards
        if(!gameImmutable.getDrawableDeck().getDecks().get("resources").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
        }
        resourceCard1.setImage(new Image(createBackPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        resourceCard2.setImage(new Image(createPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        resourceCard3.setImage(new Image(createPath(cardId)));

        //setting gold cards

        if(!gameImmutable.getDrawableDeck().getDecks().get("gold").isEmpty()){
            cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
        }
        goldCard1.setImage(new Image(createBackPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        goldCard2.setImage(new Image(createPath(cardId)));
        cardId = gameImmutable.getBoardDeck().getCard(4).getIdCard();
        goldCard3.setImage(new Image(createPath(cardId)));
    }

    private String createBackPath(int cardId) {
        String path;
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_back/00" + cardId +".png";
        }
        else if(cardId<100)
        {
            path = "/images/Codex_image/CODEX_cards_back/0" + cardId +".png";
        }
        else {
            path = "/images/Codex_image/CODEX_cards_back/" + cardId +".png";
        }
        return String.valueOf(getClass().getResource(path));
    }

    private String createPath(int cardId) {
        String path;
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_front/00" + cardId +".png";
        }
        else if(cardId<100)
        {
            path = "/images/Codex_image/CODEX_cards_front/0" + cardId +".png";
        }
        else {
            path = "/images/Codex_image/CODEX_cards_front/" + cardId +".png";
        }
        return String.valueOf(getClass().getResource(path));
    }
}
