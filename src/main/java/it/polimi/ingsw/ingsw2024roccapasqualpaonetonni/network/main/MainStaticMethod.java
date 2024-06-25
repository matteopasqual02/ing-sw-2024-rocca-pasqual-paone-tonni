package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.main;

import it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.network.ConsolePrinter;

import java.util.Arrays;
import java.util.List;

/**
 * The type Main static method.
 */
public class MainStaticMethod {
    /**
     * Clear cmd.
     */
    public static void clearCMD() {
        // Check os
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            ConsolePrinter.consolePrinter("[ERROR]: clear cmd failed");
        }
    }

    /**
     * Is not valid ip boolean.
     *
     * @param input the input
     * @return the boolean
     */
    public static boolean isNotValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return true;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }
}
