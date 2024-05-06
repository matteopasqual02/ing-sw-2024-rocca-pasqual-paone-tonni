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
            MainStaticMethod.clearCMD();
            ConsolePrinter.consolePrinter("[MAIN] Insert the remote IP or leave empty and press enter for localhost;");
            inputRemoteIP = new Scanner(System.in).nextLine();
        }while (!inputRemoteIP.isEmpty() && MainStaticMethod.isNotValidIP(inputRemoteIP)) ;

        //create Remote
        if (inputRemoteIP.isEmpty())
            System.setProperty("java.rmi.server.hostname", DefaultNetworkValues.Remote_ip_address);
        else{
            DefaultNetworkValues.Server_Ip_address = inputRemoteIP;
            System.setProperty("java.rmi.server.hostname", inputRemoteIP);
        }

        //the server will open both of them
        RMIServer.bind();
        SocketServer server = new SocketServer();
        server.start(DefaultNetworkValues.Default_SOCKET_port);

    }

}
