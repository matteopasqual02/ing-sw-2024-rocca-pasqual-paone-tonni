package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.ConnectionType;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GameFlow;

import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        clearCMD();
        int selection;
        String input;
        killLoggers();

        do {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert remote IP (leave empty for localhost)"));
            input = new Scanner(System.in).nextLine();
            if(!input.isEmpty() && !isValidIP(input)){
                clearCMD();
                ConsolePrinter.consolePrinter("Not valid");
            }
        } while (!input.isEmpty() && !isValidIP(input));

        if (!input.isEmpty()){
            DefaultNetworkValues.Server_Ip_address = input;
        }

        clearCMD();
        do {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert your IP (leave empty for localhost)"));
            input = new Scanner(System.in).nextLine();
            if(!input.isEmpty() && !isValidIP(input)){
                clearCMD();
                ConsolePrinter.consolePrinter("Not valid");
            }
        } while (!input.isEmpty() && !isValidIP(input));
        if (!input.isEmpty()){System.setProperty("java.rmi.server.hostname", input);}


        clearCMD();
        do {
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Select option: \n (1) TUI + Socket \n (2) TUI + RMI \n (3) GUI + Socket \n (4) GUI + RMI "));
            input = new Scanner(System.in).nextLine();
            try {
                selection = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                selection = -1;
                ConsolePrinter.consolePrinter("Nan");
            }
        } while (selection != 1 && selection != 2 && selection != 3 && selection != 4);



        //Get the Communication Protocol wanted
        ConnectionType conSel;
        if (selection == 1 || selection == 3) {
            conSel = ConnectionType.SOCKET;
        } else {
            conSel = ConnectionType.RMI;
        }

        ConsolePrinter.consolePrinter("Starting the game!");

        //Starts the UI wanted
        if (selection == 1 || selection == 2) {
            //Starts the game with TUI
            new GameFlow(conSel);

        } else {
            //Starts the game with GUI
            new GameFlow(conSel);
        }

    }

    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            ConsolePrinter.consolePrinter("\033\143");   //for Mac
        }
    }

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private static void killLoggers(){
        /*com.sun.javafx.util.Logging.getJavaFXLogger().disableLogging();
        com.sun.javafx.util.Logging.getCSSLogger().disableLogging();
        com.sun.javafx.util.Logging.getAccessibilityLogger().disableLogging();
        com.sun.javafx.util.Logging.getFocusLogger().disableLogging();
        com.sun.javafx.util.Logging.getInputLogger().disableLogging();
        com.sun.javafx.util.Logging.getLayoutLogger().disableLogging();*/
    }

}
