package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;

public class EndTurnMessage extends SimpleMessage{

    /**
     * message sent to all the player to inform them that the current player has just finished his turn
     */
    public EndTurnMessage() {
        super("\n\n\n"+ Color.BLUE.getEscape()+
                "████████╗██╗   ██╗██████╗ ███╗   ██╗    ███████╗███╗   ██╗██████╗ ███████╗██████╗ ██╗\n" +
                "╚══██╔══╝██║   ██║██╔══██╗████╗  ██║    ██╔════╝████╗  ██║██╔══██╗██╔════╝██╔══██╗██║\n" +
                "   ██║   ██║   ██║██████╔╝██╔██╗ ██║    █████╗  ██╔██╗ ██║██║  ██║█████╗  ██║  ██║██║\n" +
                "   ██║   ██║   ██║██╔══██╗██║╚██╗██║    ██╔══╝  ██║╚██╗██║██║  ██║██╔══╝  ██║  ██║╚═╝\n" +
                "   ██║   ╚██████╔╝██║  ██║██║ ╚████║    ███████╗██║ ╚████║██████╔╝███████╗██████╔╝██╗\n" +
                "   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝    ╚══════╝╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═════╝ ╚═╝\n" +
                "                                                                                     \n" +Color.RESET);
    }

}
