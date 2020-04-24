package it.polimi.ingsw.PSP11.messages;

public class BuildBeforeMoveRequest extends SimpleMessage {
    public BuildBeforeMoveRequest() {
        super("Would you like to activate your hero power, and build before moving a worker? y / n\n>>>");
    }
}
