package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * The type Joined game controller.
 */
public class JoinedGameController {
    /**
     * The Vbox.
     */
    private final VBox vbox = new VBox();

    /**
     * Set up controller.
     *
     * @param root the root
     */
    public void setUpController(StackPane root){
        StackPane.setAlignment(vbox,Pos.CENTER_RIGHT);
        root.getChildren().add(vbox);
        vbox.setSpacing(10);
    }

    /**
     * Add new label.
     *
     * @param text the text
     */
    public void addNewLabel(String text){
        Label label = new Label(text);
        label.setStyle("-fx-background-color: beige; -fx-padding: 10px; -fx-border-color: black; -fx-border-width: 3px; -fx-border-radius: 0px; -fx-font-family: 'Times New Roman'; -fx-font-size: 18px;");
        label.setPrefWidth(200);
        label.setPrefHeight(50);
        vbox.getChildren().add(label);
    }
}
