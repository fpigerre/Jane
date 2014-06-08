package io.github.psgs.jane;

import io.github.psgs.jane.interfaces.SystemTrayIcon;
import io.github.psgs.jane.interfaces.TwitterPopup;
import io.github.psgs.jane.utilities.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.*;
import java.io.*;
import java.util.Properties;

public class Jane {

    // Static reference for the TrayIcon
    static SystemTrayIcon trayIcon;

    // Whether Twitter keys are loaded correctly
    static String consumerKey = null;
    static String consumerSecret = null;
    static boolean validAccessToken = false;
    static boolean validAccessTokenSecret = false;

    // Whether Twitter is intended on being used
    public static boolean twitterUsed = false;

    public static void main(String[] args) {
        // Set the console output to log.log
        try {
            File output = new File("log.log");
            if (!output.exists()) output.createNewFile();
            PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
            System.setOut(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create a new TrayIcon
        trayIcon = new SystemTrayIcon();
        initiateFiles();
        refreshTwitterKeys();
        if (BrowserUtils.isConnected()) {
            try {
                // If consumer keys have been loaded, but not tokens, initiate a Twitter authentication
                if (consumerKey != null && consumerSecret != null && (!validAccessToken || !validAccessTokenSecret)) {
                    authenticateTwitter();
                } else if (consumerKey != null && consumerSecret != null && validAccessToken && validAccessTokenSecret) {
                    twitterUsed = true;
                    System.out.println("Successfully authenticated with Twitter!");
                }
            } catch (Exception ex) {
                System.out.println("An error occurred while trying to authenticate with Twitter!");
                ex.printStackTrace();
            }
            try {
                // Welcome the user and load up modules
                Properties properties = new Properties();
                properties.load(new FileInputStream("settings.properties"));
                System.out.println("Hello " + properties.getProperty("username") + ", I'm Jane!");
                AudioUtils.talk("Hello " + properties.getProperty("username") + ", I'm Jane!");
                System.out.println("Loading background modules...");
                ModuleHandler.runBackgroundModules(args);
            } catch (IOException ex) {
                System.out.println("An error occurred while trying to load a settings property!");
            }
            try {
                System.out.println("Ambient listening started!");
                AmbientListener.ambientListening();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("My FLAC encoder has crashed! This is due to a bug that is currently out of my control.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // If no internet connection is present, TTS may not be possible
            System.err.println("==============================");
            System.err.println("No connection can be found!");
            System.err.println("Some features may be disabled!");
            System.err.println("==============================");
            trayIcon.getTrayIcon().displayMessage("No Connection", "You don't seem to be connected to the internet! This means I may not be able to do some things...", TrayIcon.MessageType.WARNING);
        }
    }

    /**
     * Safely shuts down Jane and securely deletes audio sample files
     */
    public static void exit() {
        try {
            File audioCapture = new File("AudioCapture.wav");
            SecurityUtils.secureDelete(audioCapture);
            audioCapture.delete();
        } catch (IOException ex) {
            System.err.println("An error may have occurred while deleting the Audio Capture file!");
        }
        System.exit(1);
    }

    /**
     * Creates files required by Jane that don't exist yet, such as the settings.properties
     * and twitter4j.properties files
     */
    public static void initiateFiles() {
        File settings = new File("settings.properties");
        try {
            if (!settings.exists()) {
                settings.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("settings.properties"), "utf-8"));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("#######################################\n");
                stringBuilder.append("# Jane's Settings - A project by psgs #\n");
                stringBuilder.append("#######################################\n");
                stringBuilder.append("\n");
                stringBuilder.append("username = My Friend \n");
                stringBuilder.append("location = earth");
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            Settings.loadConfiguration();
        } catch (IOException ex) {
            System.out.println("An IOException occurred while trying to initialise settings!");
        }

        File twitter4j = new File("twitter4j.properties");
        try {
            if (!twitter4j.exists()) {
                twitter4j.createNewFile();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("twitter4j.properties"), "utf-8"));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("debug=false\n");
                stringBuilder.append("oauth.consumerKey= [TWITTER CONSUMER KEY HERE]\n");
                stringBuilder.append("oauth.consumerSecret= [TWITTER CONSUMER SECRET HERE]\n");
                stringBuilder.append("oauth.accessToken=\n");
                stringBuilder.append("oauth.accessTokenSecret=");
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
            }
            Settings.loadConfiguration();
        } catch (IOException ex) {

        }
    }

    /**
     * Attempts to find Twitter keys contained in the twitter4j.properties file and load them
     */
    public static void refreshTwitterKeys() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("twitter4j.properties"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                if (line.contains("oauth.consumerKey=")) {
                    if (!line.contains("[TWITTER CONSUMER KEY HERE]"))
                        consumerKey = line.replace("oauth.consumerKey= ", "").replace("oauth.consumerKey=", "");
                }
                if (line.contains("oauth.consumerSecret=")) {
                    if (!line.contains("[TWITTER CONSUMER SECRET HERE]"))
                        consumerSecret = line.replace("oauth.consumerSecret= ", "").replace("oauth.consumerSecret=", "");
                }
                if (line.contains("oauth.accessToken=")) {
                    String rawToken = line.replace("oauth.accessToken=", "");
                    if (!rawToken.equals("") && !rawToken.equals(" ") && rawToken != null) {
                        validAccessToken = true;
                    }
                }
                if (line.contains("oauth.accessTokenSecret=")) {
                    String rawTokenSecret = line.replace("oauth.accessTokenSecret=", "");
                    if (!rawTokenSecret.equals(" ") && rawTokenSecret.equals("") && rawTokenSecret != null) {
                        validAccessTokenSecret = true;
                    }
                }
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a new Twitter request token and opens a popup for the user to authenticate with Twitter
     * @throws Exception
     */
    public static void authenticateTwitter() throws Exception {
        // The factory instance is re-usable and thread safe.
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(consumerKey, consumerSecret);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        System.out.println("Click the Twitter link in the Popup");
        TwitterPopup GUI = new TwitterPopup(requestToken.getAuthorizationURL(), accessToken, requestToken);
        GUI.setVisible(true);
    }

    /**
     * Receives a Twitter pin string and stores it inside an AccessToken
     * @param pin The pin received from Twitter
     * @param accessToken The access token to save the pin with
     * @param requestToken The request token to attach to the access token
     */
    public static void sendPin(String pin, AccessToken accessToken, RequestToken requestToken) {
        Twitter twitter = TwitterFactory.getSingleton();
        try {
            if (pin.length() > 0) {
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = twitter.getOAuthAccessToken();
            }
        } catch (TwitterException ex) {
            if (401 == ex.getStatusCode()) {
                System.out.println("Unable to get the access token.");
            } else {
                ex.printStackTrace();
            }
        }
        // Persist the accessToken for future reference.
        storeAccessToken(accessToken);
        twitterUsed = true;
        System.out.println("Successfully authenticated with Twitter!");
    }

    /**
     * Stores an access token for future use inside a twitter4j.properties file
     * @param accessToken The access token to save
     */
    private static void storeAccessToken(AccessToken accessToken) {
        try {
            File storeFile = new File("twitter4j.properties");
            if (!storeFile.exists()) storeFile.createNewFile();

            Properties settings = new Properties();
            settings.load(new FileInputStream("twitter4j.properties"));

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("debug=false\n");
            stringBuilder.append("oauth.consumerKey=" + consumerKey);
            stringBuilder.append("oauth.consumerSecret=" + consumerSecret);
            stringBuilder.append("oauth.accessToken=" + accessToken.getToken() + "\n");
            stringBuilder.append("oauth.accessTokenSecret=" + accessToken.getTokenSecret() + "\n");

            try {
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("twitter4j.properties"), "utf-8"));
                writer.write(stringBuilder.toString());
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to save Twitter keys");
        }
    }
}