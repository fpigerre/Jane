package io.github.psgs.jane.utilities;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import javazoom.jl.decoder.JavaLayerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

public class AudioUtils {

    /**
     * Speaks a string of text using Google's unofficial translation API
     * @param text A string of text to speak
     * @throws FileNotFoundException
     */
    public static void talk(String text) throws FileNotFoundException {
        Audio audio = Audio.getInstance();
        InputStream sound = null;
        try {
            sound = audio.getAudio(text, Language.ENGLISH);
        } catch (UnknownHostException ex) {
            System.out.println("Jane failed to connect to the speech service!");
            System.out.println("Are you currently connected to the internet?");
        } catch (IOException ex) {
            System.out.println("An error occurred while trying to output audio!");
            ex.printStackTrace();
        }
        try {
            audio.play(sound);
        } catch (JavaLayerException ex) {
            System.out.println("An exception occurred while trying to output audio!");
        } catch (NullPointerException ex) {
            System.out.println("");
        }
    }
}