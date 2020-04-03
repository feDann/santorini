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


    /**
     * Setter method for god attribute
     * @param chosenGod god card chosen by the player
     */
    public void setGod(Card chosenGod) {
        this.god = chosenGod;
    }


    /**
     * Setter method for playerTurn attribute
     * @param sharedTurn shared turn to decorate
     */
    public void setPlayerTurn (StandardTurn sharedTurn) {
        god.setTurn(sharedTurn);
        this.playerTurn = god.getGodTurnDecorator();
    }


    /**
     * Method to select a player worker
     * @param workerIndex index of the worker to return
     * @return worker at the specified index
     */
    public Worker chooseWorker (int workerIndex) {
        return workers.get(workerIndex);
    }


    /**
     * Method to check if the player is eliminated
     * @return true if player is eliminated
     */
    public boolean isEliminated() {
        return this.eliminated;
    }


    /**
     * Method to add a worker to the player
     * @param workerToAdd the worker to add
     */
    public void addWorker (Worker workerToAdd) {
        this.workers.add(workerToAdd);
    }



}
