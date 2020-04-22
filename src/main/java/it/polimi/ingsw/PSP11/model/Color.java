package it.polimi.ingsw.PSP11.model;

import java.io.Serializable;

/**
 * Class for the workers' color
 */
public enum Color implements Serializable {
    BLUE("\u001B[34m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BROWN("\u001B[33m"),
    PURPLE("\u001B[35m");

    public static final String RESET = "\u001B[0m";

    private String escape;

    Color (String escape){
        this.escape = escape;
    }

    public String getEscape() {
        return escape;
    }
}
