package io.github.psgs.jane.utilities;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import io.github.psgs.jane.modules.Time;

import java.io.IOException;

public class AmbientListener {

    /**
     * Starts an Ambient Listening process that listens for speech and attempts to convert it to text.
     */
    public static void ambientListening() throws IOException {
        System.out.println("Ambient listening started...");
        Configuration configuration = new Configuration();

        // Set path to acoustic model.
        configuration.setAcousticModelPath("./src/main/resources/sphinx-data/en-us");
        // Set path to dictionary.
        configuration.setDictionaryPath("./src/main/resources/sphinx-data/cmudict-en-us.dict");
        // Set language model.
        configuration.setLanguageModelPath("./src/main/resources/sphinx-data/en-us.lm.dmp");
        // Set grammar path.
        configuration.setGrammarPath("./src/main/resources/sphinx-data/grammar/");
        configuration.setUseGrammar(true);
        configuration.setGrammarName("commands.grxml");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        recognizer.startRecognition(true);
        while (true) {
            String utterance = recognizer.getResult().getHypothesis();
            if (utterance.equals("time")) {
                Time.execute();
            }
            if (utterance.equals("one zero one") || utterance.equals("one oh one")) {
                break;
            } else {
                System.out.println(utterance);
            }
            System.out.println(utterance);
        }
        recognizer.stopRecognition();
    }
}