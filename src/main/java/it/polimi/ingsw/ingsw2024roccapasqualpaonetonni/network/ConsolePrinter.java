package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network;

import org.fusesource.jansi.Ansi;

/**
 * The type Console printer.
 */
public class ConsolePrinter {
    /**
     * Console printer.
     *
     * @param message the message
     */
    public static void consolePrinter(Ansi message){
        System.out.println(message);
    }

    /**
     * Console printer.
     *
     * @param message the message
     */
    public static void consolePrinter(String message){
        System.out.println(message);
    }

    /**
     * Console printer.
     *
     * @param message the message
     */
    public static void consolePrinter(StringBuilder message){
        System.out.println(message);
    }

}
