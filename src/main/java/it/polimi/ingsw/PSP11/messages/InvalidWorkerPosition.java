package it.polimi.ingsw.PSP11.messages;

public class InvalidWorkerPosition extends SimpleMessage{


    public InvalidWorkerPosition() {
        super("This position is already taken by another worker!\nPlease insert new position\n>>>");
    }


}
