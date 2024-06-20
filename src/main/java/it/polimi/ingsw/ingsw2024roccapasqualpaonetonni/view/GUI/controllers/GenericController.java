package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;

import java.util.concurrent.ExecutorService;

/**
 * The type Generic controller.
 */
public abstract class GenericController {
    /**
     * Set parameters.
     *
     * @param executor    the executor
     * @param client      the client
     * @param application the application
     */
    public void setParameters(ExecutorService executor, Client client, GUIApplication application){}
}
