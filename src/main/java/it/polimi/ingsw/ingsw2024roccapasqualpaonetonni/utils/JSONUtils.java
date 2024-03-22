package it.polimi.ingsw.ingsw2024roccapasqualpaonetonni.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JSONUtils {
    public static JsonObject parseTxtToJson(String filePath) throws IOException {
        // Read the content of the TXT file
        String txtContent = readTXTFile(filePath);

        // Parse the TXT content to JSON using Gson
        Gson gson = new Gson();
        return gson.fromJson(txtContent, JsonObject.class);
    }

    private static String readTXTFile(String filePath) throws IOException {
        // Read the contents of the TXT file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
