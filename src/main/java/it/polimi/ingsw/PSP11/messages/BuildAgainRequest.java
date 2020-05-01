package it.polimi.ingsw.PSP11.messages;

public class BuildAgainRequest extends SimpleMessage{
    public BuildAgainRequest() {
        super("Would you like to activate your hero power, and build twice this turn? y / n\n>>>");
    }
}
