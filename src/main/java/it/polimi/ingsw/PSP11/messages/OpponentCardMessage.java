package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;

public class OpponentCardMessage extends SimpleMessage {

    private Card opponentCard;
    private String opponent;

    /**
     * message used to notify the other player of the god chosen by the current player
     * @param theMessage text
     * @param card chosen god
     * @param opponent the name of the current player
     */
    public OpponentCardMessage(String theMessage ,Card card , String opponent) {
        super(theMessage);
        this.opponentCard = card;
        this.opponent = opponent;
    }

    public Card getOpponentCard() {
        return opponentCard;
    }

    public String getOpponent() {
        return opponent;
    }
}
