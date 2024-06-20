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

/**
 * The type Winners controller.
 */
public class WinnersController {
    /**
     * The Ranking.
     */
    @FXML
    private Pane ranking;

    /**
     * The Vbox.
     */
    @FXML
    private VBox vbox;

    /**
     * Show winners.
     *
     * @param rankings the rankings
     */
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
            vbox.setSpacing(20);
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

    /**
     * Add position pane.
     *
     * @param i        the
     * @param nickname the nickname
     * @param points   the points
     * @return the pane
     */
    private Pane addPosition(int i, String nickname, int points){
        Pane entry = new Pane();
        entry.setPrefWidth(324);
        entry.setPrefHeight(55);

        ImageView imageView = new ImageView();
        String path = getNumber(i);
        if (path != null) {
            imageView.setImage(new Image(String.valueOf(getClass().getResource(path))));
        }
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        entry.getChildren().add(imageView);
        imageView.setLayoutX(22);
        imageView.setLayoutY(5);

        Label label = new Label(nickname);
        label.setPrefWidth(130);
        label.setPrefHeight(40);
        entry.getChildren().add(label);
        label.setLayoutX(99);
        label.setLayoutY(5);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Times New Roman'; -fx-background-size: 60; -fx-font-size: 30px");

        label = new Label(String.valueOf(points));
        label.setPrefWidth(75);
        label.setPrefHeight(40);
        entry.getChildren().add(label);
        label.setLayoutX(245);
        label.setLayoutY(5);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-weight: bold; -fx-font-family: 'Times New Roman'; -fx-background-size: 60; -fx-font-size: 30px");

        return entry;
    }

    /**
     * Gets number.
     *
     * @param i the
     * @return the number
     */
    private String getNumber(int i) {
        return switch (i) {
            case 1 -> "/images/Various_image/first.png";
            case 2 -> "/images/Various_image/second.png";
            case 3 -> "/images/Various_image/third.png";
            case 4 -> "/images/Various_image/fourth.png";
            default -> null;
        };
    }
}
