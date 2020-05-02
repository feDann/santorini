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

public class PrometheusPowerTurnDecoratorTest {

    StandardTurn turn;
    PrometheusPowerTurnDecorator prometheus;
    Board board;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        turn = new StandardTurn();
        prometheus = new PrometheusPowerTurnDecorator();
        board = new Board();
        worker = new Worker(Color.RED);
        prometheus.setSharedTurn(turn);
        prometheus.startTurn();
    }

    @After
    public void tearDown() throws Exception {
        turn = null;
        prometheus = null;
        board = null;
        worker = null;
    }

    @Test
    public void startTurn() {
        assertTrue(turn.isCanBuildBeforeMove());
        assertFalse(prometheus.isHasBuilt());
        assertFalse(turn.isMovedUp());
        assertFalse(turn.isMovedDown());
        assertFalse(turn.isMoveAgain());
        assertFalse(turn.isBuildAgain());
        assertFalse(turn.isCantMoveUp());
        assertFalse(turn.isCanBuildDomeAnyLevel());
    }

    @Test
    public void prometheus_Empty_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = prometheus.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Empty_Corner_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(4,0));
        board.placeWorker(new Point(4,0),worker);
        actualPosition = prometheus.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(3,0),new Point(3,1),new Point(4,1)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //worker ostacoli
        Worker villainWorker = new Worker(Color.BLUE);
        villainWorker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),villainWorker);
        actualPosition = prometheus.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Board_With_Blocks_And_Domes_Move_Test() {
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

        actualPosition = prometheus.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Cant_Move_Up_Move_Test(){
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

        actualPosition = prometheus.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Has_Built_Move_Test() {
        board.init();
        ArrayList<Point> actualPosition;
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        prometheus.applyBuild(worker, board, new Point(3,3), false);
        actualPosition = prometheus.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_Apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        prometheus.applyMove(worker,board,new Point(2,3));
        assertFalse(prometheus.getSharedTurn().isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void prometheus_Board_With_Blocks_And_Dome_Build_Test() {
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
        actualPosition = prometheus.build(worker, board);

        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void prometheus_ApplyBuild_Test() {
        Point blockPosition = new Point(2,3);
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        prometheus.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.BASE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));
        assertTrue(prometheus.isHasBuilt());

        prometheus.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));
        assertTrue(prometheus.isHasBuilt());

        prometheus.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));
        assertTrue(prometheus.isHasBuilt());

        prometheus.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertTrue(board.hasDomeOnTop(blockPosition));
        assertTrue(prometheus.isHasBuilt());
    }

    @Test
    public void prometheus_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        prometheus.applyMove(worker, board, new Point(3,3));
        assertFalse(prometheus.winCondition(worker,board));

    }

    @Test
    public void prometheus_Win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        prometheus.applyMove(worker, board, new Point(3,3));
        assertTrue(prometheus.winCondition(worker,board));

    }

    @Test
    public void endTurn() {
    }
}