package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;

public class BoardUpdate extends SimpleMessage {

    private Board board;

    /**
     * print a formatted version of the board
     * @param board to print
     */
    public BoardUpdate(Board board){
        super(board.printBoard());
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
