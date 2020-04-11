package it.polimi.ingsw.PSP11.model;

import it.polimi.ingsw.PSP11.utils.XMLParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    Card card ;
    Deck deck;
    StandardTurn turn;
    @Before
    public void setUp() throws Exception {
        deck = XMLParser.deserializeDeckFromXML("src/main/resources/GodCards.xml");
        card = deck.pickGod(0);
        turn = new StandardTurn();
    }

    @After
    public void tearDown() throws Exception {
        deck = null;
        card = null;
        turn = null;
    }

    @Test
    public void setTurn_Test() {
        card.setTurn(turn);
        //assertTrue( card.getGodTurnDecorator() instanceof ApolloPowerTurnDecorator);
        assertEquals(turn, card.getGodTurnDecorator().getSharedTurn());
    }
}