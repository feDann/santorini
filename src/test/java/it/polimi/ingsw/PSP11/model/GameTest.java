package it.polimi.ingsw.PSP11.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
        Player player = new Player("bob");
        player.setColor(Color.RED);
        game.addPlayer(player);
        game.nextPlayer();
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
    public void startGameTest() {
        game.startGame();
        assertEquals(0, game.getIndexOfCurrentPlayer());
        deckInitTest();
        assertTrue(game.isGameStarted());
    }

    @Test
    public void two_Players_playerColorInit_Test() {
        Player p1 = new Player("bob");
        Player p2 = new Player("alice");
        game.setNumOfPlayers(2);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.playerColorInit();
        assertEquals(Color.BLUE, p1.getColor());
        assertEquals(Color.RED, p2.getColor());
    }

    @Test
    public void three_Players_playerColorInit_Test() {
        Player p1 = new Player("bob");
        Player p2 = new Player("alice");
        Player p3 = new Player("pippo");
        game.setNumOfPlayers(3);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);
        game.playerColorInit();
        assertEquals(Color.BLUE, p1.getColor());
        assertEquals(Color.RED, p2.getColor());
        assertEquals(Color.GREEN, p3.getColor());
    }

    @Test
    public void placeWorkerTest() {
        Point p = new Point(2,3);
        Player p1 = new Player("bob");
        p1.setColor(Color.RED);
        Worker w = new Worker(Color.RED);
        w.setId(0);
        game.addPlayer(p1);
        game.startGame();
        w.setPosition(p);
        game.placeWorker(p,w);
        assertTrue(game.getBoard().hasWorkerOnTop(p));
        assertEquals(w, game.getBoard().getWorker(p));
    }

    @Test
    public void moveTest() {
        ArrayList<Point> actualPositions;
        ArrayList<Point> expectedPositions = new ArrayList<>(Arrays.asList(new Point(0,1),new Point(1,1),new Point(1,0)));
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point point = new Point(0,0);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(point);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.placeWorker(point,worker);
        actualPositions = game.move(0);
        assertTrue(expectedPositions.containsAll(actualPositions));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void applyMoveTest() {
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point startPosition = new Point(3,1);
        Point movePosition = new Point(3,2);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(startPosition);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.placeWorker(startPosition,worker);
        game.applyMove(movePosition,0);
        assertTrue(game.getBoard().hasWorkerOnTop(movePosition));
        assertEquals(Color.RED, game.getBoard().getWorker(movePosition).getColor());
        assertFalse(game.getBoard().hasWorkerOnTop(startPosition));
    }

    @Test
    public void buildTest() {
        ArrayList<Point> actualPositions;
        ArrayList<Point> expectedPositions = new ArrayList<>(Arrays.asList(new Point(0,1),new Point(1,1),new Point(1,0)));
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point point = new Point(0,0);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(point);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.placeWorker(point,worker);
        actualPositions = game.build(0);
        assertTrue(expectedPositions.containsAll(actualPositions));
        assertTrue(actualPositions.containsAll(expectedPositions));
    }

    @Test
    public void applyBuildTest() {
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point workerPosition = new Point(3,1);
        Point buildPosition = new Point(3,2);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(workerPosition);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.placeWorker(workerPosition,worker);
        game.applyBuild(buildPosition,0, false);
        assertEquals(Block.BASE, game.getBoard().getCurrentLevel(buildPosition));
    }

    @Test
    public void removeCurrentPlayerWorker() {
        Player player = new Player("bob");
        Worker worker1 = new Worker(Color.RED);
        Worker worker2 = new Worker(Color.RED);
        Point p1 = new Point(2,3);
        Point p2 = new Point(1,0);
        player.setColor(Color.RED);
        worker1.setId(0);
        worker2.setId(1);
        worker1.setPosition(p1);
        worker2.setPosition(p2);
        player.addWorker(worker1);
        player.addWorker(worker2);
        game.addPlayer(player);
        game.startGame();
        game.placeWorker(p1,worker1);
        game.placeWorker(p2,worker2);
        game.removeCurrentPlayerWorker();
        assertFalse(game.getBoard().hasWorkerOnTop(p1));
        assertFalse(game.getBoard().hasWorkerOnTop(p2));
    }

    @Test
    public void checkWinner_False_Test() {
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point startPosition = new Point(3,1);
        Point movePosition = new Point(3,2);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(startPosition);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.placeWorker(startPosition,worker);
        game.applyMove(movePosition,0);
        assertFalse(game.checkWinner(0));
        assertFalse(game.isThereIsAWinner());
    }

    @Test
    public  void checkWinner_True_Test() {
        Player player = new Player("bob");
        Worker worker = new Worker(Color.RED);
        Point startPosition = new Point(3,1);
        Point movePosition = new Point(3,2);
        player.setColor(Color.RED);
        player.addWorker(worker);
        worker.setId(0);
        worker.setPosition(startPosition);
        game.addPlayer(player);
        game.startGame();
        game.selectGod(3);
        player.setGod(game.selectPlayerGod(0));
        player.setPlayerTurn(game.getSharedTurn());
        game.getBoard().addBlock(startPosition);
        game.getBoard().addBlock(startPosition);
        game.placeWorker(startPosition,worker);
        game.getBoard().addBlock(movePosition);
        game.getBoard().addBlock(movePosition);
        game.getBoard().addBlock(movePosition);
        game.applyMove(movePosition,0);
        assertTrue(game.checkWinner(0));
        assertTrue(game.isThereIsAWinner());
    }

}