module it.polimi.ingsw.ingsw2024roccapasqualpaonetonni {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires org.fusesource.jansi;

    opens it.polimi.ingsw.ingsw2024roccapasqualpaonetonni to javafx.fxml;
    exports it.polimi.ingsw.ingsw2024roccapasqualpaonetonni;
}