package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class StartTurnState implements GameState{

    private Game game;
    private boolean canBuildBeforeMove;
    private boolean isNew;
    private int chosenWorker;

    public StartTurnState(Game theGame) {
        this.game = theGame;
        game.getCurrentPlayer().getPlayerTurn().startTurn();
        this.canBuildBeforeMove = game.getCurrentPlayer().getPlayerTurn().getSharedTurn().isCanBuildBeforeMove();
        this.isNew = true;
        this.chosenWorker = -1;
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
            ArrayList<Worker> workers = new ArrayList<>();
            for (Worker worker : game.getCurrentPlayer().getWorkers()){
                workers.add(worker.workerClone());
            }
            return new SelectWorkerRequest(workers);
        }
        if (canBuildBeforeMove) {
            return new BuildBeforeMoveRequest();
        }
        return null;
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        //TODO
        if (message instanceof SelectWorkerResponse){
            this.chosenWorker = ((SelectWorkerResponse) message).getChosenWorker();
            if(canBuildBeforeMove){
                return this;
            }
            return new MoveState(game, chosenWorker);
        }
        else{
            if (((BuildBeforeMoveResponse) message).isCanBuildBefore()){
                return new BuildState(game, chosenWorker);
            }
            else{
                return new MoveState(game, chosenWorker);
            }
        }

    }
}
