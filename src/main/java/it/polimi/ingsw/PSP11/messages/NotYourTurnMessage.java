package it.polimi.ingsw.PSP11.messages;

public class NotYourTurnMessage extends SimpleMessage {

    public NotYourTurnMessage(String playerTurn) {
        super("It's not your turn! It's " +playerTurn + "'s turn.");
    }

}
