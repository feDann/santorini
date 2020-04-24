package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;

public class OpponentMessage extends SimpleMessage {

    private Color color;

    public OpponentMessage(String opponent1, Color color) {
        super("Your opponent is: " + opponent1 + "\nYour color is " +  color.getEscape() + color.toString().toLowerCase() + Color.RESET + "\n");
        this.color = color;
    }

    public OpponentMessage(String opponent1, String opponent2, Color color){
        super("Your opponents are: " + opponent1 + ", " + opponent2 + "\nYour color is " + color.getEscape() + color.toString().toLowerCase() + Color.RESET + "\n");
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
