package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public class LobbyController extends GenericController {
    private GUIApplication application;

    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.application = application;
    }
    
    @FXML
    public void handleNewGameButtonClick(){
        Platform.runLater(()-> {
            try {
                application.changeScene("/NameNumber.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void handleJoinGameButtonClick(){
        Platform.runLater(()-> {
            try {
                application.changeScene("/Name.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void handleJoinGameIDButtonClick(){
        Platform.runLater(()-> {
            try {
                application.changeScene("/NameGameId.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void handleReconnectButtonClick(){
        Platform.runLater(()-> {
            try {
                application.changeScene("/Reconnect.fxml");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
