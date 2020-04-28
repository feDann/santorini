package it.polimi.ingsw.PSP11.messages;


public class LoseMessage extends SimpleMessage{

    public LoseMessage() {
        super("ha! hai perso, git gud!");
    }

    public LoseMessage(String player){
        super(player + " ha perso, F in the chat!");
    }
}
