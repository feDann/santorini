package it.polimi.ingsw.PSP11.messages;


public class LoseMessage extends SimpleMessage{

    public LoseMessage() {
        super("ha! hai perso, git gud!\n");
    }

    public LoseMessage(String player){
        super(player + " ha perso, F in the chat!\n");
    }
}
