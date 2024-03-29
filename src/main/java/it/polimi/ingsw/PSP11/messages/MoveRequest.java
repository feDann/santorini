package it.polimi.ingsw.PSP11.messages;

import java.awt.*;
import java.util.ArrayList;

public class MoveRequest extends SimpleMessage{

    private ArrayList<Point> possibleMoves;

    /**
     * message with all the possible move location available for the player to choose
     * @param possibleMoves ArrayList of all the possible position in which the player could move
     */
    public MoveRequest(ArrayList<Point> possibleMoves) {
        super("");
        this.possibleMoves = possibleMoves;
        String formattedMessage = "Select the position you would like to move to (x,y)\n";
        for (Point p : possibleMoves){
            formattedMessage = formattedMessage.concat("(" + (p.x+1) + "," + (p.y+1) + ")  ");
        }
        formattedMessage = formattedMessage.concat("\n>>>");
        setMessage(formattedMessage);
    }

    public ArrayList<Point> getPossibleMoves() {
        return possibleMoves;
    }
}
