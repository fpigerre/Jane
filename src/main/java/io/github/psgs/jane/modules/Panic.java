package io.github.psgs.jane.modules;

public class Panic extends Module {

    public Panic() {
        super("Panic", "A module that initiates a panic sequence", 10);
    }

    /**
     * Executes a panic sequence
     */
    public static void execute() {
        // TODO Add deletion of general cached files
        // TODO Add initiation of computer wipe sequence
        // TODO Add initiation of panic beacon
    }
}
