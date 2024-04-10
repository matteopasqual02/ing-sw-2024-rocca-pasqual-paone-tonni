package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.GameControllerInterface;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.MainControllerInterface;

public class RMIServer implements MainControllerInterface {

    public static void main(String[] args) {
        System.out.println("Hello from server!");
    }

    @Override
    public GameControllerInterface CreateGameController(String nickname) {
        return null;
    }

    @Override
    public GameControllerInterface joinFirstAvailableGame(String nickname) {
        return null;
    }

    @Override
    public GameControllerInterface reconnect(String nickname) {
        return null;
    }

    @Override
    public GameControllerInterface leaveGame(String nickname) {
        return null;
    }
}
