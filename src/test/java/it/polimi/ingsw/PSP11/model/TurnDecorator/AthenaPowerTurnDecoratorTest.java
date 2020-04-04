package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.*;
import it.polimi.ingsw.PSP11.model.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AthenaPowerTurnDecoratorTest {

    StandardTurn turn;
    GodTurn athenaTurn;
    Board board;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        turn = new StandardTurn();
        athenaTurn = new AthenaPowerTurnDecorator();
        board = new Board();
        worker = new Worker(Color.RED);
        athenaTurn.setSharedTurn(turn);
        athenaTurn.startTurn();
    }

    @After
    public void tearDown() throws Exception {
        turn = null;
        athenaTurn = null;
        board = null;
        worker = null;
    }

    @Test
    public void startTurn() {
        assertFalse(athenaTurn.getSharedTurn().isMovedDown());
        assertFalse(athenaTurn.getSharedTurn().isMovedUp());
        assertFalse(athenaTurn.getSharedTurn().isMoveAgain());
        assertFalse(athenaTurn.getSharedTurn().isBuildAgain());
        assertFalse(athenaTurn.getSharedTurn().isCantMoveUp());
    }
    @Test
    public void athena_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));
        Point workerPosition = new Point(2,2);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = athenaTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void athena_Corner_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(0,1),new Point(1,0)}));
        Point workerPosition = new Point(0,0);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = athenaTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void athena_Board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //worker ostacoli
        Worker villainWorker = new Worker(Color.BLUE);
        villainWorker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),villainWorker);
        actualPosition = athenaTurn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void athena_Apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        athenaTurn.applyMove(worker,board,new Point(2,3));
        assertFalse(athenaTurn.getSharedTurn().isMovedUp());
        assertFalse(athenaTurn.getSharedTurn().isCantMoveUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }


    @Test
    public void athena_Apply_Move_With_Moved_Up_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //aggiungo blocco
        board.addBlock(new Point(2,3));

        athenaTurn.applyMove(worker,board,new Point(2,3));
        assertTrue(athenaTurn.getSharedTurn().isMovedUp());
        assertTrue(athenaTurn.getSharedTurn().isCantMoveUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }


    @Test
    public void athena_Empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = athenaTurn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void athena_Board_With_Blocks_And_Dome_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();

        //piazzo block dove c'è il worker
        board.addBlock(new Point(2,2));
        //in 1.1 siamo ad altezza 1, il worker puo costuire
        board.addBlock(new Point(1,1));
        //in 2.3 siamo ad altezza 3 il worker puo costruire
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        //in 3.3 altezza due il worker puo costruire
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        //in 1.2 metto una cupola, il worker non puo costuire
        board.addDome(new Point(1,2));

        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = athenaTurn.build(worker, board);

        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }


    @Test
    public void athena_Apply_Build_Test() {
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        athenaTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.BASE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        athenaTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        athenaTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        athenaTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertTrue(board.hasDomeOnTop(new Point(2,3)));
    }


    @Test
    public void athena_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        athenaTurn.applyMove(worker, board, new Point(3,3));
        assertFalse(athenaTurn.winCondition(worker,board));

    }

    @Test
    public void win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        athenaTurn.applyMove(worker, board, new Point(3,3));
        assertTrue(athenaTurn.winCondition(worker,board));

    }
 }

