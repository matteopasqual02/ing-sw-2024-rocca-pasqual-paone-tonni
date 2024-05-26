module it.polimi.ingsw.ingsw2024roccapasqualpaonetonni {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires org.fusesource.jansi;
    requires java.rmi;

    opens it.polimi.ingsw.ingsw2024roccapasqualpaonetonni to javafx.fxml;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni;

    //I had to add this last one to make mainServer work
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.listener;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.chat;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.immutable;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.exception;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.model.cards.objective;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI;
}