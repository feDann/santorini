package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.InvalidWorkerPosition;
import it.polimi.ingsw.PSP11.messages.PlaceWorkerRequest;
import it.polimi.ingsw.PSP11.messages.PlaceWorkerResponse;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlaceWorkerStateTest {

    StateTestInit init = new StateTestInit();

    Worker playerWorker;
    Board board;
    GameState state;
    Player currentPlayer;
    Game game;
    Point workerPosition;

    @Before
    public void setUp() throws Exception {
        init = new StateTestInit();
        workerPosition = new Point(0,0);
        init.start(2);
        game = init.getGame();
        currentPlayer = game.getCurrentPlayer();
        board = game.getBoard();
        playerWorker = new Worker(init.getP1().getColor());
        currentPlayer.addWorker(playerWorker);
        state = new PlaceWorkerState(init.getGame());
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void execute_valid_position_Test(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        GameState newState = state.execute(new PlaceWorkerResponse(workerPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(board.hasWorkerOnTop(workerPosition));
        assertTrue(newState instanceof PlaceWorkerState);
    }

    @Test
    public void execute_invalid_position_Test(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        GameState newState = state.execute(new PlaceWorkerResponse(workerPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(board.hasWorkerOnTop(workerPosition));
        assertTrue(newState instanceof PlaceWorkerState);

        newState = newState.execute(new PlaceWorkerResponse(workerPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(newState.stateMessage() instanceof InvalidWorkerPosition);

    }

    @Test
    public void Welcome_Message_test(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        assertTrue(state.stateMessage() instanceof PlaceWorkerRequest);
    }

    @Test
    public void execute_change_player_test(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        GameState newState = state.execute(new PlaceWorkerResponse(workerPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        newState = newState.execute(new PlaceWorkerResponse(new Point(4,4)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof PlaceWorkerState);
        assertFalse(currentPlayer.equals(game.getCurrentPlayer()));

    }

    @Test
    public void execute_change_state_test(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        //player 1
        GameState newState = state.execute(new PlaceWorkerResponse(workerPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        newState = newState.execute(new PlaceWorkerResponse(new Point(4,4)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        //player 2
        newState = newState.execute(new PlaceWorkerResponse(new Point(3,4)),new VirtualView(new ClientSocketConnection(null, null), init.getP1().playerClone(), init.getP2().playerClone()));
        newState = newState.execute(new PlaceWorkerResponse(new Point(3,3)),new VirtualView(new ClientSocketConnection(null, null), init.getP1().playerClone(), init.getP2().playerClone()));

        assertTrue(newState instanceof StartTurnState);
    }

}