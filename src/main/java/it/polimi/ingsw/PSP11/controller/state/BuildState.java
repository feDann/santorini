package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class BuildState implements GameState {

    private Game game;
    private int chosenWorkerID;
    private ArrayList<Point> possibleBuilds = new ArrayList<Point>();
    private boolean askToBuildAgain;
    private boolean askToBuildDome;
    private Point buildPosition;

    public BuildState(Game game, int chosenWorker) {
        this.game = game;
        this.chosenWorkerID = chosenWorker;
        this.askToBuildAgain = false;
        this.askToBuildDome = false;
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
    public boolean checkWin() {
        return game.checkWinner(chosenWorkerID);
    }

    @Override
    public void workerBuild() {
        possibleBuilds = game.build(chosenWorkerID);
    }

    @Override
    public void applyBuild(Point point, boolean forceBuildDome) {
        game.applyBuild(point, chosenWorkerID, forceBuildDome);
    }

    @Override
    public void endTurn() {

    }

    @Override
    public Message stateMessage() {
        if (checkWin()){
            return new WinMessage();
        }
        if (askToBuildDome){
            askToBuildDome = false;
            return new BuildDomeRequest();
        }
        if (askToBuildAgain){
            askToBuildAgain = false;
            return new BuildAgainRequest();
        }
        workerBuild();
        if (checkLose()){
            return new LoseMessage();
        }
        return new BuildRequest(possibleBuilds);
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        if (message instanceof BuildResponse){
            buildPosition = ((BuildResponse) message).getPoint();
            if (game.getSharedTurn().isCanBuildDomeAnyLevel() && game.getBoard().getCurrentLevel(buildPosition).ordinal() != 3){
                askToBuildDome = true;
                return this;
            }
            applyBuild(buildPosition,false);
            if (game.getSharedTurn().isBuildAgain()){
                askToBuildAgain = true;
                return this;
            }
            if (game.getSharedTurn().isCanBuildBeforeMove()){
                return new MoveState(game, chosenWorkerID);
            }
        }
        if (message instanceof BooleanResponse){
            if (game.getSharedTurn().isCanBuildDomeAnyLevel()){
                applyBuild(buildPosition,((BooleanResponse) message).isResponse());
            }
            else if (((BooleanResponse) message).isResponse()){
                return this;
            }
        }
        virtualView.sendMessage(new EndTurnMessage());
        game.nextPlayer();
        return new StartTurnState(game);
    }
}
