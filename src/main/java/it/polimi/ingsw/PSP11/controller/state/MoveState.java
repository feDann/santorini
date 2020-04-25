package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.MoveRequest;
import it.polimi.ingsw.PSP11.messages.MoveResponse;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class MoveState implements GameState {

    private Game game;
    private Worker chosenWorker;
    private int chosenWorkerId;
    private ArrayList<Point> possibleMoves = new ArrayList<Point>();

    public MoveState(Game game, int chosenWorker) {
        this.game = game;
        this.chosenWorkerId = chosenWorker;
        this.chosenWorker = game.getCurrentPlayer().getWorkers().get(chosenWorker);
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
        possibleMoves = game.move(chosenWorker);
    }

    @Override
    public void checkLose() {

    }

    @Override
    public void applyMove(Point point) {
        game.applyMove(point, chosenWorker);
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
        moveWorker();
        return new MoveRequest(possibleMoves);
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        applyMove(((MoveResponse) message).getPoint());
        return new BuildState(game, chosenWorkerId);
    }
}
