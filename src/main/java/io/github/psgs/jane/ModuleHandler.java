package io.github.psgs.jane;

import io.github.psgs.jane.modules.Module;
import io.github.psgs.jane.modules.TweetCheck;
import io.github.psgs.jane.utilities.StringUtils;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleHandler {

    static Reflections reflections = new Reflections("io.github.psgs.jane.modules");
    static Set<Class<? extends Module>> modules =
            reflections.getSubTypesOf(Module.class);

    public static void runBackgroundModules(String[] args) {
        TweetCheck.main(args);
    }

    /**
     * Initiates the execution of a module based on raw text input, deduced from speech
     *
     * @param rawInput A TTS conversion of speech
     */
    public static void executeModule(String rawInput) {
        String input = StringUtils.stripStringFromArray(rawInput, new String[]{".", "jane"});
        String[] splitInput = input.split(" ");
        try {
            for (Class module : modules) {
                int matches = 0;
                List<String> matchedStrings = new ArrayList<String>();
                Module instance = (Module) module.newInstance();
                for (String acceptedString : instance.getAcceptedInput()) {
                    for (String inputString : splitInput) {
                        if (inputString.equals(acceptedString)) {
                            matches++;
                            matchedStrings.add(inputString);
                        }
                    }
                }
                // Check that the input threshold supports the execution of the module
                if (matches >= instance.getInputThreshold()) {
                    //instance.execute(input);
                }
            }
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}