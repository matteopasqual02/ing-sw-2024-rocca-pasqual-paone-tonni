package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.concurrent.ExecutorService;

/**
 * The type Name number controller.
 */
public class NameNumberController extends GenericController {
    /**
     * The Button.
     */
    @FXML
    public Button button;
    @FXML
    public Button createButton;
    /**
     * The String 1.
     */
    @FXML
    private TextField string1;
    /**
     * The Executor.
     */
    @FXML
    //private TextField string2;
    private ExecutorService executor;
    /**
     * The Client.
     */
    private Client client;
    /**
     * The Num.
     */
    private int num;

    /**
     * The Last clicked.
     */
    private ToggleButton lastClicked = null;

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
        createButton.setDisable(true);
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

    /**
     * Handle button click.
     */
    @FXML
    public void handleButtonClick() {
        executor.submit(()->{
            try {
                client.receiveInput("/new "+num + " "+ string1.getText());
            } catch (IOException | NotBoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Handle button 2 click.
     *
     * @param event the event
     */
    @FXML
    public void handleButton2Click(ActionEvent event){
        num = 2;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
        createButton.setDisable(false);
    }

    /**
     * Handle button 3 click.
     *
     * @param event the event
     */
    @FXML
    public void handleButton3Click(ActionEvent event){
        num = 3;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
        createButton.setDisable(false);
    }

    /**
     * Handle button 4 click.
     *
     * @param event the event
     */
    @FXML
    public void handleButton4Click(ActionEvent event){
        num = 4;
        if (lastClicked != null){
            lastClicked.getStyleClass().remove("clicked");
        }
        lastClicked = (ToggleButton) event.getSource();
        lastClicked.getStyleClass().add("clicked");
        createButton.setDisable(false);
    }


}
