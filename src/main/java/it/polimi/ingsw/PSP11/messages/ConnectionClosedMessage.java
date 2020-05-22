package it.polimi.ingsw.PSP11.messages;

public class ConnectionClosedMessage extends SimpleMessage {
    public ConnectionClosedMessage(String message) {
        super(message + "\nConnection closed!");
    }
}
