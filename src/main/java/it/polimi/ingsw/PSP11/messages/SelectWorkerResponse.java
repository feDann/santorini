package it.polimi.ingsw.PSP11.messages;

public class SelectWorkerResponse extends Message{

    private int chosenWorker;

    public SelectWorkerResponse(int chosenWorker) {
        super("The chosen worker is: " + chosenWorker);
        this.chosenWorker = chosenWorker;
    }

    public int getChosenWorker() {
        return chosenWorker;
    }
}
