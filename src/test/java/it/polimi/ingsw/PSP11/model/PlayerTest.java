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
    public void eliminate() {
        player.eliminate();
        assertTrue(player.isEliminated());
    }

    @Test
    public void setGod() {
    }

    @Test
    public void setPlayerTurn() {
    }

    @Test
    public void chooseWorker() {
    }
}