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
        possibleMoves = game.move(chosenWorkerID);
    }

    @Override
    public boolean checkLose() {
        if (possibleMoves.isEmpty()){
            game.setThereIsALooser(true);
            return true;
        }
        return false;
    }

    @Override
    public void applyMove(Point point) {
        game.applyMove(point, chosenWorkerID);
    }

    @Override
    public boolean checkWin() {
        return false;
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

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        if (message instanceof MoveResponse){
            applyMove(((MoveResponse) message).getPoint());
            System.out.println("\n\n\nBULIAN =" + game.getCurrentPlayer().getPlayerTurn().getSharedTurn().isMoveAgain());
            if (game.getCurrentPlayer().getPlayerTurn().getSharedTurn().isMoveAgain()){
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
