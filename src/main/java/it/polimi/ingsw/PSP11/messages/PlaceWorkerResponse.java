package it.polimi.ingsw.PSP11.messages;

import java.awt.*;

public class PlaceWorkerResponse extends Message{

    Point point;

    /**
     * message with the position chosen by the player to place their worker at the start of the game
     * @param point chosen by the player to place the worker
     */
    public PlaceWorkerResponse(Point point) {
        super("");
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
