package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;
import it.polimi.ingsw.PSP11.model.Worker;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.view.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class StartTurnStateTest {

    StateTestInit init;
    Game game;
    Board board;
    Player currPlayer;
    Worker playerWorker;
    GameState currState;

    @Before
    public void setUp() {
        init = new StateTestInit();
        init.start(2);
        game = init.getGame();
        board = game.getBoard();
        currPlayer = game.getCurrentPlayer();
        playerWorker = new Worker(currPlayer.getColor());
        currPlayer.addWorker(playerWorker);
        currState = new StartTurnState(game);
    }

    @After
    public void tearDown() throws Exception {
        init = null;
        game = null;
        board = null;
        currPlayer = null;
        playerWorker = null;
        currState = null;
    }


    @Test
    public void stateMessage_Returns_LoseMessage() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(5); //Hephaestus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        init.boardForLose();

        Message stateMessage = currState.stateMessage();

        assertTrue(stateMessage instanceof LoseMessage);
    }


    @Test
    public void stateMessage_Returns_SelectWorkerRequest() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(5); //Hephaestus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        Message stateMessage = currState.stateMessage();

        assertTrue(stateMessage instanceof SelectWorkerRequest);
    }


    @Test
    public void stateMessage_Returns_BuildBeforeMoveRequest() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(8); //Prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        //Simulate first interaction with player
        currState.stateMessage();

        Message stateMessage = currState.stateMessage();

        assertTrue(stateMessage instanceof BuildBeforeMoveRequest);
    }

    @Test
    public void execute_Returns_This_On_CanBuildBeforeMove() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(8); //Prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        currState.stateMessage();
        GameState nextState = currState.execute(new SelectWorkerResponse(0), new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertEquals(currState, nextState);
    }


    @Test
    public void execute_Returns_MoveState() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(0); //Apollo
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        currState.stateMessage();
        GameState nextState = currState.execute(new SelectWorkerResponse(0), new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(nextState instanceof MoveState);
    }


    @Test
    public void execute_Returns_BuildState() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(8); //Prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        currState.stateMessage();
        GameState nextState = currState.execute(new BooleanResponse(true), new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(nextState instanceof BuildState);
    }


    @Test
    public void execute_Returns_MoveState_Second_Time() {
        Point workerPosition = new Point(2,2);

        playerWorker.setPosition(workerPosition);
        board.placeWorker(workerPosition, playerWorker);

        game.selectGod(8); //Prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0)); //set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn()); //set shared turn

        currState.stateMessage();
        GameState nextState = currState.execute(new BooleanResponse(false), new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(nextState instanceof MoveState);
    }


}