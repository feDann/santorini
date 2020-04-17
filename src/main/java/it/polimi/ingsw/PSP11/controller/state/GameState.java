package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;

public interface GameState {

    public void selectGameGods();

    public String selectPlayerGod(int index);

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

    public String execute(Message message);
}