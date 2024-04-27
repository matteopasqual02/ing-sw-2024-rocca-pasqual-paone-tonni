package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServer;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server.SocketServer;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;
import static it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter.consolePrinter;
import static org.fusesource.jansi.Ansi.ansi;
//import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.*;

public class MainServer {
    public static void main( String[] args) throws IOException{
        String inputRemoteIP;

        //insert IP
        do{
            clearCMD();
            ConsolePrinter.consolePrinter(ansi().cursor(1, 0).a("Insert the remote IP (leave empty and press enter for localhost);"));
            inputRemoteIP = new Scanner(System.in).nextLine();
        }while (!inputRemoteIP.isEmpty() && !isValidIP(inputRemoteIP)) ;

        //create Remote
        if (inputRemoteIP.isEmpty())
            System.setProperty("java.rmi.server.hostname", DefaultNetworkValues.Remote_ip_address);
        else{
            DefaultNetworkValues.Server_Ip_address = inputRemoteIP;
            System.setProperty("java.rmi.server.hostname", inputRemoteIP);
        }

        //the server will open both of them
        RMIServer.bind();
        new SocketServer();

    }

    private static boolean isValidIP(String input) {
        List<String> parsedIP;
        parsedIP = Arrays.stream(input.split("\\.")).toList();
        if (parsedIP.size() != 4) {
            return false;
        }
        for (String part : parsedIP) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
    private static void clearCMD() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            ConsolePrinter.consolePrinter("\033\143");   //for Mac
        }
    }
}
