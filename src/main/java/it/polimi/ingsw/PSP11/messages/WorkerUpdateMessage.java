package it.polimi.ingsw.PSP11.messages;

import java.awt.*;

public class WorkerUpdateMessage extends SimpleMessage{

    public WorkerUpdateMessage(String nickname, Point point) {
        super(nickname + " has placed his worker in (" + (point.x + 1) + "," + (point.y + 1) + ")\n");
    }

}
