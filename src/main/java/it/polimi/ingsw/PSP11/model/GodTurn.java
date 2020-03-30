package it.polimi.ingsw.PSP11.model;

public abstract class GodTurn extends Turn{

    private Turn sharedTurn;

    public void setSharedTurn(Turn sharedTurn) {
        this.sharedTurn = sharedTurn;
    }
}
