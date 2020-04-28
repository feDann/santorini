package it.polimi.ingsw.PSP11.messages;

import java.awt.*;
import java.util.ArrayList;

public class BuildRequest extends SimpleMessage{

    private ArrayList<Point> possibleBuilds;

    public BuildRequest(ArrayList<Point> possibleBuilds) {
        super("");
        this.possibleBuilds = possibleBuilds;
        String formattedMessage = "Select the position you would like to build (x,y)\n";
        for (Point p : possibleBuilds){
            formattedMessage = formattedMessage.concat("(" + (p.x+1) + "," + (p.y+1) + ")  ");
        }
        formattedMessage = formattedMessage.concat("\n>>>");
        setMessage(formattedMessage);
    }

    public ArrayList<Point> getPossibleBuilds() {
        return possibleBuilds;
    }
}
