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

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public int getPriority() {
        return this.priority;
    }

    public int getInputThreshold() {
        return this.inputThreshold;
    }

    public List<String> getAcceptedInput() {
        return this.acceptedInput;
    }
}
