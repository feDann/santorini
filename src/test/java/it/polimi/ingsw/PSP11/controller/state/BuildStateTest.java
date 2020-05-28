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

import static org.junit.Assert.assertTrue;

public class BuildStateTest {

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
        board.placeWorker(workerPosition, playerWorker);
        playerWorker.setPosition(workerPosition);
        currentPlayer.addWorker(playerWorker);
        state = new BuildState(init.getGame(),0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_Change_State_Three_Players_Expected_Error_During_Send(){ game.selectGod(0);//Apollo
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));//set God
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());


        GameState newState = state.execute(new BuildResponse(new Point(2,2)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));
        assertTrue(newState instanceof StartTurnState);
    }

    @Test
    public void stateMessage_Return_Build_Request_Message_Test_Expected_Error_During_Send(){
        game.selectGod(0);
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        assertTrue(state.stateMessage() instanceof BuildRequest);
    }


    //atlas activate it's power
    @Test
    public void stateMessage_Return_Build_Dome_Request_Test_Expected_Error_During_Send(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();

        GameState newState = state.execute(new BuildResponse(new Point(2,2)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(state.stateMessage() instanceof BuildDomeRequest);
    }

    //atlas but cant use it's power
    @Test
    public void stateMessage_Return_cant_BuildDome_Test_Expected_Error_During_Send(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        Point buildPoint = new Point(2,2);
        board.addBlock(buildPoint);
        board.addBlock(buildPoint);
        board.addBlock(buildPoint);

        currentPlayer.getPlayerTurn().startTurn();

        GameState newState = state.execute(new BuildResponse(buildPoint),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof StartTurnState);
    }

    @Test
    public void stateMessage_Dome_Is_Built_Any_Level_Test_Expected_Error_During_Send(){
        game.selectGod(3);//atlas (build dome at any level)
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();
        Point buildPosition = new Point(2,2);

        state.execute(new BuildResponse(buildPosition),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        state.execute(new BooleanResponse(true),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(init.getGame().getBoard().hasDomeOnTop(buildPosition));
    }

    @Test
    public void stateMessage_Return_Build_Twice_Test_Expected_Error_During_Send(){
        game.selectGod(4);//demeter
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();

        GameState newState = state.execute(new BuildResponse(new Point(2,2)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(state.stateMessage() instanceof BuildAgainRequest);
    }

    @Test
    public void stateMessage_Return_Move_Before_Build_Test_Expected_Error_During_Send(){
        game.selectGod(8);//prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();
        assertTrue(game.getSharedTurn().isCanBuildBeforeMove());

        GameState newState = state.execute(new BuildResponse(new Point(2,2)),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof MoveState);
    }

    @Test
    public void stateMessage_Return_Build_State_Test_Expected_Error_During_Send(){
        game.selectGod(8);//prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();
        assertTrue(game.getSharedTurn().isCanBuildBeforeMove());

        GameState newState = state.execute(new BooleanResponse(true),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof BuildState);
    }

    @Test
    public void stateMessage_Return_Apply_Build_State_Test_Expected_Error_During_Send(){
        game.selectGod(8);//prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();
        assertTrue(game.getSharedTurn().isCanBuildBeforeMove());

        GameState newState = state.execute(new BooleanResponse(true),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof BuildState);
    }

    @Test
    public void stateMessage_Build_Before_Move_Test_Expected_Error_During_Send(){
        game.selectGod(8);//prometheus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();
        assertTrue(game.getSharedTurn().isCanBuildBeforeMove());

        GameState newState = state.execute(new BooleanResponse(true),new VirtualView(new ClientSocketConnection(null, null), init.getP2().playerClone(), init.getP1().playerClone()));

        assertTrue(newState instanceof BuildState);
    }

    @Test
    public void State_Message_Player_Lose_The_Game_Test_Expected_Error_During_Send(){
        game.selectGod(5);//hephaestus
        game.getCurrentPlayer().setGod(game.selectPlayerGod(0));
        game.getCurrentPlayer().setPlayerTurn(game.getSharedTurn());

        currentPlayer.getPlayerTurn().startTurn();

        board.addDome(new Point(0,1));
        board.addDome(new Point(1,0));
        board.addDome(new Point(1,1));

        Message message = state.stateMessage();

        assertTrue(message instanceof LoseMessage);

    }

}