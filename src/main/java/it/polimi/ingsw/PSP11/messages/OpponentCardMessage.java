package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;

public class OpponentCardMessage extends SimpleMessage {

    private Card opponentCard;
    private String opponent;

    public OpponentCardMessage(String theMessage ,Card card , String opponent) {
        super(theMessage);
        this.opponentCard = card;
    }

    public Card getOpponentCard() {
        return opponentCard;
    }

    public String getOpponent() {
        return opponent;
    }
}
