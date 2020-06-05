package it.polimi.ingsw.PSP11.messages;

public class MoveAgainRequest extends SimpleMessage{
    /**
     * request message for activating Artemis power
     */
    public MoveAgainRequest() {
        super("Would you like to activate your hero power, and move twice this turn? y / n\n>>>");
    }
}
