package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class BoardTest {

    Board board = new Board();
    Point point = new Point();

    @Before
    public void setUp(){
        board.init();
    }


    @Test
    public void initTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                assertFalse(board.hasDomeOnTop(point));
                assertFalse(board.hasWorkerOnTop(point));
            }
        }
    }

    @Test
    public void hasWorkerOnTopTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                assertFalse(board.hasWorkerOnTop(point));
            }
        }

    }

    @Test
    public void hasDomeOnTopTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                assertFalse(board.hasDomeOnTop(point));
            }
        }
    }

    @Test
    public void placeWorkerTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                board.placeWorker(point);
                assertTrue(board.hasWorkerOnTop(point));
            }
        }
    }

    @Test
    public void addDomeTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                board.addDome(point);
                assertTrue(board.hasDomeOnTop(point));
            }
        }
    }

    @Test
    public void addBlockTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                board.addBlock(point);
                assertEquals(Block.BASE, board.getCurrentLevel(point));
            }
        }
    }

    @Test
    public void getCurrentLevelTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                point.setLocation(i, j);
                assertEquals(Block.GROUND, board.getCurrentLevel(point));
            }
        }
    }
}