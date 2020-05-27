package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.SelectGameGodResponse;
import it.polimi.ingsw.PSP11.messages.SelectGameGodsRequest;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.view.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SelectGameGodsStateTest {

    StateTestInit init = new StateTestInit();

    GameState state;

    @Before
    public void setUp() throws Exception {
        init.start(3);
        state = new SelectGameGodsState(init.getGame());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute_Change_State_Expected_Error_During_Send() {
        GameState newState = state.execute(new SelectGameGodResponse(new ArrayList<>(Arrays.asList(1,2,3))), new VirtualView(new ClientSocketConnection(null, null), init.getP3().playerClone(), init.getP2().playerClone(), init.getP1().playerClone()));
        assertEquals(init.getGame().getDeck().getCards().get(1).getIdCard(),init.getGame().getChosenCards().get(0).getIdCard());
        assertEquals(init.getGame().getDeck().getCards().get(2).getIdCard(),init.getGame().getChosenCards().get(1).getIdCard());
        assertEquals(init.getGame().getDeck().getCards().get(3).getIdCard(),init.getGame().getChosenCards().get(2).getIdCard());
        assertTrue(newState instanceof SelectPlayerGodState);
    }

    @Test
    public void stateMessage(){
        assertTrue(state.stateMessage() instanceof SelectGameGodsRequest);
    }



}