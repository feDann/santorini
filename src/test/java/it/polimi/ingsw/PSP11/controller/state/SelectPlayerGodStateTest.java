package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.SelectPlayerGodRequest;
import it.polimi.ingsw.PSP11.messages.SelectPlayerGodResponse;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;
import it.polimi.ingsw.PSP11.view.VirtualView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SelectPlayerGodStateTest {

    StateTestInit init;
    SelectPlayerGodState currState;

    @Before
    public void setUp() throws Exception {
        init = new StateTestInit();
    }

    @After
    public void tearDown() throws Exception {
        init = null;
        currState = null;
    }

    @Test
    public void selectPlayerGod_Two_Players() {
        init.start(2);
        init.getGame().selectGod(0);
        init.getGame().selectGod(1);
        currState = new SelectPlayerGodState(init.getGame());
        PlayerInfo opponent = init.getP2().playerClone();
        PlayerInfo player = init.getP1().playerClone();
        int indexOfChosenGod = 0;
        int indexOfCurrentPlayer = init.getGame().getIndexOfCurrentPlayer();

        currState.selectPlayerGod(indexOfChosenGod);

        assertEquals(init.getGame().getChosenCards().get(indexOfChosenGod), init.getGame().getPlayers().get(indexOfCurrentPlayer).getGod());
        assertFalse(currState.chosenCards.contains(init.getGame().getPlayers().get(indexOfCurrentPlayer).getGod()));
    }

    @Test
    public void selectPlayerGod_Three_Players() {
        init.start(3);
        init.getGame().selectGod(0);
        init.getGame().selectGod(1);
        init.getGame().selectGod(2);
        currState = new SelectPlayerGodState(init.getGame());
        PlayerInfo opponent = init.getP2().playerClone();
        PlayerInfo player = init.getP1().playerClone();
        int indexOfChosenGod = 2;
        int indexOfCurrentPlayer = init.getGame().getIndexOfCurrentPlayer();

        currState.selectPlayerGod(indexOfChosenGod);

        assertEquals(init.getGame().getChosenCards().get(indexOfChosenGod), init.getGame().getPlayers().get(indexOfCurrentPlayer).getGod());
        assertFalse(currState.chosenCards.contains(init.getGame().getPlayers().get(indexOfCurrentPlayer).getGod()));
    }

    @Test
    public void stateMessage_Two_Players() {
        init.start(2);
        init.getGame().selectGod(0);
        init.getGame().selectGod(1);
        currState = new SelectPlayerGodState(init.getGame());
        assertTrue(currState.stateMessage() instanceof SelectPlayerGodRequest);
    }

    @Test
    public void stateMessage_Three_Players() {
        init.start(3);
        init.getGame().selectGod(3);
        init.getGame().selectGod(4);
        init.getGame().selectGod(5);
        currState = new SelectPlayerGodState(init.getGame());
        assertTrue(currState.stateMessage() instanceof SelectPlayerGodRequest);
    }

    @Test
    public void execute_Change_State_Two_Players_Expected_Error_During_Send() {
        init.start(2);
        init.getGame().selectGod(0);
        init.getGame().selectGod(1);
        currState = new SelectPlayerGodState(init.getGame());
        PlayerInfo opponent = init.getP2().playerClone();
        PlayerInfo player = init.getP1().playerClone();
        int indexOfChosenGod = 0;

        GameState nextState = currState.execute(new SelectPlayerGodResponse(indexOfChosenGod), new VirtualView(new ClientSocketConnection(null,null), opponent, player));
        assertTrue(nextState instanceof SelectPlayerGodState);

        nextState = currState.execute(new SelectPlayerGodResponse(indexOfChosenGod), new VirtualView(new ClientSocketConnection(null,null), player, opponent));
        assertTrue(nextState instanceof PlaceWorkerState);
    }

    @Test
    public void execute_Change_State_Three_Players_Expected_Error_During_Send() {
        init.start(3);
        init.getGame().selectGod(6);
        init.getGame().selectGod(7);
        init.getGame().selectGod(8);
        currState = new SelectPlayerGodState(init.getGame());
        PlayerInfo opponent1 = init.getP2().playerClone();
        PlayerInfo opponent2 = init.getP3().playerClone();
        PlayerInfo player = init.getP1().playerClone();
        int indexOfChosenGod = 0;

        GameState nextState = currState.execute(new SelectPlayerGodResponse(indexOfChosenGod), new VirtualView(new ClientSocketConnection(null,null), opponent1, opponent2, player));
        assertTrue(nextState instanceof SelectPlayerGodState);

        nextState = currState.execute(new SelectPlayerGodResponse(indexOfChosenGod), new VirtualView(new ClientSocketConnection(null,null), player, opponent2, opponent1));
        assertTrue(nextState instanceof SelectPlayerGodState);

        nextState = currState.execute(new SelectPlayerGodResponse(indexOfChosenGod), new VirtualView(new ClientSocketConnection(null,null), player, opponent1, opponent2));
        assertTrue(nextState instanceof PlaceWorkerState);
    }

}