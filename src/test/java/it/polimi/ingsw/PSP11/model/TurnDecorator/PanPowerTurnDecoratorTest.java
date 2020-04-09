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

public class PanPowerTurnDecoratorTest {

    StandardTurn turn;
    PanPowerTurnDecorator pan;
    Board board;
    Worker worker;

    @Before
    public void setUp() {
        turn = new StandardTurn();
        pan = new PanPowerTurnDecorator();
        board = new Board();
        worker = new Worker(Color.RED);
        pan.setSharedTurn(turn);
        pan.startTurn();
    }

    @After
    public void tearDown() {
        turn = null;
        pan = null;
        board = null;
        worker = null;
    }

    @Test
    public void startTurn() {
        assertFalse(turn.isMovedDown());
        assertFalse(turn.isMovedUp());
        assertFalse(turn.isMoveAgain());
        assertFalse(turn.isBuildAgain());
        assertFalse(pan.isMovedDownTwoOrMoreLevels());
    }

    @Test
    public void pan_Empty_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = pan.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_Empty_Corner_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(4,0));
        board.placeWorker(new Point(4,0),worker);
        actualPosition = pan.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(3,0),new Point(3,1),new Point(4,1)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_Board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //worker ostacoli
        Worker villainWorker = new Worker(Color.BLUE);
        villainWorker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),villainWorker);
        actualPosition = pan.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_Board_With_Blocks_And_Domes_Move_Test() {
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

        actualPosition = pan.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_Cant_Move_Up_Move_Test(){
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1)}));
        Point workerPosition = new Point(2,2);
        Point baseBlockPosition = new Point(1,1);
        Point middleBlockPosition = new Point(3,3);
        Point topBlockPosition = new Point(2,3);
        Point domePosition = new Point(1,2);

        turn.setCantMoveUp(true);
        board.init();
        //base block on worker position
        board.addBlock(workerPosition);
        //base block, can move
        board.addBlock(baseBlockPosition);
        //middle block, can't move
        board.addBlock(middleBlockPosition);
        board.addBlock(middleBlockPosition);
        //top block , can't move
        board.addBlock(topBlockPosition);
        board.addBlock(topBlockPosition);
        board.addBlock(topBlockPosition);
        //dome, can't move
        board.addDome(domePosition);

        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);

        actualPosition = pan.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_Apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        pan.applyMove(worker,board,new Point(2,3));
        assertFalse(pan.getSharedTurn().isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
        assertFalse(pan.isMovedDownTwoOrMoreLevels());
    }

    @Test
    public void pan_Moved_Down_Two_Levels_Apply_Move_Test() {
        board.init();
        Point jumpPoint = new Point(3,3);
        Point landingPoint = new Point(2,2);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        worker.setPosition(jumpPoint);
        board.placeWorker(jumpPoint, worker);
        pan.applyMove(worker, board, landingPoint);
        assertTrue(pan.isMovedDownTwoOrMoreLevels());
        assertFalse(pan.getSharedTurn().isMovedUp());
    }

    @Test
    public void pan_Moved_Down_Three_Levels_Apply_Move_Test() {
        board.init();
        Point jumpPoint = new Point(3,3);
        Point landingPoint = new Point(2,2);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        worker.setPosition(jumpPoint);
        board.placeWorker(jumpPoint, worker);
        pan.applyMove(worker, board, landingPoint);
        assertTrue(pan.isMovedDownTwoOrMoreLevels());
        assertFalse(pan.getSharedTurn().isMovedUp());
    }

    @Test
    public void pan_Board_With_Blocks_And_Dome_Build_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));

        Point workerPosition = new Point(2,2);
        Point baseBlockPosition = new Point(1,1);
        Point middleBlockPosition = new Point(3,3);
        Point topBlockPosition = new Point(2,3);
        Point domePosition = new Point(1,2);

        board.init();
        //base block on worker position
        board.addBlock(workerPosition);
        //base block, can build
        board.addBlock(baseBlockPosition);
        //middle block, can build
        board.addBlock(middleBlockPosition);
        board.addBlock(middleBlockPosition);
        //top block , can't build
        board.addBlock(topBlockPosition);
        board.addBlock(topBlockPosition);
        board.addBlock(topBlockPosition);
        //dome, can't build
        board.addDome(domePosition);

        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition,worker);
        actualPosition = pan.build(worker, board);

        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void pan_ApplyBuild_Test() {
        Point blockPosition = new Point(2,3);
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        pan.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.BASE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        pan.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        pan.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        pan.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertTrue(board.hasDomeOnTop(blockPosition));
    }

    @Test
    public void pan_Win_Condition_Returns_True_Test() {
        board.init();
        Point jumpPoint = new Point(3,3);
        Point landingPoint = new Point(2,2);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        worker.setPosition(jumpPoint);
        board.placeWorker(jumpPoint, worker);
        pan.applyMove(worker, board, landingPoint);
        assertTrue(pan.winCondition(worker, board));
    }

    @Test
    public void pan_Win_Condition_Returns_False_Test() {
        board.init();
        Point startPoint = new Point(3,3);
        Point landingPoint = new Point(2,2);
        worker.setPosition(startPoint);
        board.placeWorker(startPoint, worker);
        pan.applyMove(worker, board, landingPoint);
        assertFalse(pan.winCondition(worker, board));
    }

    @Test
    public void pan_EndTurn() {
        board.init();
        Point jumpPoint = new Point(3,3);
        Point landingPoint = new Point(2,2);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        board.addBlock(jumpPoint);
        worker.setPosition(jumpPoint);
        board.placeWorker(jumpPoint, worker);
        pan.applyMove(worker, board, landingPoint);
        pan.endTurn();
        assertFalse(pan.isMovedDownTwoOrMoreLevels());
    }
}