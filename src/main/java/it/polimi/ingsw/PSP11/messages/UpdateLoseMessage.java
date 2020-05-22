package it.polimi.ingsw.PSP11.messages;

public class UpdateLoseMessage extends SimpleMessage {

    public UpdateLoseMessage(String player){
        super(player + " ha perso, F in the chat!\n");
    }
}
