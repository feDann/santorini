package it.polimi.ingsw.PSP11.messages;

import java.awt.*;
import java.util.ArrayList;

public class PlaceWorkerResponse extends Message{

    Point point;

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
