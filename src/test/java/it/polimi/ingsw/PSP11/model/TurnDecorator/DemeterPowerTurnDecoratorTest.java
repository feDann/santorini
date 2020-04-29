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

public class DemeterPowerTurnDecoratorTest {

    DemeterPowerTurnDecorator demeter;
    StandardTurn turn;
    Board board;
    Worker worker;

    @Before
    public void setUp() {
        demeter = new DemeterPowerTurnDecorator();
        turn = new StandardTurn();
        demeter.setSharedTurn(turn);
        board = new Board();
        worker = new Worker(Color.RED);
        demeter.startTurn();
    }

    @After
    public void tearDown() {
        demeter = null;
        turn = null;
        board = null;
        worker = null;
    }

    @Test
    public void demeter_StartTurn() {
        demeter.startTurn();
        assertFalse(demeter.getSharedTurn().isMovedDown());
        assertFalse(demeter.getSharedTurn().isMovedUp());
        assertFalse(demeter.getSharedTurn().isMoveAgain());
        assertTrue(demeter.getSharedTurn().isBuildAgain());
        assertEquals(0, demeter.getNumberOfTimesAlreadyBuilt());
    }

    @Test
    public void demeter_Empty_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = demeter.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void demeter_Empty_Corner_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(0,0));
        board.placeWorker(new Point(0,0),worker);
        actualPosition = demeter.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void demeter_Board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //worker ostacoli
        Worker villainWorker = new Worker(Color.BLUE);
        villainWorker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),villainWorker);
        actualPosition = demeter.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void demeter_Board_With_Blocks_And_Domes_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //piazzo block dove c'è il worker
        board.addBlock(new Point(2,2));
        //in 1.1 siamo ad altezza 1, il worker puo salire
        board.addBlock(new Point(1,1));
        //in 2.3 siamo ad altezza 3 il worker non puo salire
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        //in 3.3 altezza due il worker puo salire
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        //in 1.2 metto una cupola, il worker non puo andare
        board.addDome(new Point(1,2));
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        actualPosition = demeter.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void demeter_Apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        demeter.applyMove(worker,board,new Point(2,3));
        assertFalse(turn.isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void demeter_Apply_Move_With_Moved_Up_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //aggiungo blocco
        board.addBlock(new Point(2,3));

        demeter.applyMove(worker,board,new Point(2,3));
        assertTrue(turn.isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void demeter_Build_And_Apply_Build_Test_Empty_Board() {

        assertTrue(demeter.getSharedTurn().isBuildAgain());
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = demeter.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));

        demeter.applyBuild(worker, board, new Point(1,1), false);

        assertTrue(demeter.getSharedTurn().isBuildAgain());

        ArrayList<Point> newActualPosition;
        newActualPosition = demeter.build(worker, board);
        ArrayList<Point> newExpectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(newExpectedPosition.containsAll(newActualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(newActualPosition.containsAll(newExpectedPosition));
        assertFalse(demeter.getSharedTurn().isBuildAgain());

    }


    @Test
    public void demeter_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        demeter.applyMove(worker, board, new Point(3,3));
        assertFalse(demeter.winCondition(worker,board));

    }

    @Test
    public void demeter_Win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        demeter.applyMove(worker, board, new Point(3,3));
        assertTrue(demeter.winCondition(worker,board));

    }

    @Test
    public void demeter_EndTurn() {

        board.init();
        worker.setPosition(new Point(1,1));
        board.placeWorker(new Point(1,1), worker);
        demeter.build(worker, board);
        demeter.applyBuild(worker,board,new Point(2,2), false);
        assertEquals(1, demeter.getNumberOfTimesAlreadyBuilt());
        demeter.build(worker, board);
        demeter.applyBuild(worker,board,new Point(2,3), false);
        assertEquals(2, demeter.getNumberOfTimesAlreadyBuilt());
        demeter.endTurn();
        assertEquals(0, demeter.getNumberOfTimesAlreadyBuilt());

    }

}