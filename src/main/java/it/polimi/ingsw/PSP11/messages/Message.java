package it.polimi.ingsw.PSP11.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private String message;

    public Message (String theMessage) {
        this.message = theMessage;
    }

    public String getMessage() {
        return message;
    }

}
