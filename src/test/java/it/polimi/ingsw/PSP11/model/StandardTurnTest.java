package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StandardTurnTest {

    StandardTurn turn;
    Board board;
    Worker worker;

    @Before
    public void setUp() {
        turn = new StandardTurn();
        board = new Board();
        worker = new Worker(Color.RED);
        turn.startTurn();
    }

    @After
    public void tearDown() {
        turn = null;
        board = null;
        worker = null;
    }

    @Test
    public void start_Turn_Test() {
        turn.startTurn();
        assertFalse(turn.isMovedDown());
        assertFalse(turn.isMovedUp());
        assertFalse(turn.isMoveAgain());
        assertFalse(turn.isBuildAgain());
    }

    @Test
    public void empty_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = turn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void empty_Corner_Board_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(0,0));
        board.placeWorker(new Point(0,0),worker);
        actualPosition = turn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        //worker su cui controllo
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //worker ostacoli
        Worker villainWorker = new Worker(Color.BLUE);
        villainWorker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),villainWorker);
        actualPosition = turn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void board_With_Blocks_And_Domes_Move_Test() {
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

        actualPosition = turn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void cant_Move_Up_Move_Test(){
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

        actualPosition = turn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }


    @Test
    public void apply_Move_On_Empty_Board_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);

        turn.applyMove(worker,board,new Point(2,3));
        assertFalse(turn.isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void apply_Move_With_Moved_Up_Test() {
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        //aggiungo blocco
        board.addBlock(new Point(2,3));

        turn.applyMove(worker,board,new Point(2,3));
        assertTrue(turn.isMovedUp());
        assertEquals(new Point(2,3),worker.getPosition());
        assertTrue(board.hasWorkerOnTop(new Point(2,3)));
        assertFalse(board.hasWorkerOnTop(new Point(2,2)));
        assertEquals(worker, board.getWorker(new Point(2,3)));
        assertNull(board.getWorker(new Point(2, 2)));
    }

    @Test
    public void empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = turn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void empty_Corner_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(0,0));
        board.placeWorker(new Point(0,0),worker);
        actualPosition = turn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void Board_With_Blocks_And_Dome_Build_Test() {
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
        actualPosition = turn.build(worker, board);

        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apply_Build_Test() {
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        turn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.BASE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        turn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        turn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        turn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertTrue(board.hasDomeOnTop(new Point(2,3)));
    }


    @Test
    public void win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        turn.applyMove(worker, board, new Point(3,3));
        assertFalse(turn.winCondition(worker,board));

    }

    @Test
    public void win_Condition_Returns_False_Moved_Up_Test() {
        board.init();

        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));


        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        turn.applyMove(worker, board, new Point(3,3));
        assertFalse(turn.winCondition(worker,board));

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
        turn.applyMove(worker, board, new Point(3,3));
        assertTrue(turn.winCondition(worker,board));

    }

    @Test
    public void endTurnTest() {
        turn.endTurn();
    }
}