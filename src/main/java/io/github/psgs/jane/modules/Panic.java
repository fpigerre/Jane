package io.github.psgs.jane.modules;

import java.util.ArrayList;
import java.util.List;

public class Panic extends Module {

    public static List<String> acceptedInput = new ArrayList<String>();

    public Panic() {
        super("Panic", "A module that initiates a panic sequence", 10, 3);
        acceptedInput.add("panic");
    }

    public static void execute() {
        // TODO Add deletion of general cached files
        // TODO Add initiation of computer wipe sequence
        // TODO Add initiation of panic beacon
    }

    @Override
    public List<String> getAcceptedInput() {
        return acceptedInput;
    }
}
