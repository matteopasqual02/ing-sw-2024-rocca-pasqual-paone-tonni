package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class NameInsertController extends GenericController{
    @FXML
    public Button button;
    @FXML
    private TextField string1;
    private ExecutorService executor;
    private Client client;
    @Override
    public void setParameters(ExecutorService executor, Client client){
        this.executor = executor;
        this.client = client;
    }
    @FXML
    public void handleButtonClick(ActionEvent event){

        String name = string1.getText();

        executor.submit(()->{
            try {
                client.receiveInput("/join "+ name);
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
