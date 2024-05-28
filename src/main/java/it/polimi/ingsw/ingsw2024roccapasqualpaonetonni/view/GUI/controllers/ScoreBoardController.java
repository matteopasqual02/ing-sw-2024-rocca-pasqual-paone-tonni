package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable.GameImmutable;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.concurrent.ExecutorService;

public class ScoreBoardController extends GenericController{
    @FXML
    private ImageView image0;
    private ExecutorService executor;
    private Client client;
    private GUIApplication application;

    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }

    public void setStartingPawns(GameImmutable gameImmutable, String nickname) {
        for(Player p: gameImmutable.getPlayers()){
                if(p.getColorPlayer() == 1){
                    image0 = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_vert.png")));
                } else if (p.getColorPlayer() == 2) {
                    image0 = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_bleu.png")));
                } else if (p.getColorPlayer() == 3) {
                    image0 = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_rouge.png")));
                } else {
                    image0 = new ImageView(String.valueOf(getClass().getResource("/images/Codex_image/CODEX_pion_jaune.png")));
                }
                image0.setFitHeight(50);
                image0.setFitWidth(50);
                Label name = new Label(p.getNickname());
/*
                ImageView hand1 = new ImageView();
                ImageView hand2 = new ImageView();
                ImageView hand3 = new ImageView();
                cardId = p.getHand().get(0).getIdCard();
                hand1.setImage(new Image(createBackPath(cardId)));
                cardId = p.getHand().get(1).getIdCard();
                hand2.setImage(new Image(createBackPath(cardId)));
                cardId = p.getHand().get(2).getIdCard();
                hand3.setImage(new Image(createBackPath(cardId)));

                hand1.setFitHeight(30);
                hand1.setFitWidth(30);
                hand2.setFitHeight(30);
                hand2.setFitWidth(30);
                hand3.setFitHeight(30);
                hand3.setFitWidth(30);

                String printPoints = "Points: " + p.getCurrentPoints();
                Label points = new Label(printPoints);
                Button button = new Button("See board");
                VBox vBox1 = new VBox(name, color);
                HBox hbox1 = new HBox(hand1,hand2,hand3);
                VBox vBox2 = new VBox(hbox1,points,button);
                HBox hbox2 = new HBox(vBox1,vBox2);

                otherPlayersVBox.getChildren().add(hbox2);

                receiverPrivateMessages.getItems().add(p.getNickname());*/
            }
    }
}
