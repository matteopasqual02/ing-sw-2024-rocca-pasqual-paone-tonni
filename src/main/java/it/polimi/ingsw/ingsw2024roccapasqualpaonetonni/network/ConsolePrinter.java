package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;
import  org.fusesource.jansi.Ansi;
public class ConsolePrinter {
    public static void consolePrinter(Ansi message){
        new Thread(()->System.out.println(message)).start();
    }
    public static void consolePrinter(String message){
        new Thread(()->System.out.println(message)).start();
    }
    public static void consolePrinter(StringBuilder message){
        new Thread(()->System.out.println(message)).start();
    }

}
