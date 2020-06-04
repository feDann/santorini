package it.polimi.ingsw.PSP11.messages;

public class DuplicateNicknameMessage extends SimpleMessage {

    /**
     * error message that alert the player that the nickname chosen is already taken
     */
    public DuplicateNicknameMessage() {
        super("This nickname is already in use, please choose another one\n>>>");
    }
}
