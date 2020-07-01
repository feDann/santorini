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


    /**
     * {@inheritDoc}
     */
    @Override
    public void selectGameGods(ArrayList<Integer> ids) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectPlayerGod(int index) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeWorker(Point point) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startTurn() {
        game.startTurn();
        this.canBuildBeforeMove = game.getSharedTurn().isCanBuildBeforeMove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectWorker() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveWorker() {

    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyMove(Point point) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void workerBuild() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyBuild(Point point, boolean forceBuildDome) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {

    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
