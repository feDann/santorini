package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;

public class PlaceWorkerRequest extends SimpleMessage{

    private Board clonedBoard;

    /**
     * message sent to the player asking to place his worker
     */
    public PlaceWorkerRequest() {
        super("");
        String formattedMessage = "";
        formattedMessage = formattedMessage.concat("\nPlace your worker (x,y)\n>>>");
        setMessage(formattedMessage);
    }

}
