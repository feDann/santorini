package it.polimi.ingsw.PSP11.messages;

public class OpponentMessage extends SimpleMessage {

    public OpponentMessage(String opponent1) {
        super("Your opponent is: " + opponent1 + "\n");
    }

    public OpponentMessage(String opponent1, String opponent2){
        super("Your opponents are: " + opponent1 + ", " + opponent2 + "\n");
    }
}
