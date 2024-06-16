package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class WinnersController {
    @FXML
    private VBox ranking;


    public void showWinners(List<Player> rankings){
        int i = 1;
        for (Player player : rankings){
            addPosition(i, player.getNickname(), player.getCurrentPoints());
            i++;
        }
    }

    private void addPosition(int i, String nickname, int points){
        HBox hbox = new HBox();

        ImageView imageView = new ImageView();
        String path = getNumber(i);
        if (path != null) {
            imageView.setImage(new Image(path));
        }
        imageView.setFitWidth(77);
        hbox.getChildren().add(imageView);

        Label label = new Label(nickname);
        label.setPrefWidth(125);
        hbox.getChildren().add(label);

        label = new Label(String.valueOf(points));
        label.setPrefWidth(58);
        hbox.getChildren().add(label);

        ranking.getChildren().add(hbox);
    }

    private String getNumber(int i) {
        switch (i) {
            case 1:
                return "/images/Various_image/first.png";
            case 2:
                return "/images/Various_image/second.png";
            case 3:
                return "/images/Various_image/third.png";
            case 4:
                return "/images/Various_image/fourth.png";
            default:
                return null;
        }
    }
}
