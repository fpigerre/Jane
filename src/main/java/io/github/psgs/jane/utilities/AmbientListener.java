package io.github.psgs.jane.utilities;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.*;
import io.github.psgs.jane.ModuleHandler;

import javax.sound.sampled.AudioFileFormat;
import java.io.File;

/**
 * @author MasterEjay
 */
public class AmbientListener {

    /**
     * Starts an Ambient Listening process that listens for speech and attempts to convert it to text.
     * At the moment, this method may not work, as it requires an API that has been marked as broken.
     * Development is currently underway to fix this issue
     */
    @Deprecated
    public static void ambientListening() {
        MicrophoneAnalyzer mic = new MicrophoneAnalyzer(AudioFileFormat.Type.WAVE);
        mic.setAudioFile(new File("AudioSample.wav"));
        while (true) {
            mic.open();
            final int THRESHOLD = 8;
            int volume = mic.getAudioVolume();
            boolean isSpeaking = (volume > THRESHOLD);
            if (isSpeaking) {
                try {
                    System.out.println("RECORDING...");
                    mic.captureAudioToFile(mic.getAudioFile());
                    do {
                        Thread.sleep(1000); // Updates every second
                    }
                    while (mic.getAudioVolume() > THRESHOLD);
                    System.out.println("Recording Complete!");
                    System.out.println("Recognizing...");
                    Recognizer rec = new Recognizer(Recognizer.Languages.AUTO_DETECT);
                    GoogleResponse response = rec.getRecognizedDataForWave(mic.getAudioFile(), 3);
                    GSpeechDuplex duplex = new GSpeechDuplex(Settings.googleAPIKey);
                    FlacEncoder flacEncoder = new FlacEncoder();
                    flacEncoder.convertWaveToFlac(mic.getAudioFile(), new File(mic.getAudioFile() + ".flac"));
                    duplex.addResponseListener(new GSpeechResponseListener() {
                        @Override
                        public void onResponse(GoogleResponse googleResponse) {
                            displayResponse(googleResponse);
                        }
                    });
                    duplex.recognize(new File(mic.getAudioFile() + ".flac"), 8000);
                    System.out.println("Looping back");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error Occurred");
                } finally {
                    mic.close();
                }
            }
        }
    }

    /**
     * Manipulates and displays a GoogleResponse object
     * @param gr A GoogleResponse object to display
     */
    private static void displayResponse(GoogleResponse gr) {
        if (gr.getResponse() == null) {
            System.out.println((String) null);
            return;
        }
        System.out.println("Google Response: " + gr.getResponse());
        System.out.println("Google is " + Double.parseDouble(gr.getConfidence()) * 100 + "% confident in"
                + " the reply");
        System.out.println("Other Possible responses are: ");
        for (String string : gr.getOtherPossibleResponses()) {
            System.out.println("\t" + string);
        }
        if (gr.getResponse() != null) {
            ModuleHandler.executeModule(gr.getResponse());
        }
    }

}