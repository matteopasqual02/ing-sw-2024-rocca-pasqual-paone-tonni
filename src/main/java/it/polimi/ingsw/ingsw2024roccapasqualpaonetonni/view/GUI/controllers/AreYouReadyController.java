package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class AreYouReadyController extends GenericController{
    @FXML
    public Button button;
    private ExecutorService executor;
    private Client client;
    @Override
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
    }
    @FXML
    public void handleButtonClick(ActionEvent event){

        executor.submit(()->{
            try {
                client.receiveInput("y");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
