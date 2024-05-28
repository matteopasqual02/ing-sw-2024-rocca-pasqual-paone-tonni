package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class JoinedGameController {
    private final VBox vbox = new VBox();

    public void addNewLabel(String text, StackPane root){
        Label label = new Label(text);
        label.setStyle("-fx-background-color: beige; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 5px; -fx-font-family: 'Times New Roman'; -fx-font-size: 18px;");
        label.setPrefWidth(200);
        label.setPrefHeight(50);
        vbox.setSpacing(10);
        vbox.getChildren().add(label);
        StackPane.setAlignment(vbox,Pos.CENTER_RIGHT);
        root.getChildren().add(vbox);
    }
}
