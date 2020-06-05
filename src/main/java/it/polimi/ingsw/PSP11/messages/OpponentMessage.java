package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

import java.util.ArrayList;

public class OpponentMessage extends SimpleMessage {

    private Color color;
    private ArrayList<PlayerInfo> opponents = new ArrayList<>();

    /**
     * message used to communicate the name of the opponent to the current player at the start of the game
     * also used to print the color of the current player
     * @param opponent1 name of the opponent
     * @param color of the current player
     */
    public OpponentMessage(PlayerInfo opponent1, Color color) {
        super("\n" +
                "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n"+
                "Your opponent is: " + opponent1.getColor().getEscape()+ opponent1.getName() + Color.RESET +
                "\nYour color is " +  color.getEscape() + color.toString().toLowerCase() + Color.RESET +
                "\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n");
        opponents.add(opponent1);
        this.color = color;
    }
    /**
     * message used to communicate the names of the opponents to the current player at the start of the game
     * also used to print the color of the current player
     * @param opponent1 name of the first opponent
     * @param opponent2 name of the second opponent
     * @param color of the current player
     */
    public OpponentMessage(PlayerInfo opponent1, PlayerInfo opponent2, Color color){
        super("\n\n" +
                "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"+
                "\nYour opponents are: " + opponent1.getColor().getEscape() + opponent1.getName() +Color.RESET+ ", " + opponent2.getColor().getEscape() + opponent2.getName() +Color.RESET +
                "\nYour color is " + color.getEscape() + color.toString().toLowerCase() + Color.RESET +
                "\n▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n");
        this.color = color;
        opponents.add(opponent1);
        opponents.add(opponent2);
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<PlayerInfo> getOpponents() {
        return opponents;
    }
}
