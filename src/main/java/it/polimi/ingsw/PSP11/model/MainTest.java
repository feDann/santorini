package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainTest {

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
        Player player1 = new Player("darkfado777");
        game.addPlayer(player1);
        game.setNumOfPlayer(2);
        Player player2 = new Player("darkfado666");
        game.addPlayer(player2);

        game.selectGod(0);
        game.selectGod(6);

        player1.setGod(game.selectPlayerGod(0));
        player2.setGod(game.selectPlayerGod(1));

        player1.setPlayerTurn(game.getSharedTurn());
        player2.setPlayerTurn(game.getSharedTurn());

        player1.addWorker(new Worker(Color.RED));
        player1.addWorker(new Worker(Color.RED));

        player2.addWorker(new Worker(Color.BLUE));
        player2.addWorker(new Worker(Color.BLUE));


        Board board = game.getBoard();
        player1.chooseWorker(0).setPosition(new Point(0,0));
        player1.chooseWorker(1).setPosition(new Point(3,3));
        board.placeWorker(new Point(0,0) , player1.chooseWorker(0));
        board.placeWorker(new Point(3,3) , player1.chooseWorker(1));

        player2.chooseWorker(0).setPosition(new Point(2,2));
        player2.chooseWorker(1).setPosition(new Point(1,1));
        board.placeWorker(new Point(2,2) , player2.chooseWorker(0));
        board.placeWorker(new Point(1,1) , player2.chooseWorker(1));

        board.printBoard();

        Scanner scanner = new Scanner(System.in);

        while(!game.isGameEnded()){
            System.out.println("\n\n");
            System.out.println("Turno di " + game.getPlayers().get(game.getIndexOfCurrentPlayer()).getNickname());
            game.getCurrentPlayer().getPlayerTurn().startTurn();
            System.out.println("seleziona il worker da muovere: 0 o 1?");
            System.out.println("coordinate del worker 0: " + game.getCurrentPlayer().chooseWorker(0).getPosition().toString());
            System.out.println("coordinate del worker 1: " + game.getCurrentPlayer().chooseWorker(1).getPosition().toString());

            int chosenWorker = scanner.nextInt();
            ArrayList<Point> possiblieMoves = game.getCurrentPlayer().getPlayerTurn().move(game.getCurrentPlayer().chooseWorker(chosenWorker),board);
            System.out.println("\n\n");
            System.out.println(possiblieMoves.toString());
            System.out.println("inserisici le coordinate in cui vuoi muoverti x,y");
            String chosenPosition = scanner.next();
            int x = Integer.parseInt( chosenPosition.substring(0,1));
            int y = Integer.parseInt( chosenPosition.substring(2,3));
            System.out.println("hai scelto" + x + y);
            game.getCurrentPlayer().getPlayerTurn().applyMove(game.getCurrentPlayer().chooseWorker(chosenWorker), board, new Point(x,y));
            board.printBoard();

            ArrayList<Point> possiblieBuild = game.getCurrentPlayer().getPlayerTurn().build(game.getCurrentPlayer().chooseWorker(chosenWorker),board);
            System.out.println("\n\n");
            System.out.println(possiblieBuild.toString());
            System.out.println("inserisici le coordinate in cui vuoi costruire x,y");
            chosenPosition = scanner.next();
            x = Integer.parseInt( chosenPosition.substring(0,1));
            y = Integer.parseInt( chosenPosition.substring(2,3));
            System.out.println("hai scelto" + x + y);
            game.getCurrentPlayer().getPlayerTurn().applyBuild(game.getCurrentPlayer().chooseWorker(chosenWorker), board, new Point(x,y),false);
            System.out.println("\n\n");
            board.printBoard();

            game.nextPlayer();

        }

    }
}
