package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumConnectionType;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.Client;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.EnumViewType;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.view.GUI.GUIApplication;
import javafx.application.Application;
import org.fusesource.jansi.AnsiConsole;


import static org.fusesource.jansi.Ansi.ansi;

public class MainClient {

    public static void main(String[] args) throws IOException, NotBoundException {
        MainStaticMethod.clearCMD();
        boolean selection;
        String inputString;
        EnumConnectionType selConnection;
        EnumViewType selView;

        do {
            ConsolePrinter.consolePrinter("[MAIN] Insert the Sever IP or leave empty for localhost");
            inputString = new Scanner(System.in).nextLine();
            if(!inputString.isEmpty() && MainStaticMethod.isNotValidIP(inputString)){
                MainStaticMethod.clearCMD();
                ConsolePrinter.consolePrinter("[MAIN] Not valid IP");
            }
        } while (!inputString.isEmpty() && MainStaticMethod.isNotValidIP(inputString));
        if (!inputString.isEmpty()){
            DefaultNetworkValues.Server_Ip_address = inputString;
        }

        MainStaticMethod.clearCMD();
        do {
            ConsolePrinter.consolePrinter("[MAIN] Insert Your IP or leave empty for localhost ");
            inputString = new Scanner(System.in).nextLine();
            if(!inputString.isEmpty() && MainStaticMethod.isNotValidIP(inputString)){
                MainStaticMethod.clearCMD();
                ConsolePrinter.consolePrinter("[MAIN] Not valid IP");
            }
        } while (!inputString.isEmpty() && MainStaticMethod.isNotValidIP(inputString));
        if (!inputString.isEmpty()){System.setProperty("java.rmi.server.hostname", inputString);}


        MainStaticMethod.clearCMD();
        do {
            ConsolePrinter.consolePrinter(
                    "Select option: \n (ts)-> TUI + Socket \n (tr)-> TUI + RMI \n (gs)-> GUI + Socket \n (gr)-> GUI + RMI "
            );
            inputString = new Scanner(System.in).nextLine();

            switch (inputString){
                case "ts" -> {
                    selection = true;
                    selConnection = EnumConnectionType.SOCKET;
                    selView = EnumViewType.TUI;
                    ConsolePrinter.consolePrinter("[MAIN] Starting the game manager!");
                    new Client(selConnection);
                }
                case "tr" -> {
                    selection = true;
                    selConnection = EnumConnectionType.RMI;
                    selView = EnumViewType.TUI;
                    ConsolePrinter.consolePrinter("[MAIN] Starting the game manager!");
                    new Client(selConnection);
                }
                case "gs" -> {
                    selection = true;
                    selConnection = EnumConnectionType.SOCKET;
                    selView = EnumViewType.GUI;
                    ConsolePrinter.consolePrinter("[MAIN] Starting the game manager!");

                    Application.launch(GUIApplication.class, selConnection.toString());
                }
                case "gr" -> {
                    selection = true;
                    selConnection = EnumConnectionType.RMI;
                    selView = EnumViewType.GUI;
                    ConsolePrinter.consolePrinter("[MAIN] Starting the game manager!");

                    Application.launch(GUIApplication.class, selConnection.toString());
                }
                case null, default -> {
                    selection = false;
                    selConnection = null;
                    selView = null;
                    ConsolePrinter.consolePrinter("[MAIN] Not valid option");
                }
            }

        } while (!selection);

        /*ConsolePrinter.consolePrinter("[MAIN] Starting the game manager!");

        new Client(selConnection,selView);*/
    }




}
