package it.polimi.ingsw.PSP11.messages;

import java.util.ArrayList;

public class SelectGameGodResponse extends Message{

    ArrayList<Integer> idOfChosenGods;

    /**
     * message with the id of the gods chosen for the game
     * @param idOfChosenGods arrayList of the id of the chosen gods
     */
    public SelectGameGodResponse(ArrayList<Integer> idOfChosenGods) {
        super("id of the chosen cards: ");
        this.idOfChosenGods = idOfChosenGods;
    }

    public ArrayList<Integer> getIdOfChosenGods() {
        return idOfChosenGods;
    }
}
