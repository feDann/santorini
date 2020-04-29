package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class BuildState implements GameState {

    private Game game;
    private int chosenWorkerID;
    private ArrayList<Point> possibleBuilds = new ArrayList<Point>();
    private boolean looser;

    public BuildState(Game game, int chosenWorker) {
        this.game = game;
        this.chosenWorkerID = chosenWorker;
        this.looser = false;
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
    public boolean checkLose() {
        if (possibleBuilds.isEmpty()){
            game.setThereIsALooser(true);
            return true;
        }
        return false;
    }

    @Override
    public void applyMove(Point point) {

    }

    @Override
    public void checkWin() {

    }

    @Override
    public void workerBuild() {
        possibleBuilds = game.build(chosenWorkerID);
    }

    @Override
    public void applyBuild(Point point) {
        //TODO
        //bool per build dome
        game.applyBuild(point, chosenWorkerID, false);
    }

    @Override
    public void endTurn() {

    }

    @Override
    public Message stateMessage() {
        workerBuild();
        if (checkLose()){
            return new LoseMessage();
        }
        return new BuildRequest(possibleBuilds);
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        applyBuild(((BuildResponse) message).getPoint());
        virtualView.sendMessage(new EndTurnMessage());
        game.nextPlayer();
        return new StartTurnState(game);
    }
}
