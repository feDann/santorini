package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.BuildBeforeMoveRequest;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SelectWorkerRequest;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class StartTurnState implements GameState{

    private Game game;
    private boolean canBuildBeforeMove;
    private boolean isNew;

    public StartTurnState(Game theGame) {
        this.game = theGame;
        game.getCurrentPlayer().getPlayerTurn().startTurn();
        this.canBuildBeforeMove = game.getCurrentPlayer().getPlayerTurn().getSharedTurn().isCanBuildBeforeMove();
        this.isNew = true;
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
    public void applyMove() {

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
        if (isNew) {
            isNew = false;
            //TODO
            //clone the workers
            return new SelectWorkerRequest(game.getCurrentPlayer().getWorkers());
        }

        if (canBuildBeforeMove) {
            //TODO
            //message if he wants to move
        }

        //nothing
        return null;
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        //TODO
        //save worker chosen

        //MoveState or BuildState
        return null;
    }
}
