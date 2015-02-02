package io.github.psgs.jane.utilities;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import io.github.psgs.jane.modules.*;

import java.io.IOException;

public class AmbientListener {

    /**
     * Starts an Ambient Listening process that listens for speech and attempts to convert it to text.
     */
    public static void ambientListening() throws IOException {
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
            String utterance = recognizer.getResult().getHypothesis().replace("jane", "");
            if (utterance.equals("time") || utterance.equals("date")) {
                Time.execute();
            }
            if (utterance.contains("github")) {
                if (utterance.equals("github")) {
                    GitHubJobs.execute();
                } else {
                    String[] command = utterance.split(" ");
                    if (command[0].equals("github")) {
                        GitHubJobs.execute(command[1]);
                    }
                }
            }
            if (utterance.equals("panic panic panic")) {
                Panic.execute();
            }
            if (utterance.contains("tweets")) {
                String[] command = utterance.split(" ");
                if (command.length > 1) {
                    if (command[0].equals("tweets")) {
                        ReadTweet.execute(command[1]);
                    }
                } else {
                    System.out.println("An argument wasn't detected for the Twitter command!");
                }
            }
            if (utterance.contains("google")) {
                GoogleSearch.execute(utterance.replace("google", ""));
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