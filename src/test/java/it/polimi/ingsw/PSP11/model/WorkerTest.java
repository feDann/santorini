package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static it.polimi.ingsw.PSP11.model.Color.RED;
import static org.junit.Assert.*;

public class WorkerTest {

    Worker worker;
    Point point;

    @Before
    public void setUp()  {
       worker = new Worker(RED);
       point = new Point();
       point.setLocation(2, 1);
    }

    @After
    public void tearDown()  {
        worker = null;
        point = null;
    }

    @Test
    public void setPosition() {
        worker.setPosition(point);
        assertEquals(point, worker.getPosition());
    }

    @Test
    public void setMoved() {
        //check for true result
        boolean moved = true;
        worker.setMoved(moved);
        assertTrue(worker.isMoved());
        //check for false result
        moved = false;
        worker.setMoved(moved);
        assertFalse(worker.isMoved());
    }


    @Test
    public void getColor() {
        assertEquals(RED, worker.getColor());
    }
}