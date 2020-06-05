package it.polimi.ingsw.PSP11.messages;

public class WaitMessage extends SimpleMessage {
    /**
     * message used to inform the player in the lobby that a player or more is still missing
     */
    public WaitMessage() {
        super("Wait for other player...\n");
    }
}
