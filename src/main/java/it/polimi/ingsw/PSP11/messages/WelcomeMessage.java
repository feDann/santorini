package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;

public class WelcomeMessage extends SimpleMessage {
    /**
     * message used to greet the player when they open the game
     */
    public WelcomeMessage () {
        super(Color.GREEN.getEscape() +
                "███████╗ █████╗ ███╗   ██╗████████╗ ██████╗ ██████╗ ██╗███╗   ██╗██╗\n" +
                "██╔════╝██╔══██╗████╗  ██║╚══██╔══╝██╔═══██╗██╔══██╗██║████╗  ██║██║\n" +
                "███████╗███████║██╔██╗ ██║   ██║   ██║   ██║██████╔╝██║██╔██╗ ██║██║\n" +
                "╚════██║██╔══██║██║╚██╗██║   ██║   ██║   ██║██╔══██╗██║██║╚██╗██║██║\n" +
                "███████║██║  ██║██║ ╚████║   ██║   ╚██████╔╝██║  ██║██║██║ ╚████║██║\n" +
                "╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚═╝" +
                "                                                                                 " + Color.RESET + "\nChose your nickname: \n>>>");
    }

}
