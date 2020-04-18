package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;

import java.util.ArrayList;

public interface GameState {

    public void selectGameGods(ArrayList<Integer> ids);

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

    public Message stateMessage();

    public GameState execute(Message message);
}