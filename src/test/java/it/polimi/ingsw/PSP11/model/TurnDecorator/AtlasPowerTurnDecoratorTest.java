package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.StandardTurn;
import it.polimi.ingsw.PSP11.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AtlasPowerTurnDecoratorTest {

    StandardTurn turn;
    Board board;
    Worker worker;
    AtlasPowerTurnDecorator atlasTurn;

    @Before
    public void setUp() {
        atlasTurn = new AtlasPowerTurnDecorator();
        turn = new StandardTurn();
        atlasTurn.setSharedTurn(turn);
        board = new Board();
        worker = new Worker(Color.RED);
        atlasTurn.startTurn();
    }

    @After
    public void tearDown() {
        turn = null;
        board = null;
        worker = null;
        atlasTurn = null;
    }

    @Test
    public void startTurn() {
        atlasTurn.startTurn();
        assertFalse(atlasTurn.getSharedTurn().isMovedDown());
        assertFalse(atlasTurn.getSharedTurn().isMovedUp());
        assertFalse(atlasTurn.getSharedTurn().isBuildAgain());
        assertFalse(atlasTurn.getSharedTurn().isMoveAgain());
        assertTrue(atlasTurn.getSharedTurn().isCanBuildDomeAnyLevel());
    }

    @Test
    public void atlas_empty_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = atlasTurn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void atlas_Empty_Corner_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(0,0));
        board.placeWorker(new Point(0,0),worker);
        actualPosition = atlasTurn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void atlas_apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        atlasTurn.applyMove(worker,board,new Point(2,3));
        assertFalse(turn.isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void atlas_Empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = atlasTurn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void atlas_applyBuild_No_Dome_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        atlasTurn.build(worker,board);
        atlasTurn.applyBuild(worker,board,new Point(2,3),false);
        assertFalse(board.hasDomeOnTop(new Point(2,3)));
    }

    @Test
    public void atlas_applyBuild_Dome_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        atlasTurn.build(worker,board);
        atlasTurn.applyBuild(worker,board,new Point(1,1),true);
        assertTrue(board.hasDomeOnTop(new Point(1,1)));
    }

    @Test
    public void atlas_Win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        atlasTurn.applyMove(worker, board, new Point(3,3));
        assertTrue(atlasTurn.winCondition(worker,board));

    }

    @Test
    public void atlas_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        atlasTurn.applyMove(worker, board, new Point(3,3));
        assertFalse(atlasTurn.winCondition(worker,board));

    }

    @Test
    public void endTurn() {
        atlasTurn.endTurn();
        assertFalse(atlasTurn.getSharedTurn().isCanBuildDomeAnyLevel());
    }
}