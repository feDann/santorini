package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;

public class StartGameMessage extends SimpleMessage {
    /**
     * message used to greet the player when they start a new game
     */
    public StartGameMessage() {
        super(Color.GREEN.getEscape() +""+
                "██████╗  █████╗ ███╗   ███╗███████╗    ███████╗████████╗ █████╗ ██████╗ ████████╗███████╗██████╗ ██╗\n" +
                "██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔════╝╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██║\n" +
                "██║  ███╗███████║██╔████╔██║█████╗      ███████╗   ██║   ███████║██████╔╝   ██║   █████╗  ██║  ██║██║\n" +
                "██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ╚════██║   ██║   ██╔══██║██╔══██╗   ██║   ██╔══╝  ██║  ██║╚═╝\n" +
                "╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ███████║   ██║   ██║  ██║██║  ██║   ██║   ███████╗██████╔╝██╗\n"+Color.RESET);
    }
}
