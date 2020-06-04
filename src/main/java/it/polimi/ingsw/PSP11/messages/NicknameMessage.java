package it.polimi.ingsw.PSP11.messages;

public class NicknameMessage extends Message{

    private boolean firstPlayer;

    /**
     * message used to communicate the player name to the server
     * @param theMessage the nickname chosen by the player
     */
    public NicknameMessage(String theMessage) {
        super(theMessage);
    }

}