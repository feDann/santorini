package it.polimi.ingsw.PSP11.model;

import it.polimi.ingsw.PSP11.messages.BuildUpdateMessage;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;
import it.polimi.ingsw.PSP11.messages.UpdateMessage;
import it.polimi.ingsw.PSP11.messages.WorkerUpdateMessage;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.utils.XMLParser;

import java.awt.*;
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
    private int numOfPlayers;
    private boolean thereIsAWinner;
    private StandardTurn sharedTurn;
    private boolean gameStarted;
    private boolean gameEnded;
    private final String godCardsXMLPath = "xml/GodCards.xml";
    private boolean thereIsALooser;

    /**
     * Class constructor
     */

    public Game (){
        board = new Board();
        players = new ArrayList<>();
        chosenCards = new ArrayList<>();
        deck = null;
        indexOfCurrentPlayer = -1;
        numOfPlayers = -1;
        sharedTurn = new StandardTurn();
        gameEnded = false;
        gameStarted = false;
        thereIsALooser = false;
        thereIsAWinner = false;
    }

    /**
     * inizialize the deck
     */
    public void deckInit(){
        try {
            deck = XMLParser.deserializeDeckFromXML(godCardsXMLPath);
        } catch (IOException e) {
            System.err.println("Impossible to deserialize Deck! Error: " +e.getMessage());
        }
    }

    /**
     * Setter method for numOfPlayer attribute
     * @param num the number of player for the game
     */

    public void setNumOfPlayers(int num){
        numOfPlayers = num;
    }

    /**
     * getter method for the number of player
     * @return the number of player
     */

    public int getNumOfPlayers(){
        return numOfPlayers;
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
        notify(new UpdateMessage(null,getCurrentPlayer().playerClone(),new SimpleMessage(getCurrentPlayer().getColor().getEscape() + getCurrentPlayer().getNickname() + Color.RESET + " has chosen " + chosenCards.get(indexOfChosenGod).getName() + "\n")));
        return chosenCards.get(indexOfChosenGod);
    }

    /**
     * getter method for the board class
     * @return the board
     */

    public Board getBoard(){
        return board;
    }

    public Board boardClone(){
        return board.boardClone();
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
        if(++indexOfCurrentPlayer >= numOfPlayers){
            indexOfCurrentPlayer = 0;
        }
    }

    public boolean isThereIsAWinner() {
        return thereIsAWinner;
    }

    public void setThereIsAWinner(boolean thereIsAWinner) {
        this.thereIsAWinner = thereIsAWinner;
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

    public void playerColorInit(){
        for (int i = 0; i < numOfPlayers; i++){
            players.get(i).setColor(Color.values()[i]);
        }
    }

    public void placeWorker(Point point, Worker worker){
        board.placeWorker(point, worker);
        notify(new UpdateMessage(boardClone(), getCurrentPlayer().playerClone(), new WorkerUpdateMessage(getCurrentPlayer().playerClone(), point)));
    }

    public void notifyBoard() {
        notify(new UpdateMessage(boardClone(), getCurrentPlayer().playerClone(), new SimpleMessage("\nLET THE GAME BEGIN!\n")));
    }

    public void startTurn(){
        getCurrentPlayer().getPlayerTurn().startTurn();
    }

    public ArrayList<Point> move(int workerID){
        Worker worker = getCurrentPlayer().getWorkers().get(workerID);
        return getCurrentPlayer().getPlayerTurn().move(worker,board);
    }

    public void applyMove(Point point, int workerID){
        Worker worker = getCurrentPlayer().getWorkers().get(workerID);
        getCurrentPlayer().getPlayerTurn().applyMove(worker, board, point);
        notify(new UpdateMessage(boardClone(), getCurrentPlayer().playerClone(), new WorkerUpdateMessage(getCurrentPlayer().playerClone(), point)));
    }

    public ArrayList<Point> build(int workerID){
        Worker worker = getCurrentPlayer().getWorkers().get(workerID);
        return getCurrentPlayer().getPlayerTurn().build(worker,board);
    }

    public void applyBuild(Point point, int workerID, boolean forceBuildDome){
        Worker worker = getCurrentPlayer().getWorkers().get(workerID);
        getCurrentPlayer().getPlayerTurn().applyBuild(worker, board, point,forceBuildDome);
        notify(new UpdateMessage(boardClone(), getCurrentPlayer().playerClone(), new BuildUpdateMessage(getCurrentPlayer().playerClone(), point)));
    }

    public boolean isThereIsALooser() {
        return thereIsALooser;
    }

    public void setThereIsALooser(boolean thereIsALooser) {
        this.thereIsALooser = thereIsALooser;
    }

    public void removeCurrentPlayerWorker(){
        for (Worker worker : this.getCurrentPlayer().getWorkers()){
            this.getBoard().removeWorker(worker.getPosition());
        }
        notify(new UpdateMessage(boardClone(), getCurrentPlayer().playerClone(), new SimpleMessage(getCurrentPlayer().getNickname() + "'s workers has been removed!\n")));
    }

    public void removeCurrentPlayer(){
        players.remove(getCurrentPlayer());
    }

    public boolean checkWinner(int workerID){
        Worker worker = getCurrentPlayer().getWorkers().get(workerID);
        if (getCurrentPlayer().getPlayerTurn().winCondition(worker, board)) {
            thereIsAWinner = true;
            return true;
        }
        return false;
    }

    public void endTurn(){
        notify(new UpdateMessage(null, getCurrentPlayer().playerClone(), new SimpleMessage(getCurrentPlayer().getColor().getEscape() + getCurrentPlayer().getNickname() + Color.RESET + " ended his turn\n")));
    }
    /**
     * Start the game
     */
    public void startGame(){
        board.init();
        deckInit();
        indexOfCurrentPlayer = 0;
        gameStarted = true;
    }
}