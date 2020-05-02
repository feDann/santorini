package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.Worker;

import java.awt.*;
import java.util.ArrayList;

public class SelectWorkerRequest extends SimpleMessage {

    private ArrayList<Worker> availableWorkers = new ArrayList<>();

    public SelectWorkerRequest(ArrayList<Worker> workers, Card playerGod) {
        super("");
        String formattedMessage = "\n\n" + Color.GREEN.getEscape()+


                "████████╗██╗   ██╗██████╗ ███╗   ██╗    ███████╗████████╗ █████╗ ██████╗ ████████╗███████╗██████╗ ██╗\n" +
                "╚══██╔══╝██║   ██║██╔══██╗████╗  ██║    ██╔════╝╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝██╔════╝██╔══██╗██║\n" +
                "   ██║   ██║   ██║██████╔╝██╔██╗ ██║    ███████╗   ██║   ███████║██████╔╝   ██║   █████╗  ██║  ██║██║\n" +
                "   ██║   ██║   ██║██╔══██╗██║╚██╗██║    ╚════██║   ██║   ██╔══██║██╔══██╗   ██║   ██╔══╝  ██║  ██║╚═╝\n" +
                "   ██║   ╚██████╔╝██║  ██║██║ ╚████║    ███████║   ██║   ██║  ██║██║  ██║   ██║   ███████╗██████╔╝██╗\n" +
                "   ╚═╝    ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝    ╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝ ╚═╝\n" +
                "                                                                                                     \n\n"

                + Color.RESET;


        formattedMessage = formattedMessage.concat("\nYour god is: " +Color.RED.getEscape()+ playerGod.getName()+Color.RESET +
                                                   "\nDescription:\n     "+ playerGod.getDescription());
        formattedMessage = formattedMessage.concat("\n\n\nSelect the worker you want to move (1 or 2):");


        for(Worker worker : workers){
            availableWorkers.add(worker);
            Point position = worker.getPosition();
            formattedMessage += "\nWorker " + (worker.getId()+1) + " : " + "(" + (position.x + 1) + "," + (position.y + 1) + ")";
        }



        formattedMessage += "\n>>>";
        setMessage(formattedMessage);
    }

    public ArrayList<Worker> getAvailableWorkers() {
        return availableWorkers;
    }
}
