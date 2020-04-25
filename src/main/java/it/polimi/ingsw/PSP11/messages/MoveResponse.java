package it.polimi.ingsw.PSP11.messages;

import java.awt.*;

public class MoveResponse extends Message{

    Point point;

    public MoveResponse(Point point) {
        super("The player chose to move in " + point.toString());
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }
}
