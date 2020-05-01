package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public interface GameState {

    public void selectGameGods(ArrayList<Integer> ids);

    public void selectPlayerGod(int index);

    public void placeWorker(Point point);

    public void startTurn();

    public void selectWorker();

    public void moveWorker();

    public boolean checkLose();

    public void applyMove(Point point);

    public boolean checkWin();

    public void workerBuild();

    public void applyBuild(Point point, boolean forceBuildDome);

    public void endTurn();

    public Message stateMessage();

    public GameState execute(Message message, VirtualView virtualView);
}