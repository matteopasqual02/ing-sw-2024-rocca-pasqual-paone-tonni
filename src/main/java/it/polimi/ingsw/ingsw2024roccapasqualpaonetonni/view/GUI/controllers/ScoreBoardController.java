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
import java.util.concurrent.ExecutorService;

public class ScoreBoardController extends GenericController{
    @FXML
    private ImageView image0;
    @FXML
    private AnchorPane anchor;
    private ExecutorService executor;
    private Client client;
    private GUIApplication application;
    private HashMap<Integer,ImageView> positionMap; //it holds the number and the variable so we dont have to call each variable separatly
    private HashMap<Integer,Integer> playerPosition;

    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
        positionMap = new HashMap<>();
        playerPosition = new HashMap<>();
        positionMap.put(0,image0);
    }

    public void setStartingPawns(GameImmutable gameImmutable) {
        for(Player p: gameImmutable.getPlayers()) {
            if (playerPosition != null && playerPosition.containsKey(0)) {
                ImageView newImage;
                if (p.getColorPlayer() == 1) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setX(image0.getX() - image0.getX() / 3);
                    newImage.setY(image0.getY() - image0.getY() / 3);
                } else if (p.getColorPlayer() == 2) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setX(image0.getX() - image0.getX() / 3);
                    newImage.setY(image0.getY() + image0.getY() / 3);
                } else if (p.getColorPlayer() == 3) {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setX(image0.getX() + image0.getX() / 3);
                    newImage.setY(image0.getY() - image0.getY() / 3);
                } else {
                    newImage = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png")));
                    anchor.getChildren().add(newImage);
                    newImage.setX(image0.getX() + image0.getX() / 3);
                    newImage.setY(image0.getY() + image0.getY() / 3);
                }
                newImage.setFitWidth(image0.getFitWidth());
                newImage.setFitHeight(image0.getFitHeight());
            } else {
                if (p.getColorPlayer() == 1) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png"))));
                    image0.setX(image0.getX() - image0.getX() / 3);
                    image0.setY(image0.getY() - image0.getY() / 3);
                } else if (p.getColorPlayer() == 2) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png"))));
                    image0.setX(image0.getX() - image0.getX() / 3);
                    image0.setY(image0.getY() + image0.getY() / 3);
                } else if (p.getColorPlayer() == 3) {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png"))));
                    image0.setX(image0.getX() + image0.getX() / 3);
                    image0.setY(image0.getY() - image0.getY() / 3);
                } else {
                    image0.setImage(new Image(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png"))));
                    image0.setX(image0.getX() + image0.getX() / 3);
                    image0.setY(image0.getY() + image0.getY() / 3);
                }
                /*image0.setFitHeight(50);
                image0.setFitWidth(50);*/
            }
            playerPosition.put(0, p.getColorPlayer());
        }
    }
}
