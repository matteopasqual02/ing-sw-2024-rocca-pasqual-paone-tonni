package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

public class NameNumberController extends GenericController {
    @FXML
    public Button button;
    @FXML
    private TextField string1;
    @FXML
    //private TextField string2;
    private ExecutorService executor;
    private Client client;
    private int num;

    private ToggleButton lastClicked = null;

    @Override
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){
        this.executor = executor;
        this.client = client;
    }

    /*@FXML
    public void handleButtonClick(ActionEvent event){

        String name = string1.getText();
        try{
            num = Integer.parseInt(string2.getText());
        }catch (NumberFormatException e){
            //qualcosa che fa reinserire il tutto
        }

        executor.submit(()->{
            try {
                client.receiveInput("/new "+num + " "+ name);
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }*/

    @FXML
    public void handleButtonClick(ActionEvent event) {
        executor.submit(()->{
            try {
                client.receiveInput("/new "+num + " "+ string1.getText());
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    public void handleButton2Click(ActionEvent event){
        num = 2;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
    }

    @FXML
    public void handleButton3Click(ActionEvent event){
        num = 3;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
    }

    @FXML
    public void handleButton4Click(ActionEvent event){
        num = 4;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
    }


}
