package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class MoveState implements GameState {

    private Game game;
    private int chosenWorkerID;
    private ArrayList<Point> possibleMoves = new ArrayList<Point>();
    boolean askToMoveAgain;

    public MoveState(Game game, int chosenWorker) {
        this.game = game;
        this.chosenWorkerID = chosenWorker;
        this.askToMoveAgain = false;
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
        possibleMoves = game.move(chosenWorkerID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkLose() {
        if (possibleMoves.isEmpty()){
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
        game.applyMove(point, chosenWorkerID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return game.checkWinner(chosenWorkerID);
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
        if (checkWin()){
            return new WinMessage();
        }
        if (askToMoveAgain){
            askToMoveAgain = false;
            return new MoveAgainRequest();
        }
        moveWorker();
        if (checkLose()){
            return new LoseMessage();
        }
        return new MoveRequest(possibleMoves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        if (message instanceof MoveResponse){
            applyMove(((MoveResponse) message).getPoint());
            if (game.getSharedTurn().isMoveAgain()){
                askToMoveAgain = true;
                return this;
            }
        }
        if (message instanceof BooleanResponse){
            if (((BooleanResponse) message).isResponse()){
                return this;
            }
        }
        return new BuildState(game, chosenWorkerID);
    }
}
