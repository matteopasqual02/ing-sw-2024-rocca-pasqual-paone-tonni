module it.polimi.ingsw.ingsw2024roccapasqualpaonetonni {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires org.fusesource.jansi;
    requires java.rmi;

    opens it.polimi.ingsw.ingsw2024roccapasqualpaonetonni to javafx.fxml;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni;
    //i had to add this last one to make mainServer work
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.controller.controllerInterface;
}