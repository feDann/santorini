package it.polimi.ingsw.PSP11.messages;

public class WinMessage extends SimpleMessage{
    public WinMessage() {
        super("You won this game, ggwp\n");
    }

    public WinMessage(String winner){
        super("The winner is: " + winner + "\n");
    }
}
