package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;

public class UpdateMessage {
    private Board board;

    public UpdateMessage(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
