package io.github.psgs.jane.modules;

public class Module {

    String name;
    String description;
    int priority;

    public Module(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    /**
     * Gets a module's name
     *
     * @return The module's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets a module's description
     *
     * @return The module's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets a module's priority over other modules and system tasks
     *
     * @return The module's priority level
     */
    public int getPriority() {
        return this.priority;
    }
}
