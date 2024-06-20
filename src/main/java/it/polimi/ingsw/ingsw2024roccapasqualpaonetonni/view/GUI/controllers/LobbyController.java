package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * The type Lobby controller.
 */
public class LobbyController extends GenericController {
    /**
     * The Application.
     */
    private GUIApplication application;

    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    public void setParameters(ExecutorService executor, Client client,GUIApplication application){
        this.application = application;
    }

    /**
     * Handle new game button click.
     */
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

    /**
     * Handle join game button click.
     */
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

    /**
     * Handle join game id button click.
     */
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

    /**
     * Handle reconnect button click.
     */
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
