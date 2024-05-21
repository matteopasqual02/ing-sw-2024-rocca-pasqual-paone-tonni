package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.Card;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
        String path = null;
        Player player = gameImmutable.getPlayers().stream()
                .filter(p -> p.getNickname().equals(nickname))
                .findFirst()
                .orElse(null);

        //setting hand

        int cardId = player.getHand().get(0).getIdCard();
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_gold_front/00" + String.valueOf(cardId) +".png";
        }
        else
        {
            path = "/images/Codex_image/CODEX_cards_gold_front/0" + String.valueOf(cardId) +".png";
        }
        myHandImage1.setImage(new Image(getClass().getResource(path).toString()));

        cardId = player.getHand().get(1).getIdCard();
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_gold_front/00" + String.valueOf(cardId) +".png";
        }
        else
        {
            path = "/images/Codex_image/CODEX_cards_gold_front/0" + String.valueOf(cardId) +".png";
        }
        myHandImage2.setImage(new Image(getClass().getResource(path).toString()));

        cardId = player.getHand().get(2).getIdCard();
        if(cardId<10){
            path = "/images/Codex_image/CODEX_cards_gold_front/00" + String.valueOf(cardId) +".png";
        }
        else
        {
            path = "/images/Codex_image/CODEX_cards_gold_front/0" + String.valueOf(cardId) +".png";
        }
        myHandImage3.setImage(new Image(getClass().getResource(path).toString()));

        //setting secret objectives
/*
        cardId = player.getObjectiveBeforeChoice()[0].getIdCard();
        path = "/images/Codex_image/CODEX_cards_gold_front/0" + String.valueOf(cardId) +".png";
        secretObjectiveImage1.setImage(new Image(getClass().getResource(path).toString()));
        if(cardId<100){
            path = "images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        else
        {
            path = "images/gameImages/images/CODEX_cards_gold_front/" + cardId +".png";
        }
        secretObjectiveImage1.setImage(new Image(getClass().getClassLoader().getResource(path).toString()));

        cardId = player.getObjectiveBeforeChoice()[1].getIdCard();
        path = "/images/Codex_image/CODEX_cards_gold_front/0" + String.valueOf(cardId) +".png";
        if(cardId<100){
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/" + cardId +".png";
        }
        secretObjectiveImage2.setImage(new Image(getClass().getResource(path).toString()));

        //setting common objectives

        cardId = gameImmutable.getBoardDeck().getCommonObjective(0).getIdCard();
        if(cardId<100){
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/" + cardId +".png";
        }
        commonObjectiveImage1.setImage(new Image(getClass().getResource(path).toString()));

        cardId = gameImmutable.getBoardDeck().getCommonObjective(1).getIdCard();
        if(cardId<100){
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/" + cardId +".png";
        }
        commonObjectiveImage2.setImage(new Image(getClass().getResource(path).toString()));

        //setting resource cards

        cardId = gameImmutable.getDrawableDeck().getDecks().get("resources").peek().getIdCard();
        if(cardId<10){
            path = "/images/gameImages/images/CODEX_cards_gold_front/00" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        resourceCard1.setImage(new Image(getClass().getResource(path).toString()));

        cardId = gameImmutable.getBoardDeck().getCard(0).getIdCard();
        if(cardId<10){
            path = "/images/gameImages/images/CODEX_cards_gold_front/00" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        resourceCard2.setImage(new Image(getClass().getResource(path).toString()));

        cardId = gameImmutable.getBoardDeck().getCard(1).getIdCard();
        if(cardId<10){
            path = "/images/gameImages/images/CODEX_cards_gold_front/00" + cardId +".png";
        }
        else
        {
            path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        }
        resourceCard3.setImage(new Image(getClass().getResource(path).toString()));

        //setting gold cards

        cardId = gameImmutable.getDrawableDeck().getDecks().get("gold").peek().getIdCard();
        path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        goldCard1.setImage(new Image(getClass().getResource(path).toString()));

        cardId = gameImmutable.getBoardDeck().getCard(2).getIdCard();
        path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        goldCard2.setImage(new Image(getClass().getResource(path).toString()));

        cardId = gameImmutable.getBoardDeck().getCard(3).getIdCard();
        path = "/images/gameImages/images/CODEX_cards_gold_front/0" + cardId +".png";
        goldCard3.setImage(new Image(getClass().getResource(path).toString()));*/
    }
}
