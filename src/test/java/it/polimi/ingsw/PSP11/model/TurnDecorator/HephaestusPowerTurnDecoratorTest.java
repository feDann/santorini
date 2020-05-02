package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.*;
import it.polimi.ingsw.PSP11.model.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class HephaestusPowerTurnDecoratorTest {

    HephaestusPowerTurnDecorator hephaestusTurn;
    StandardTurn turn;
    Board board;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        hephaestusTurn = new HephaestusPowerTurnDecorator();
        turn = new StandardTurn();
        hephaestusTurn.setSharedTurn(turn);
        board = new Board();
        board.init();
        worker = new Worker(Color.GREEN);
        hephaestusTurn.startTurn();
    }

    @After
    public void tearDown() throws Exception {
        hephaestusTurn = null;
        turn = null;
        board = null;
        worker = null;
    }

    @Test
    public void hephaestus_StartTurn() {
        assertFalse(hephaestusTurn.getSharedTurn().isMovedUp());
        assertFalse(hephaestusTurn.getSharedTurn().isMovedDown());
        assertFalse(hephaestusTurn.getSharedTurn().isMoveAgain());
        assertFalse(hephaestusTurn.getSharedTurn().isBuildAgain());
    }

    @Test
    public void hephaestus_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        Point workerPosition = new Point(2,2);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = hephaestusTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void hephaestus_Corner_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        Point workerPosition = new Point(0,0);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = hephaestusTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void hephaestus_Apply_Move_On_Empty_Board_Test() {
        Point oldWorkerPosition = new Point(2,2);
        Point newWorkerPosition = new Point(2,3);
        board.init();
        worker.setPosition(oldWorkerPosition);
        board.placeWorker(oldWorkerPosition, worker);
        hephaestusTurn.applyMove(worker,board,newWorkerPosition);
        assertFalse(hephaestusTurn.getSharedTurn().isMovedUp());
        assertEquals(newWorkerPosition,worker.getPosition());
        assertTrue(board.hasWorkerOnTop(newWorkerPosition));
        assertFalse(board.hasWorkerOnTop(oldWorkerPosition));
        assertEquals(worker, board.getWorker(newWorkerPosition));
        assertNull(board.getWorker(oldWorkerPosition));
    }

    @Test
    public void hephaestus_Empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));

        Point workerPosition = new Point(2,2);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition,worker);
        actualPosition = hephaestusTurn.build(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void hephaestus_SecondBuild_Build_Test(){
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition = new ArrayList<>(Collections.singletonList(new Point(2, 2)));

        Point workerPosition = new Point(1,1);
        Point buildPosition = new Point(2,2);
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        hephaestusTurn.applyBuild(worker,board, buildPosition, false);
        assertTrue(hephaestusTurn.getSharedTurn().isBuildAgain());
        actualPosition = hephaestusTurn.build(worker,board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
        assertFalse(hephaestusTurn.getSharedTurn().isBuildAgain());

    }

    @Test
    public void hephaestus_Second_Build_Cant_Build_Test(){
        Point workerPosition = new Point(1,1);
        Point buildPosition = new Point(2,2);
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        board.addBlock(buildPosition);
        board.addBlock(buildPosition);
        hephaestusTurn.applyBuild(worker,board, buildPosition, false);
        assertFalse(hephaestusTurn.getSharedTurn().isBuildAgain());
    }

    @Test
    public void hephaestus_applyBuild_Test() {
        Point blockPosition = new Point(2,3);
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        hephaestusTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.BASE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        hephaestusTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        hephaestusTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        hephaestusTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertTrue(board.hasDomeOnTop(blockPosition));
    }


    @Test
    public void hephaestus_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        hephaestusTurn.applyMove(worker, board, new Point(3,3));
        assertFalse(hephaestusTurn.winCondition(worker,board));

    }

    @Test
    public void hephaestus_Win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        hephaestusTurn.applyMove(worker, board, new Point(3,3));
        assertTrue(hephaestusTurn.winCondition(worker,board));

    }


    @Test
    public void hephaestus_endTurn() {
        hephaestusTurn.endTurn();
        assertFalse(hephaestusTurn.getSharedTurn().isMovedUp());
        assertFalse(hephaestusTurn.getSharedTurn().isMovedDown());
        assertFalse(hephaestusTurn.getSharedTurn().isMoveAgain());
        assertFalse(hephaestusTurn.getSharedTurn().isBuildAgain());
    }
}