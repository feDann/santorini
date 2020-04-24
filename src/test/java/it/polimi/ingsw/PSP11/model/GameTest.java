package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Deck deck;

    @Before
    public void setUp() {
        game = new Game();
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void deckInitTest() {
        game.deckInit();
        deck = game.getDeck();
        assertEquals("Apollo", deck.getCards().get(0).getName());
        assertEquals(1, deck.getCards().get(0).getIdCard());
        assertEquals("Artemis", deck.getCards().get(1).getName());
        assertEquals(2, deck.getCards().get(1).getIdCard());
        assertEquals("Athena", deck.getCards().get(2).getName());
        assertEquals(3, deck.getCards().get(2).getIdCard());
        assertEquals("Atlas", deck.getCards().get(3).getName());
        assertEquals(4, deck.getCards().get(3).getIdCard());
        assertEquals("Demeter", deck.getCards().get(4).getName());
        assertEquals(5, deck.getCards().get(4).getIdCard());
        assertEquals("Hephaestus", deck.getCards().get(5).getName());
        assertEquals(6, deck.getCards().get(5).getIdCard());
        assertEquals("Minotaur", deck.getCards().get(6).getName());
        assertEquals(7, deck.getCards().get(6).getIdCard());
        assertEquals("Pan", deck.getCards().get(7).getName());
        assertEquals(8, deck.getCards().get(7).getIdCard());
        assertEquals("Prometheus", deck.getCards().get(8).getName());
        assertEquals(9, deck.getCards().get(8).getIdCard());

    }

    @Test
    public void setNumOfPlayerTest() {
        game.setNumOfPlayers(3);
        assertEquals(3, game.getNumOfPlayers());
    }

    @Test
    public void addPlayerTest() {
        Player player = new Player("DarkFado777");
        game.addPlayer(player);
        assertTrue(game.getPlayers().contains(player));
        assertEquals(player, game.getPlayers().get(0));

        Player player2 = new Player("DarkFado666");
        game.addPlayer(player2);
        assertTrue(game.getPlayers().contains(player2));
        assertEquals(player2, game.getPlayers().get(1));
    }

    @Test
    public void selectGodTest() {
        game.deckInit();
        game.selectGod(1);
        assertEquals(game.getDeck().getCards().get(1),game.getChosenCards().get(0));
    }

    @Test
    public void selectPlayerGodTest() {
        game.deckInit();
        game.selectGod(3);
        assertEquals(game.getChosenCards().get(0), game.selectPlayerGod(0));
    }


    @Test
    public void isGameStartedTest() {
        assertFalse(game.isGameStarted());
        game.startGame();
        assertTrue(game.isGameStarted());
    }


    @Test
    public void nextPlayerTest() {
        Player player1 = new Player("1");
        Player player2 = new Player("2");
        game.setNumOfPlayers(2);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
        assertEquals(0, game.getIndexOfCurrentPlayer());
        game.nextPlayer();
        assertEquals(1, game.getIndexOfCurrentPlayer());
        game.nextPlayer();
        assertEquals(0, game.getIndexOfCurrentPlayer());
    }

    @Test
    public void getWinnerTest() {
        assertEquals(-1, game.getWinner());
    }

    @Test
    public void setWinnerTest() {
        game.setWinner(1);
        assertEquals(1, game.getWinner());
    }

    @Test
    public void startGameTest() {
        game.startGame();
        assertEquals(0, game.getIndexOfCurrentPlayer());
        deckInitTest();
        assertTrue(game.isGameStarted());
    }
}