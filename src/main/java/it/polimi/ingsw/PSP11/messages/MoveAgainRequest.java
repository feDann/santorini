package it.polimi.ingsw.PSP11.messages;

public class MoveAgainRequest extends SimpleMessage{
    public MoveAgainRequest() {
        super("Would you like to activate your hero power, and move twice this turn? y / n\n>>>");
    }
}
