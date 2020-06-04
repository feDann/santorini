package it.polimi.ingsw.PSP11.messages;

public class ConnectionClosedMessage extends SimpleMessage {

    /**
     * message to notify all the player that the connection is closed
     * @param message is the cause for the disconnection
     */
    public ConnectionClosedMessage(String message) {
        super(message + "\nConnection closed!");
    }
}
