package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;

public class WelcomeMessage extends SimpleMessage {

    public WelcomeMessage () {
        super(Color.GREEN.getEscape() + " _______  _______  _       _________ _______  _______ _________ _       _________\n" +
                "(  ____ \\(  ___  )( (    /|\\__   __/(  ___  )(  ____ )\\__   __/( (    /|\\__   __/\n" +
                "| (    \\/| (   ) ||  \\  ( |   ) (   | (   ) || (    )|   ) (   |  \\  ( |   ) (   \n" +
                "| (_____ | (___) ||   \\ | |   | |   | |   | || (____)|   | |   |   \\ | |   | |   \n" +
                "(_____  )|  ___  || (\\ \\) |   | |   | |   | ||     __)   | |   | (\\ \\) |   | |   \n" +
                "      ) || (   ) || | \\   |   | |   | |   | || (\\ (      | |   | | \\   |   | |   \n" +
                "/\\____) || )   ( || )  \\  |   | |   | (___) || ) \\ \\_____) (___| )  \\  |___) (___\n" +
                "\\_______)|/     \\||/    )_)   )_(   (_______)|/   \\__/\\_______/|/    )_)\\_______/\n" +
                "                                                                                 " + Color.RESET + "\nChose your nickname: \n>>>");
    }

}