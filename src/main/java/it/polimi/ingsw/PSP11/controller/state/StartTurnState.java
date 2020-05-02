package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class StartTurnState implements GameState{

    private final Game game;
    private boolean canBuildBeforeMove;
    private boolean isNew;
    private int chosenWorkerId;
    private ArrayList<Worker> movableWorkers = new ArrayList<>();

    public StartTurnState(Game theGame) {
        this.game = theGame;
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
        game.startTurn();
        this.canBuildBeforeMove = game.getSharedTurn().isCanBuildBeforeMove();
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
            if(! game.move(worker.getId()).isEmpty()) {
                movableWorkers.add(worker.workerClone());
            }
        }
        if(movableWorkers.isEmpty()){
            game.setThereIsALoser(true);
            return true;
        }
        return false;
    }

    @Override
    public void applyMove(Point point) {

    }

    @Override
    public boolean checkWin() {
        return false;
    }

    @Override
    public void workerBuild() {

    }

    @Override
    public void applyBuild(Point point, boolean forceBuildDome) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public Message stateMessage() {
        if (isNew) {
            startTurn();
            isNew = false;
            if (checkLose()){
                return new LoseMessage();
            }
            return new SelectWorkerRequest(movableWorkers, game.getCurrentPlayer().getGod().cardClone());
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
            if(canBuildBeforeMove){
                return this;
            }
            return new MoveState(game,this.chosenWorkerId);
        }
        else{
            if (((BooleanResponse) message).isResponse()){
                return new BuildState(game, this.chosenWorkerId);
            }
            else{
                return new MoveState(game,this.chosenWorkerId);
            }
        }

    }
}
