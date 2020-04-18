package it.polimi.ingsw.PSP11.messages;

import java.util.ArrayList;

public class SelectGameGodResponse extends Message{

    ArrayList<Integer> idOfChosenGods;

    public SelectGameGodResponse(ArrayList<Integer> idOfChosenGods) {
        super("id of the chosen cards: ");
        this.idOfChosenGods = idOfChosenGods;
    }

    public ArrayList<Integer> getIdOfChosenGods() {
        return idOfChosenGods;
    }
}
