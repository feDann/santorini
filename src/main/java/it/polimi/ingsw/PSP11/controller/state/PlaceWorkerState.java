package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public class PlaceWorkerState implements GameState{

    private Game game;
    private boolean invalidPoint;
    private int numOfWorker;
    private int numOfPlayer;
    private boolean isNew;

    public PlaceWorkerState(Game game) {
        this.game = game;
        this.invalidPoint =false;
        this.numOfWorker = 0;
        this.numOfPlayer = 0;
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
        if(game.getBoard().hasWorkerOnTop(point)){
            invalidPoint = true;
            return;
        }
        Worker worker = new Worker(game.getCurrentPlayer().getColor());
        worker.setPosition(point);
        game.getCurrentPlayer().addWorker(worker);
        game.placeWorker(point, worker);

        numOfWorker++;
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
            this.isNew = false;
            game.notifyBoard();
        }

        if (invalidPoint){
            return new InvalidWorkerPosition();
        }
        return new PlaceWorkerRequest(game.boardClone());
    }

    @Override
    public GameState execute(Message message, VirtualView virtualView) {
        invalidPoint = false;
        Point point = ((PlaceWorkerResponse) message).getPoint();
        System.out.println(point.toString());
        placeWorker(((PlaceWorkerResponse) message).getPoint());
        if (numOfWorker < 2){
            return this;
        }
        numOfWorker = 0;
        numOfPlayer++;
        virtualView.sendMessage(new EndTurnMessage());
        game.nextPlayer();
        if (numOfPlayer < game.getNumOfPlayers()){
            return this;
        }

        return new StartTurnState(game);
    }
}
