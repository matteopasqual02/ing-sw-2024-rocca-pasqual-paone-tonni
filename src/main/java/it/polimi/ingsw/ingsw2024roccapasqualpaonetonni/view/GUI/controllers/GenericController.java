package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;

import java.util.concurrent.ExecutorService;

public abstract class GenericController {
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){}
}
