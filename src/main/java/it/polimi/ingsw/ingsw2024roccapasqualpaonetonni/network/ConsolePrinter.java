package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;
import  org.fusesource.jansi.Ansi;
public class ConsolePrinter {
    public static void consolePrinter(Ansi msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void consolePrinter(String msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void consolePrinter(StringBuilder msg){
        new Thread(()->{System.out.println(msg);}).start();
    }
    public static void consolePrinterNoLine(String msg){
        new Thread(()->{System.out.print(msg);}).start();
    }
}
