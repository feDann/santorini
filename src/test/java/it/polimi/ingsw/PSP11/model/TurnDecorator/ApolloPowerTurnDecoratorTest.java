package it.polimi.ingsw.PSP11.model.TurnDecorator;

import it.polimi.ingsw.PSP11.model.*;
import it.polimi.ingsw.PSP11.model.Color;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ApolloPowerTurnDecoratorTest {

    StandardTurn turn;
    GodTurn apolloTurn;
    Board board;
    Worker worker;

    @Before
    public void setUp() throws Exception {
        turn = new StandardTurn();
        apolloTurn = new ApolloPowerTurnDecorator();
        board = new Board();
        worker = new Worker(Color.RED);
        apolloTurn.setSharedTurn(turn);
        apolloTurn.startTurn();
    }

    @After
    public void tearDown() throws Exception {
        turn = null;
        apolloTurn = null;
        board = null;
        worker = null;
    }

    @Test
    public void apollo_startTurn_Test() {
        assertFalse(apolloTurn.getSharedTurn().isMovedDown());
        assertFalse(apolloTurn.getSharedTurn().isMovedUp());
        assertFalse(apolloTurn.getSharedTurn().isMoveAgain());
        assertFalse(apolloTurn.getSharedTurn().isBuildAgain());
    }

    @Test
    public void apollo_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));
        Point workerPosition = new Point(2,2);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = apolloTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apollo_Corner_Empty_Move_Board_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(0,1),new Point(1,0)}));
        Point workerPosition = new Point(0,0);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        actualPosition = apolloTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apollo_Board_With_Worker_Move_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(3,2),new Point(3,1),new Point(3,3)}));
        Worker villainWorker = new Worker(Color.BLUE);
        Worker otherWorker = new Worker(Color.RED);
        Point workerPosition = new Point(2,2);
        Point villainWorkerPosition = new Point(2,3);
        Point otherWorkerPosition = new Point(2,1);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition, worker);
        //set position of villain worker and place it on board
        villainWorker.setPosition(villainWorkerPosition);
        board.placeWorker(villainWorkerPosition, villainWorker);
        //set position of other player worker and place it on board
        otherWorker.setPosition(otherWorkerPosition);
        board.placeWorker(otherWorkerPosition, otherWorker);
        actualPosition = apolloTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apollo_Board_With_Blocks_And_Dome_move_Test(){
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));
        Point workerPosition = new Point(2,2);
        Point baseBlockPosition = new Point(1,1);
        Point middleBlockPosition = new Point(3,3);
        Point topBlockPosition = new Point(2,3);
        Point domePosition = new Point(1,2);

        board.init();
        //base block on worker position
        board.addBlock(workerPosition);
        //base block, can move
        board.addBlock(baseBlockPosition);
        //middle block, can move
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

        actualPosition = apolloTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));


    }
    @Test
    public void apollo_Cant_Move_Up_Move_Test(){
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

        actualPosition = apolloTurn.move(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apollo_Apply_Move_On_Empty_Board_Test() {
        Point oldWorkerPosition = new Point(2,2);
        Point newWorkerPosition = new Point(2,3);
        board.init();
        worker.setPosition(oldWorkerPosition);
        board.placeWorker(oldWorkerPosition, worker);
        apolloTurn.applyMove(worker,board,newWorkerPosition);
        assertFalse(apolloTurn.getSharedTurn().isMovedUp());
        assertEquals(newWorkerPosition,worker.getPosition());
        assertTrue(board.hasWorkerOnTop(newWorkerPosition));
        assertFalse(board.hasWorkerOnTop(oldWorkerPosition));
        assertEquals(worker, board.getWorker(newWorkerPosition));
        assertEquals(null, board.getWorker(oldWorkerPosition));
    }

    @Test
    public void apollo_apply_Move_With_Moved_Up_Test() {
        Point oldWorkerPosition = new Point(2,2);
        Point newWorkerPosition = new Point(2,3);
        board.init();
        board.addBlock(newWorkerPosition);
        worker.setPosition(oldWorkerPosition);
        board.placeWorker(oldWorkerPosition, worker);
        apolloTurn.applyMove(worker,board,newWorkerPosition);
        assertTrue(apolloTurn.getSharedTurn().isMovedUp());
        assertEquals(newWorkerPosition,worker.getPosition());
        assertTrue(board.hasWorkerOnTop(newWorkerPosition));
        assertFalse(board.hasWorkerOnTop(oldWorkerPosition));
        assertEquals(worker, board.getWorker(newWorkerPosition));
        assertEquals(null, board.getWorker(oldWorkerPosition));
    }

    @Test
    public void apollo_apply_Move_With_Worker_On_NewPosition_Test() {
        Worker villainWorker = new Worker(Color.BLUE);
        Point oldWorkerPosition = new Point(2,2);
        Point newWorkerPosition = new Point(2,3);
        board.init();
        board.addBlock(newWorkerPosition);
        //position of worker
        worker.setPosition(oldWorkerPosition);
        board.placeWorker(oldWorkerPosition, worker);
        //position of villain worker
        villainWorker.setPosition(newWorkerPosition);
        board.placeWorker(newWorkerPosition, villainWorker);

        apolloTurn.applyMove(worker,board,newWorkerPosition);

        assertTrue(apolloTurn.getSharedTurn().isMovedUp());
        assertEquals(newWorkerPosition,worker.getPosition());
        assertEquals(oldWorkerPosition, villainWorker.getPosition());
        assertTrue(board.hasWorkerOnTop(newWorkerPosition));
        assertTrue(board.hasWorkerOnTop(oldWorkerPosition));
        assertEquals(worker, board.getWorker(newWorkerPosition));
        assertEquals(villainWorker, board.getWorker(oldWorkerPosition));
    }


    @Test
    public void apollo_Empty_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(1,2),new Point(1,3),new Point(2,3),new Point(2,1),new Point(3,2),new Point(3,1),new Point(3,3)}));

        Point workerPosition = new Point(2,2);
        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition,worker);
        actualPosition = apolloTurn.build(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void apollo_Empty_Corner_Board_Build_Test() {
        ArrayList<Point> actualPosition;
        ArrayList<Point> expectedPosition =  new ArrayList<>(Arrays.asList(new Point[]{new Point(1,1),new Point(0,1),new Point(1,0)}));
        Point workerPosition = new Point(0,0);

        board.init();
        worker.setPosition(workerPosition);
        board.placeWorker(workerPosition,worker);
        actualPosition = apolloTurn.build(worker, board);
        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }

    @Test
    public void Apollo_Board_With_Blocks_And_Dome_Build_Test() {
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
        actualPosition = apolloTurn.build(worker, board);

        assertTrue(expectedPosition.containsAll(actualPosition));
        assertTrue(actualPosition.containsAll(expectedPosition));
    }


    @Test
    public void apollo_applyBuild_Test() {
        Point blockPosition = new Point(2,3);
        board.init();

        assertEquals(Block.GROUND,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        apolloTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.BASE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        apolloTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.MIDDLE,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        apolloTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertFalse(board.hasDomeOnTop(blockPosition));

        apolloTurn.applyBuild(worker,board,blockPosition,false);
        assertEquals(Block.TOP,board.getCurrentLevel(blockPosition));
        assertTrue(board.hasDomeOnTop(blockPosition));
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
        apolloTurn.applyMove(worker, board, new Point(3,3));
        assertFalse(apolloTurn.winCondition(worker,board));

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
        apolloTurn.applyMove(worker, board, new Point(3,3));
        assertTrue(apolloTurn.winCondition(worker,board));

    }



}