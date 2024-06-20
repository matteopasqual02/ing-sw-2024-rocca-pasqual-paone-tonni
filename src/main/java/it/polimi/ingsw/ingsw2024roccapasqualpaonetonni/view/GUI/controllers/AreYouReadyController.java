package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

/**
 * The type Are you ready controller.
 */
public class AreYouReadyController extends GenericController{
    /**
     * The Button.
     */
    @FXML
    public Button button;

    /**
     * The executor.
     */
    private ExecutorService executor;

    /**
     * The client.
     */
    private Client client;

    /**
     * The GUI application.
     */
    private GUIApplication application;

    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    @Override
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
        this.application = application;
    }

    /**
     * Handle button click.
     */
    @FXML
    public void handleButtonClick(){
        Platform.runLater(()->{
                    try {
                        application.changeSceneWithNoController("/WaitingForOthers.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        executor.submit(()->{
            try {
                client.receiveInput("y");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
