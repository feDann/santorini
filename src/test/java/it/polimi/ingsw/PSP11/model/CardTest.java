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
        deck = XMLParser.deserializeDeckFromXML("xml/GodCards.xml");
        if(deck == null) System.out.println("null");
        card = deck.pickGod(0);
        turn = new StandardTurn();
    }

    @After
    public void tearDown() {
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

    @Test
    public void cardClone_Test() {
        int indexOfCardToClone = 4;
        Card clonedCard = deck.pickGod(indexOfCardToClone).cardClone();
        assertEquals(deck.pickGod(indexOfCardToClone).getName(), clonedCard.getName());
        assertEquals(deck.pickGod(indexOfCardToClone).getIdCard(), clonedCard.getIdCard());
        assertEquals(deck.pickGod(indexOfCardToClone).getDescription(), clonedCard.getDescription());
    }

}