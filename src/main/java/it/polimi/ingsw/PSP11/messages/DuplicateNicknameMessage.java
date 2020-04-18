package it.polimi.ingsw.PSP11.messages;

public class DuplicateNicknameMessage extends SimpleMessage {

    public DuplicateNicknameMessage() {
        super("This nickname is already in use, please choose another one\n>>>");
    }
}
