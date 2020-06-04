package it.polimi.ingsw.PSP11.messages;

public class NotYourTurnMessage extends SimpleMessage {
    /**
     * message used in case a player write something while it's not his turn
     * @param playerTurn the current player
     */
    public NotYourTurnMessage(String playerTurn) {
        super("It's not your turn! It's " +playerTurn + "'s turn.\n");
    }

}
