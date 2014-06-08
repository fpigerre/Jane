package io.github.psgs.jane.utilities;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class BrowserUtils {

    /**
     * Opens a page in a Web Browser
     * @param uri The URI to open in a Browser
     */
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Opens a page in a Web Browser
     * @param url The URL to open in a Browser
     */
    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns whether the host system is connected to the internet
     * @return Whether the host system is connected to the internet
     */
    public static boolean isConnected() {
        try {
            URL url = new URL("http://google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            return urlConnect.getResponseCode() == 200;
        } catch (IOException ex) {
            return false;
        }
    }
}
