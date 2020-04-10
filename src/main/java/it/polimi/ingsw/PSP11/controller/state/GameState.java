package it.polimi.ingsw.PSP11.controller.state;

public interface GameState {

    /**
     * sets the god chosen by the player
     * @param index of the selected god
     */
    public void selectPlayerGod(int index);

    public void placeWorker();

    public void startTurn();

    public void selectWorker();

    public void moveWorker();

    public void checkLose();

    public void applyMove();

    public void checkWin();

    public void workerBuild();

    public void applyBuild();

    public void endTurn();

    public void execute();
}