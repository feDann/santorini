package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ArtemisPowerTurnDecoratorTest {

    StandardTurn turn;
    Board board;
    Worker worker;
    ArtemisPowerTurnDecorator artemisTurn;

    @Before
    public void artemis_SetUp() {
        artemisTurn = new ArtemisPowerTurnDecorator();
        turn = new StandardTurn();
        artemisTurn.setSharedTurn(turn);
        board = new Board();
        worker = new Worker(Color.RED);
        artemisTurn.startTurn();
    }

    @After
    public void artemis_TearDown() {
        turn = null;
        board = null;
        worker = null;
        artemisTurn = null;
    }

    @Test
    public void artemis_startTurn() {
        artemisTurn.startTurn();
        assertFalse(artemisTurn.getSharedTurn().isMovedDown());
        assertFalse(artemisTurn.getSharedTurn().isMovedUp());
        assertFalse(artemisTurn.getSharedTurn().isBuildAgain());
        assertTrue(artemisTurn.getSharedTurn().isMoveAgain());
        assertEquals(0,artemisTurn.getNumberOfTimesAlreadyMoved());
    }

    @Test
    public void artemis_Empty_Board_Move_And_ApplyMove_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = artemisTurn.move(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
        assertTrue(artemisTurn.getSharedTurn().isMoveAgain());

        //seconda move
        artemisTurn.applyMove(worker,board,new Point(2,1));
        ArrayList<Point> actualPosition2;
        actualPosition2 = artemisTurn.move(worker, board);
        ArrayList<Point> expectedPosition2 =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(3,2),new Point(3,1),new Point(1,0),new Point(2,0),new Point(3,0)));
        assertTrue(expectedPosition2.containsAll(actualPosition2));
        //actualPosition2.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition2.containsAll(expectedPosition2));
        assertFalse(artemisTurn.getSharedTurn().isMoveAgain());
    }

    @Test
    public void artemis_Empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(2,2));
        board.placeWorker(new Point(2,2),worker);
        actualPosition = artemisTurn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void artemis_Empty_Corner_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        board.init();
        worker.setPosition(new Point(0,0));
        board.placeWorker(new Point(0,0),worker);
        actualPosition = artemisTurn.build(worker, board);
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(0,1),new Point(1,0)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void artemis_board_With_Blocks_And_Dome_Build_Test() {
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
        actualPosition = artemisTurn.build(worker, board);

        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point(1,1),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)));
        assertTrue(expectedPosition.containsAll(actualPosition));
        //actualPosition.forEach((point)-> System.out.println(point.toString()));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void artemis_Apply_Build_Test() {
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        artemisTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.BASE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        artemisTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        artemisTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertFalse(board.hasDomeOnTop(new Point(2,3)));

        artemisTurn.applyBuild(worker,board,new Point(2,3),false);
        assertEquals(Block.TOP,board.getCurrentLevel(new Point(2,3)));
        assertTrue(board.hasDomeOnTop(new Point(2,3)));
    }

    @Test
    public void artemis_Win_Condition_Returns_False_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        artemisTurn.applyMove(worker, board, new Point(3,3));
        assertFalse(artemisTurn.winCondition(worker,board));

    }

    @Test
    public void artemis_win_Condition_Returns_True_Test() {
        board.init();

        board.addBlock(new Point(2,3));
        board.addBlock(new Point(2,3));

        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));
        board.addBlock(new Point(3,3));

        worker.setPosition(new Point(2,3));
        board.placeWorker(new Point(2,3),worker);
        artemisTurn.applyMove(worker, board, new Point(3,3));
        assertTrue(artemisTurn.winCondition(worker,board));

    }


    @Test
    public void artemis_EndTurn() {
        board.init();
        worker.setPosition(new Point(2,3));

        artemisTurn.move(worker,board);
        artemisTurn.applyMove(worker,board, new Point(3,3));
        assertEquals(1,artemisTurn.getNumberOfTimesAlreadyMoved());

        artemisTurn.move(worker,board);
        artemisTurn.applyMove(worker,board, new Point(3,4));
        assertEquals(2,artemisTurn.getNumberOfTimesAlreadyMoved());

        artemisTurn.endTurn();
        assertEquals(0,artemisTurn.getNumberOfTimesAlreadyMoved());

    }
}