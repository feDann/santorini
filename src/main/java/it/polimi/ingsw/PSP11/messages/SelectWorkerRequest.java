package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class SelectWorkerRequest extends SimpleMessage {

    public SelectWorkerRequest(ArrayList<Worker> workers) {
        super("");
        String formattedMessage = "\n\nYOUR TURN STARTED!\nSelect the worker you want to move (1 or 2):";
        for (int i = 0 ; i < workers.size() ; i++) {
            Point position = workers.get(i).getPosition();
            formattedMessage += "\nWorker " + (i+1) + " : " + "(" + (position.x + 1) + "," + (position.y + 1) + ")";
        }
        formattedMessage += "\n>>>";
        setMessage(formattedMessage);
    }
}
