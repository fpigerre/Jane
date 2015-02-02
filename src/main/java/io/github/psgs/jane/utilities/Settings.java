package io.github.psgs.jane.utilities;

import java.io.*;
import java.util.Properties;

public class Settings {

    public static String userName;
    public static String location;
    public static String googleAPIKey;

    /**
     * Loads data from a settings.properties file
     *
     * @throws IOException
     */
    public static void loadConfiguration() throws IOException {
        Properties settings = new Properties();
        settings.load(new FileInputStream("settings.properties"));

        userName = settings.getProperty("username");
        location = settings.getProperty("location");
        googleAPIKey = settings.getProperty("google-api-key");
    }

    /**
     * Stores data in a key-value pair to settings.properties file
     *
     * @param key  The key to store data to
     * @param data The value to store with the key
     */
    public static void storeData(String key, String data) {
        try {
            Properties settings = new Properties();
            settings.load(new FileInputStream("settings.properties"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader("settings.properties"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            boolean overWrittenData = false;

            while (line != null) {
                if (line.contains(key + " = " + data)) {
                    return;
                }
                if (line.contains(key + " = ")) {
                    stringBuilder.append(key + " = " + data);
                    overWrittenData = true;
                } else {
                    stringBuilder.append(line);
                }
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            if (!overWrittenData) {
                stringBuilder.append(key + " = " + data);
            }

            try {
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("settings.properties"), "utf-8"));
                writer.write(stringBuilder.toString());
                writer.flush();
                writer.close();
                bufferedReader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to store data to settings.properties");
        }
    }
}
