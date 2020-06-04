package it.polimi.ingsw.PSP11.messages;

public class ConnectionMessage extends SimpleMessage{
    /**
     * message used to ask the number of player of the match
     */
    public ConnectionMessage() {
        super("How many players? (2 or 3)" +"\n>>>");
    }

}
