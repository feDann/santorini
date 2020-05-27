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

import static org.junit.Assert.*;

import java.awt.*;

public class MoveStateTest {

    StateTestInit init;
    Worker playerWorker;
    Board board;
    GameState state;
    Player currentPlayer;
    Game game;
    Point workerPosition;

    @Before
    public void setUp() throws Exception {
        init = new StateTestInit();
        workerPosition = new Point(1,1);
        init.start(3);
        game = init.getGame();
        currentPlayer = game.getCurrentPlayer();
        board = game.getBoard();
        playerWorker = new Worker(init.getP1().getColor());
        board.placeWorker(workerPosition, playerWorker);
        playerWorker.setPosition(workerPosition);
        currentPlayer.addWorker(playerWorker);
        state = new MoveState(init.getGame(),0);
    }

    @After
    public void tearDown() throws Exception {
        init = null;
        board = null;
        playerWorker = null;
        state = null;
        currentPlayer = null;
    }

    @Test
    public void moveWorker() {
    }

    @Test
    public void checkLose() {
    }

    @Test
    public void applyMove() {
    }

    @Test
    public void checkWin() {
    }



    @Test
    public void stateMessage_Return_Move_Request() {
        game.selectGod(0);//Apollo
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        assertTrue(state.stateMessage() instanceof MoveRequest);

    }

    @Test
    public void stateMessage_Return_Win_Message(){
        game.selectGod(7);//Pan
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        //simulate a win move
        board.addBlock(workerPosition);
        board.addBlock(workerPosition);

        currentPlayer.getPlayerTurn().applyMove(playerWorker,board,new Point(0,0));


        assertTrue(state.stateMessage() instanceof WinMessage);


    }

    @Test
    public void stateMessage_Return_MoveAgainRequest_Expected_Error_During_Send(){
        game.selectGod(1);//Artemis
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        currentPlayer.getPlayerTurn().startTurn();

        //simulate an initial move
        state = state.execute(new MoveResponse(new Point(0,0)),new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(state.stateMessage() instanceof MoveAgainRequest);
    }

    @Test
    public void stateMessage_Return_LoseMessage(){
        init.boardForLose();

        board.placeWorker(new Point(2,2),playerWorker);
        playerWorker.setPosition(new Point(2,2));

        game.selectGod(1);//Artemis
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        assertTrue(state.stateMessage() instanceof LoseMessage);



    }

    @Test
    public void execute_Return_BuildState_Expected_Error_During_Send() {
        game.selectGod(0);//Apollo
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        currentPlayer.getPlayerTurn().startTurn();
        GameState nextState = state.execute(new MoveResponse(new Point(0,0)),new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(nextState instanceof BuildState);
    }

    @Test
    public void execute_Return_MoveState_Expected_Error_During_Send(){
        game.selectGod(1);//artemis
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        currentPlayer.getPlayerTurn().startTurn();
        GameState nextState = state.execute(new MoveResponse(new Point(0,0)),new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(nextState instanceof MoveState);
    }

    @Test
    public void execute_on_BooleanResponse_Return_MoveState_Expected_Error_During_Send(){
        game.selectGod(1);//artemis
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        currentPlayer.getPlayerTurn().startTurn();
        GameState nextState = state.execute(new BooleanResponse(true),new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(nextState instanceof MoveState);
    }
    @Test
    public void execute_on_BooleanResponse_Return_BuildState_Expected_Error_During_Send(){
        game.selectGod(1);//artemis
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());
        currentPlayer.getPlayerTurn().startTurn();
        GameState nextState = state.execute(new BooleanResponse(false),new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(nextState instanceof BuildState);
    }
}