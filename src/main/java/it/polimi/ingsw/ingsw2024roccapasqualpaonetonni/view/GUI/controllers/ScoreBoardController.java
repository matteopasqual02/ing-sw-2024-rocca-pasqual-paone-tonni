package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class ScoreBoardController extends GenericController{
    @FXML
    private ImageView image0;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;
    @FXML
    private ImageView image10;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image14;
    @FXML
    private ImageView image15;
    @FXML
    private ImageView image16;
    @FXML
    private ImageView image17;
    @FXML
    private ImageView image18;
    @FXML
    private ImageView image19;
    @FXML
    private ImageView image20;
    @FXML
    private ImageView image21;
    @FXML
    private ImageView image22;
    @FXML
    private ImageView image23;
    @FXML
    private ImageView image24;
    @FXML
    private ImageView image25;
    @FXML
    private ImageView image26;
    @FXML
    private ImageView image27;
    @FXML
    private ImageView image28;
    @FXML
    private ImageView image29;

    @FXML
    private AnchorPane anchor;
    private ExecutorService executor;
    private Client client;
    private GUIApplication application;
    private HashMap<Integer,ImageView> positionMap;
            /*=Map.of(
            0,image0,
            1,image1,
            2,image2,
            3,image3,
            4,image4,
            5,image5,
            6,image6,
            7,image7,
            8,image8,
            9,image9,
            10,image10,
            11,image11,
            12,image12,
            13,image13,
            14,image14,
            15,image15,
            16,image16,
            17,image17,
            18,image18,
            19,image19,
            20,image20,
            21,image21,
            22,image22,
            23,image23,
            24,image24,
            25,image25,
            26,image26,
            27,image27,
            28,image28,
            29,image29
            );*/ //it holds the number and the variable so we dont have to call each variable separatly
    private HashMap<Integer,Integer> playerPosition;
    private int x=0;
    private int y=0;

    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
        positionMap = new HashMap<>();
        playerPosition = new HashMap<>();
        positionMap.put(0,image0);
        positionMap.put(1, image1);
        positionMap.put(2, image2);
        positionMap.put(3, image3);
        positionMap.put(4, image4);
        positionMap.put(5, image5);
        positionMap.put(6, image6);
        positionMap.put(7, image7);
        positionMap.put(8, image8);
        positionMap.put(9, image9);
        positionMap.put(10, image10);
        positionMap.put(11, image11);
        positionMap.put(12, image12);
        positionMap.put(13, image13);
        positionMap.put(14, image14);
        positionMap.put(15, image15);
        positionMap.put(16, image16);
        positionMap.put(17, image17);
        positionMap.put(18, image18);
        positionMap.put(19, image19);
        positionMap.put(20, image20);
        positionMap.put(21, image21);
        positionMap.put(22, image22);
        positionMap.put(23, image23);
        positionMap.put(24, image24);
        positionMap.put(25, image25);
        positionMap.put(26, image26);
        positionMap.put(27, image27);
        positionMap.put(28, image28);
        positionMap.put(29, image29);
    }

    /*public void setStartingPawns(GameImmutable gameImmutable) {
        for(Player p: gameImmutable.getPlayers()) {
            if (playerPosition != null && playerPosition.containsKey(0)) {
                ImageView newImage;
                if (p.getColorPlayer() == 1) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setLayoutX(image0.getLayoutX() - 5);
                    newImage.setLayoutY(image0.getLayoutY() - 5);
                } else if (p.getColorPlayer() == 2) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setLayoutX(image0.getLayoutX() - 5);
                    newImage.setLayoutY(image0.getLayoutY() + 5);
                } else if (p.getColorPlayer() == 3) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setLayoutX(image0.getLayoutX() + 5);
                    newImage.setLayoutY(image0.getLayoutY() - 5);
                } else {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setLayoutX(image0.getLayoutX() + 5);
                    newImage.setLayoutY(image0.getLayoutY() + 5);
                }
                newImage.setFitWidth(image0.getFitWidth());
                newImage.setFitHeight(image0.getFitHeight());
                newImage.setPreserveRatio(true);
            } /*else {
                if (p.getColorPlayer() == 1) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png"))));
                    image0.setLayoutX(image0.getLayoutX() - 5);
                    image0.setLayoutY(image0.getLayoutY() - 5);
                } else if (p.getColorPlayer() == 2) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png"))));
                    image0.setLayoutX(image0.getLayoutX() - 5);
                    image0.setLayoutY(image0.getLayoutY() + 5);
                } else if (p.getColorPlayer() == 3) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png"))));
                    image0.setLayoutX(image0.getLayoutX() + 5);
                    image0.setLayoutY(image0.getLayoutY() - 5);
                } else {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png"))));
                    image0.setLayoutX(image0.getLayoutX() + 5);
                    image0.setLayoutY(image0.getLayoutY() + 5);
                }
                /*image0.setFitHeight(50);
                image0.setFitWidth(50);*//*
            }*//*
            playerPosition.put(0, p.getColorPlayer());
        }
    }*/
    public void setStartingPawns(GameImmutable gameImmutable) {
        for(Player p: gameImmutable.getPlayers()) {
            ImageView newImage;
            ImageView reference;
            if (p.getColorPlayer() == 1) {
                newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
                x=-5;
                y=-5;
            } else if (p.getColorPlayer() == 2) {
                newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
                x=-5;
                y=5;
            } else if (p.getColorPlayer() == 3) {
                newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
                x=5;
                y=-5;
            } else {
                newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png")));
                x=5;
                y=5;
            }
            reference = positionMap.get(p.getCurrentPoints());
            anchor.getChildren().add(newImage);
            newImage.setLayoutX(reference.getLayoutX() + x);
            newImage.setLayoutY(reference.getLayoutY() + y);
            newImage.setFitWidth(reference.getFitWidth());
            newImage.setFitHeight(reference.getFitHeight());
            newImage.setPreserveRatio(true);
        }
    }
}
