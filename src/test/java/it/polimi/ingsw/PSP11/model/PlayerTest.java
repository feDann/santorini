package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player = null;

    @Before
    public void setUp() {
        player = new Player("Bob");
    }

    @After
    public void tearDown() {
        player = null;
    }

    @Test
    public void eliminateTest() {
        player.eliminate();
        assertTrue(player.isEliminated());
    }

    @Test
    public void setGodTest() {

    }

    @Test
    public void setPlayerTurnTest() {

    }

    @Test
    public void chooseWorkerTest() {
        Worker worker = new Worker(Color.RED);
        player.addWorker(worker);
        assertEquals(worker, player.chooseWorker(0));
    }

    @Test
    public void addWorkerTest() {
        Worker worker1 = new Worker(Color.BLUE);
        Worker worker2 = new Worker(Color.BLUE);
        player.addWorker(worker1);
        player.addWorker(worker2);
        assertEquals(worker1, player.chooseWorker(0));
        assertEquals(worker2, player.chooseWorker(1));
        assertEquals(Color.BLUE, player.chooseWorker(0).getColor());
        assertEquals(Color.BLUE, player.chooseWorker(1).getColor());
    }

}