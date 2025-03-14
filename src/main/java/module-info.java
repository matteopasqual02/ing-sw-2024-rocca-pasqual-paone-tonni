module it.polimi.ingsw.ingsw2024roccapasqualpaonetonni {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires org.fusesource.jansi;
    requires java.rmi;

    opens it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI to javafx.fxml;
    opens it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers to javafx.fxml;

    //I had to add this last one to make mainServer work
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.client;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.clientMessages;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.serverMessages;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.TUI;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.controllers;

}