package it.polimi.ingsw.PSP11.messages;

public class SelectWorkerResponse extends Message{

    private int chosenWorker;

    /**
     * message used to notify the server of the worker chosen by the player
     * @param chosenWorker the id of the chosen worker
     */
    public SelectWorkerResponse(int chosenWorker) {
        super("The chosen worker is: " + chosenWorker);
        this.chosenWorker = chosenWorker;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }
}
