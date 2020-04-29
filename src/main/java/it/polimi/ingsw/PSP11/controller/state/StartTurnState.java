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
    private int chosenWorkerId;
    private Worker chosenWorker;
    private ArrayList<Worker> movableWorkers = new ArrayList<>();

    public StartTurnState(Game theGame) {
        this.game = theGame;
        game.startTurn();
        this.canBuildBeforeMove = game.getCurrentPlayer().getPlayerTurn().getSharedTurn().isCanBuildBeforeMove();
        this.isNew = true;
        this.chosenWorkerId = -1;
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
        for (Worker worker : game.getCurrentPlayer().getWorkers()){
            if(! game.move(worker).isEmpty()) {
                movableWorkers.add(worker.workerClone());
            }
        }
        if(movableWorkers.isEmpty()){
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

    }

    @Override
    public void applyBuild(Point point) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public Message stateMessage() {
        if (isNew) {
            isNew = false;
            if (checkLose()){
                return new LoseMessage();
            }
            return new SelectWorkerRequest(movableWorkers);
        }
        if (canBuildBeforeMove) {
            return new BuildBeforeMoveRequest();
        }
        return null;
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        if (message instanceof SelectWorkerResponse){
            this.chosenWorkerId = ((SelectWorkerResponse) message).getChosenWorker();
            for(Worker worker : game.getCurrentPlayer().getWorkers()){
                if(worker.getPosition().equals(movableWorkers.get(chosenWorkerId).getPosition())){
                    chosenWorker = worker;
                }
            }
            if(canBuildBeforeMove){
                return this;
            }
            return new MoveState(game,chosenWorker);
        }
        else{
            if (((BuildBeforeMoveResponse) message).isCanBuildBefore()){
                return new BuildState(game, chosenWorker);
            }
            else{
                return new MoveState(game,chosenWorker);
            }
        }

    }
}
