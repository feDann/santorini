package it.polimi.ingsw.PSP11.model;


import java.util.ArrayList;

/**
 * Class for the players
 */
public class Player {

    private String nickname;
    private boolean eliminated;
    private Turn playerTurn;
    private Card god;
    private ArrayList<Worker> workers;


    /**
     * Class constructor
     * @param chosenName nickname chosen by the player
     */
    public Player (String chosenName) {
        this.nickname = chosenName;
        this.eliminated = false;
        this.playerTurn = null;
        this.god = null;
        this.workers = new ArrayList<>();
    }


    /**
     * Eliminates the player by setting eliminated parameter to true
     */
    public void eliminate() {
        this.eliminated = true;
    }


    public void setGod(Card chosenGod) {
        this.god = chosenGod;
    }


    public void setPlayerTurn (Turn sharedTurn) {
        god.setTurn(sharedTurn);
        this.playerTurn = god.getTurn();
    }


    public Worker chooseWorker (int workerIndex) {
        return workers.get(workerIndex);
    }



}
