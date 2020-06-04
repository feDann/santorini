package it.polimi.ingsw.PSP11.messages;

public class Ping extends Message{

    /**
     * ping message used to verify the connection between the client and the server
     */
    public Ping() {
        super("ping");
    }
}
