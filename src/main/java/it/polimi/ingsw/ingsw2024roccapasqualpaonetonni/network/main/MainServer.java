package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.RMIServer;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.socket.server.SocketServer;
import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils.DefaultNetworkValues;

import java.io.IOException;
import java.util.Scanner;
//import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.RMI.remoteinterfaces.*;

/**
 * The type Main server.
 */
public class MainServer {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
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

    /*only test*/
    public static void test() {
        System.setProperty("java.rmi.server.hostname", DefaultNetworkValues.Remote_ip_address);
        RMIServer.bind();
        SocketServer server = new SocketServer();
        server.start(DefaultNetworkValues.Default_SOCKET_port);
    }
}
