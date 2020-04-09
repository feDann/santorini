package it.polimi.ingsw.PSP11.model;

/**
 * Class for the workers' color
 */
public enum Color {
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m");

    static final String RESET = "\u001B[0m";

    private String escape;

    Color (String escape){
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }
}
