package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class JoinedGameController {

    public void addNewLabel(String text, Scene scene, StackPane root){
        HBox popup = new HBox(new Label(text));
        popup.setStyle("-fx-background-color: #2a9df4; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        popup.setTranslateX(scene.getWidth());
        root.getChildren().add(popup);

        Timeline slideIn = new Timeline();
        KeyValue keyValueIn = new KeyValue(popup.translateXProperty(), scene.getWidth() - popup.getWidth());
        KeyFrame keyFrameIn = new KeyFrame(Duration.seconds(0.5), keyValueIn);
        slideIn.getKeyFrames().add(keyFrameIn);
        slideIn.play();
    }
}
