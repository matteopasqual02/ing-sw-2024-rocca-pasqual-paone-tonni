package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class JoinedGameController {
    @FXML
    private VBox labelBox;
    public void addNewLabel(String text){
        Label newLabel = new Label(text);
        labelBox.getChildren().add(newLabel);
    }
}
