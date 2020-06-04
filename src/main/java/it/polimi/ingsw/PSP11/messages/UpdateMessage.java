package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

public class UpdateMessage {
    private Board board;
    private PlayerInfo player;
    private Message updateMessage;

    /**
     * message used to update all the player on the state of the game
     * @param board the game board with all the buildings
     * @param player the current player
     * @param message the update message
     */
    public UpdateMessage(Board board, PlayerInfo player, Message message) {
        this.board = board;
        this.player = player;
        this.updateMessage = message;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerInfo getPlayer() {
        return player;
    }

    public Message getUpdateMessage() {
        return updateMessage;
    }

}
