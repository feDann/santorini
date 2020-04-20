package it.polimi.ingsw.PSP11.model;

import it.polimi.ingsw.PSP11.messages.UpdateMessage;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.utils.XMLParser;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for the Game
 */

public class Game extends Observable<UpdateMessage> {

    private Board board;
    private ArrayList<Player> players;
    private ArrayList<Card> chosenCards;
    private Deck deck;
    private int indexOfCurrentPlayer;
    private int numOfPlayer;
    private int winner;
    private StandardTurn sharedTurn;
    private boolean gameStarted;
    private boolean gameEnded;
    private final String godCardsXMLPath = "src/main/resources/GodCards.xml";


    /**
     * Class constructor
     */

    public Game (){
        board = new Board();
        players = new ArrayList<>();
        chosenCards = new ArrayList<>();
        deck = null;
        indexOfCurrentPlayer = -1;
        numOfPlayer = -1;
        winner = -1;
        sharedTurn = new StandardTurn();
        gameEnded = false;
        gameStarted = false;
    }

    /**
     * inizialize the deck
     */
    public void deckInit(){
        try {
            deck = XMLParser.deserializeDeckFromXML(godCardsXMLPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return chosenCards.get(indexOfChosenGod);
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
     * @return the index of the current player
     */

    public int getIndexOfCurrentPlayer(){
        return indexOfCurrentPlayer;
    }

    /**
     * Choose the new value for currentPlayer
     */

    public void nextPlayer(){
        if(++indexOfCurrentPlayer >= numOfPlayer){
            indexOfCurrentPlayer = 0;
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

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getChosenCards() {
        return new ArrayList<Card>(chosenCards);
    }

    public StandardTurn getSharedTurn() {
        return sharedTurn;
    }

    public Player getCurrentPlayer(){
        return players.get(indexOfCurrentPlayer);
    }

    private void playerColorInit(){
        for (int i = 0; i < numOfPlayer; i++){
            players.get(i).setColor(Color.values()[i]);
        }
    }

    /**
     * Start the game
     */
    public void startGame(){
        board.init();
        deckInit();
        indexOfCurrentPlayer = 0;
        gameStarted = true;
        playerColorInit();
    }


}
