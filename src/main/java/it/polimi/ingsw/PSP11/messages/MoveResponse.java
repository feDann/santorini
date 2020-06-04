package it.polimi.ingsw.PSP11.messages;

import java.awt.*;

public class MoveResponse extends Message{

    Point point;
    /**
     * message with the position chosen by the player to move to
     * @param point chosen by the player to move to
     */
    public MoveResponse(Point point) {
        super("The player chose to move in " + point.toString());
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
