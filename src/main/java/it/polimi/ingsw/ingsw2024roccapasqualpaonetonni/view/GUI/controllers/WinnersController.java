package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.Player;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class WinnersController {
    @FXML
    private Pane ranking;

    @FXML
    private VBox vbox;

    public void showWinners(List<Player> rankings){
        int count = 1;
        int position = 1;
        int points = rankings.getFirst().getCurrentPoints();
        Pane entry;
        for (Player player : rankings){
            if (player.getCurrentPoints() < points) {
                position = count;
                points = player.getCurrentPoints();
            }
            entry = addPosition(position, player.getNickname(), player.getCurrentPoints());
            vbox.getChildren().add(entry);
            count++;
        }
    }

    /*
    private void addPosition(int i, String nickname, int points){
        HBox hbox = new HBox();

        ImageView imageView = new ImageView();
        String path = getNumber(i);
        if (path != null) {
            imageView.setImage(new Image(String.valueOf(getClass().getResource(path))));
        }
        imageView.setFitWidth(77);
        imageView.setFitHeight(77);
        hbox.getChildren().add(imageView);

        Label label = new Label(nickname);
        label.setPrefWidth(125);
        hbox.getChildren().add(label);

        label = new Label(String.valueOf(points));
        label.setPrefWidth(58);
        hbox.getChildren().add(label);

        ranking.getChildren().add(hbox);
    }
    */

    private Pane addPosition(int i, String nickname, int points){
        Pane entry = new Pane();
        double width = ranking.getWidth() * 0.9;
        double height = ranking.getHeight() * 0.25;
        entry.setPrefWidth(width);
        entry.setPrefHeight(height);

        ImageView imageView = new ImageView();
        String path = getNumber(i);
        if (path != null) {
            imageView.setImage(new Image(String.valueOf(getClass().getResource(path))));
        }
        imageView.setFitWidth(height*0.9);
        imageView.setFitHeight(height*0.9);
        entry.getChildren().add(imageView);
        imageView.setLayoutX(width*0.06);
        imageView.setLayoutY(height*0.05);

        Label label = new Label(nickname);
        label.setPrefWidth(width*0.4);
        label.setPrefHeight(height*0.9);
        entry.getChildren().add(label);
        label.setLayoutX(width*0.3);
        label.setLayoutY(height*0.05);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Times New Roman'; -fx-background-size: 60");

        label = new Label(String.valueOf(points));
        label.setPrefWidth(width*23);
        label.setPrefHeight(height*0.9);
        entry.getChildren().add(label);
        label.setLayoutX(width*0.76);
        label.setLayoutY(height*0.05);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Times New Roman'; -fx-background-size: 60");

        return entry;
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
