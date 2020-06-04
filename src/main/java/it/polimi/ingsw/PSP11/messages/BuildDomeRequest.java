package it.polimi.ingsw.PSP11.messages;

public class BuildDomeRequest extends SimpleMessage{
    /**
     * request for activating Atlas and Hephaestus power
     */
    public BuildDomeRequest() {
        super("Would you like to activate your hero power, and build a dome instead of a block? y / n\n>>>");
    }
}
