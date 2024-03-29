package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.model.Board;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;

import java.awt.*;

public class StateTestInit {

    private Game game;
    private Player p1;
    private Player p2;
    private Player p3;

    public StateTestInit() {
        game = new Game();
        p1 = new Player("p1");
        p2 = new Player("p2");
        p3 = new Player("p3");
    }

    public void start(int numOfPlayer){

        game.setNumOfPlayers(numOfPlayer);
        game.addPlayer(p1);
        game.addPlayer(p2);
        if (numOfPlayer == 3){
            game.addPlayer(p3);
        }
        game.playerColorInit();
        game.startGame();

    }

    public void boardForLose(){
        Board board = game.getBoard();
        Point build1 = new Point(1,1);
        Point build2 = new Point(1,2);
        Point build3 = new Point(1,3);
        Point build4 = new Point(2,3);
        Point build5 = new Point(3,3);
        Point build6 = new Point(3,2);
        Point build7 = new Point(3,1);
        Point build8 = new Point(2,1);

        board.addDome(build1);
        board.addDome(build2);
        board.addDome(build3);
        board.addDome(build4);
        board.addDome(build5);
        board.addDome(build6);
        board.addDome(build7);
        board.addDome(build8);

    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Player getP3() {
        return p3;
    }

    public void setP3(Player p3) {
        this.p3 = p3;
    }
}
