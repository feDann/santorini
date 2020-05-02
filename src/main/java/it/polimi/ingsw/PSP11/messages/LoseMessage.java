package it.polimi.ingsw.PSP11.messages;


import it.polimi.ingsw.PSP11.model.Color;

public class LoseMessage extends SimpleMessage{

    public LoseMessage() {
        super(Color.RED.getEscape() +
                "\n\n▓██   ██▓ ▒█████   █    ██     ██▓     ▒█████    ██████ ▄▄▄█████▓\n" +
                " ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██▒    ▒██▒  ██▒▒██    ▒ ▓  ██▒ ▓▒\n" +
                "  ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██░    ▒██░  ██▒░ ▓██▄   ▒ ▓██░ ▒░\n" +
                "  ░ ▐██▓░▒██   ██░▓▓█  ░██░   ▒██░    ▒██   ██░  ▒   ██▒░ ▓██▓ ░ \n" +
                "  ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░██████▒░ ████▓▒░▒██████▒▒  ▒██▒ ░ \n" +
                "   ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒    ░ ▒░▓  ░░ ▒░▒░▒░ ▒ ▒▓▒ ▒ ░  ▒ ░░   \n" +
                " ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░    ░ ░ ▒  ░  ░ ▒ ▒░ ░ ░▒  ░ ░    ░    \n" +
                " ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░      ░ ░   ░ ░ ░ ▒  ░  ░  ░    ░      \n" +
                " ░ ░         ░ ░     ░            ░  ░    ░ ░        ░           \n" +
                " ░ ░                                                             \n"+ Color.RESET);
    }

    public LoseMessage(String player){
        super(player + " ha perso, F in the chat!\n");
    }
}
