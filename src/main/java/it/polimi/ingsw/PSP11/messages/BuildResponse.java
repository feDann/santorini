package it.polimi.ingsw.PSP11.messages;

import java.awt.*;

public class BuildResponse extends Message{

    Point point;

    /**
     * message with the position chosen by the player to build a block
     * @param point chosen by the player to build
     */
    public BuildResponse(Point point) {
        super("The player chose to built in " + point.toString());
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
