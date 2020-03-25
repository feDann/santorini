package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    Cell cell = null;

    @Before
    public void setUp() throws Exception {
        cell = new Cell();
    }

    @After
    public void tearDown() throws Exception {
        cell = null;
    }

    @Test
    public void hasWorkerOnTopTest() {
        assertFalse(cell.hasWorkerOnTop());
    }

    @Test
    public void hasDomeOnTopTest() {
        assertFalse(cell.hasDomeOnTop());
    }

    @Test
    public void placeWorkerTest() {
        cell.placeWorker();
        assertTrue(cell.hasWorkerOnTop());
    }

    @Test
    public void addDomeTest() {
        cell.addDome();
        assertTrue(cell.hasDomeOnTop());
    }

    @Test
    public void addBlockTest() {
        cell.addBlock();
        assertEquals(Block.BASE, cell.getCurrentLevel());
    }

    @Test
    public void getCurrentLevelTest() {
    }
}