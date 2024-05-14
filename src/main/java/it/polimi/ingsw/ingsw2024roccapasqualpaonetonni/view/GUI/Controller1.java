package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class Controller1 {
    @FXML
    public Button button;
    private ExecutorService executor;
    private Client client;
    public void setParameters(ExecutorService executor, Client client){
        this.executor = executor;
        this.client = client;
    }
    @FXML
    public void handleButtonClick(ActionEvent event){
        executor.submit(()->{
            try {
                client.receiveInput("/new 2 a");
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
