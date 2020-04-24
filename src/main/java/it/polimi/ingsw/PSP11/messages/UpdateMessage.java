package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;

public class UpdateMessage {
    private Board board;
    private String playerName;
    private Message updateMessage;

    public UpdateMessage(Board board, String player, Message message) {
        this.board = board;
        this.playerName = player;
        this.updateMessage = message;
    }

    public Board getBoard() {
        return board;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Message getUpdateMessage() {
        return updateMessage;
    }

}
