package it.polimi.ingsw.PSP11.model;

import java.util.ArrayList;
import java.util.jar.JarOutputStream;

/**
 * Class for the Game
 */

public class Game {

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Card> chosenCards;
    private Deck deck;
    private int currentPlayer;
    private int numOfPlayer;
    private int winner;
    private Turn sharedTurn;
    private boolean gameStarted;
    private boolean gameEnded;
    private boolean gameSetup;

    /**
     * Class constructor
     */

    public Game (){
        board = new Board();
        players = new ArrayList<>();
        chosenCards = new ArrayList<>();
        deck = new Deck();
        currentPlayer = -1;
        numOfPlayer = -1;
        winner = -1;
        //sharedTurn = new ConcreteTurn;
        gameEnded = false;
        gameStarted = false;
    }

    /**
     * Setter method for numOfPlayer attribute
     * @param num the number of player for the game
     */

    public void setNumOfPlayer(int num){
        numOfPlayer = num;
    }

    /**
     * getter method for the number of player
     * @return the number of player
     */

    public int getNumOfPlayer(){
        return numOfPlayer;
    }

    /**
     * Add new player in game
     * @param newPlayer the new player
     */

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    /**
     * Method for select the cards to use in game
     * @param indexOfChosenGod the index of the card to select in Deck class
     */

    public void selectGod(int indexOfChosenGod){
        chosenCards.add( deck.pickGod(indexOfChosenGod));
    }

    /**
     * Method for select the player's card
     * @param indexOfChosenGod the index of the chosen card
     * @return the chosen Card object
     */

    public Card selectPlayerGod (int indexOfChosenGod){
        return deck.pickGod(indexOfChosenGod);
    }

    /**
     * getter method for the board class
     * @return the board
     */

    public Board getBoard(){
        return board;
    }

    /**
     *
     * @return the value of gameStarted
     */

    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     *
     * @return the value of gameEnded
     */

    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * Set the gameEnded attribute to the new param
     * @param gameEnded true if the game is ended , false otherwise
     */

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    /**
     *
     * @return the currentPlayer value
     */

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * Choose the new value for currentPlayer
     */

    public void nextPlayer(){
        if(++currentPlayer >= numOfPlayer){
            currentPlayer = 0;
        }
    }

    /**
     *
     * @return if there is a winner, the index of the winner, -1 otherwise
     */

    public int getWinner(){
        return winner;
    }

    /**
     * Set the index of the winner to the winner attribute
     * @param indexOfWinner the index of the winner
     */

    public void setWinner(int indexOfWinner){
        winner = indexOfWinner;
    }

    /**
     * Start the game
     */

    public void startGame(){
        board.init();
        currentPlayer = 0;
        gameStarted = true;
    }

}
