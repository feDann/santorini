package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;

public class PlaceWorkerRequest extends SimpleMessage{

    private Board clonedBoard;

    public PlaceWorkerRequest(Board board) {
        super("");
        clonedBoard = board;
        //String formattedMessage = clonedBoard.printBoard();
        String formattedMessage = "";
        formattedMessage = formattedMessage.concat("\nPlace your worker (x,y)\n>>>");
        setMessage(formattedMessage);
    }

    public Board getClonedBoard() {
        return clonedBoard;
    }
}
