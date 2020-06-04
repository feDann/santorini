package it.polimi.ingsw.PSP11.messages;

public class InvalidWorkerPosition extends SimpleMessage{

    /**
     * error message sent to the current player if he chose a position already occupied by another worker
     */
    public InvalidWorkerPosition() {
        super("This position is already taken by another worker!\nPlease insert new position\n>>>");
    }


}
