package io.github.psgs.jane.modules;

import java.util.ArrayList;
import java.util.List;

public class Module {

    String name;
    String description;
    int priority;
    int inputThreshold;
    List<String> acceptedInput = new ArrayList<String>();

    public Module(String name, String description, int priority, int inputThreshold) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.inputThreshold = inputThreshold;
    }

    /**
     * Gets a module's name
     * @return The module's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets a module's description
     * @return The module's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets a module's priority over other modules and system tasks
     * @return The module's priority level
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Gets the amount of matched criteria needed to initiate a module
     * @return The module's input threshold
     */
    public int getInputThreshold() {
        return this.inputThreshold;
    }

    /**
     * Gets input strings that count towards the initiation of a particular module
     * @return The module's accepted input
     */
    public List<String> getAcceptedInput() {
        return this.acceptedInput;
    }
}
