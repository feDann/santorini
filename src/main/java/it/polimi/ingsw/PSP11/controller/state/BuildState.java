package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class BuildState implements GameState {

    public BuildState(Game game, int chosenWorker) {
    }

    @Override
    public void selectGameGods(ArrayList<Integer> ids) {

    }

    @Override
    public void selectPlayerGod(int index) {

    }

    @Override
    public void placeWorker(Point point) {

    }

    @Override
    public void startTurn() {

    }

    @Override
    public void selectWorker() {

    }

    @Override
    public void moveWorker() {

    }

    @Override
    public void checkLose() {

    }

    @Override
    public void applyMove(Point point) {

    }

    @Override
    public void checkWin() {

    }

    @Override
    public void workerBuild() {

    }

    @Override
    public void applyBuild() {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public Message stateMessage() {
        return new SimpleMessage("iniziamo a costruire!");
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        return null;
    }
}
