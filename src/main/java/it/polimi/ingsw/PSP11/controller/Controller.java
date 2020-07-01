package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.controller.state.SelectGameGodsState;
import it.polimi.ingsw.PSP11.controller.state.StartTurnState;
import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.observer.Observer;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.util.HashMap;
import java.util.Map;

public class Controller implements Observer<ControllerMessage> {

    private GameState gameState;
    private final Game game;
    private String requestingPlayer;
    private Map<String, ClientSocketConnection> currentPlayers = new HashMap<>();
    private VirtualView requestingView;

    /**
     * Allocate a new Controller object
     * @param game the current game
     * @param currentPlayers the current player
     */
    public Controller(Game game, Map<String, ClientSocketConnection> currentPlayers) {
        this.game = game;
        this.currentPlayers = currentPlayers;
    }

    /**
     * This method kill the game
     */
    private void endGame(){
        String nickname = game.getCurrentPlayer().getNickname();
        currentPlayers.get(nickname).killGame(nickname);
        currentPlayers.clear();
    }

    /**
     * This method is called when someone lose, if is a three player game kicks out the loser, otherwise kill the game
     */
    private void loseConditionHandler() {
        if (game.getNumOfPlayers() == 2){
            game.nextPlayer();
            currentPlayers.get(game.getCurrentPlayer().getNickname()).send(new WinMessage());
            endGame();
        }
        if (game.getNumOfPlayers() == 3){
            String playerToKill = game.getCurrentPlayer().getNickname();
            for (String player : currentPlayers.keySet()){
                if (! player.equals(playerToKill)){
                    currentPlayers.get(player).send(new UpdateLoseMessage(playerToKill));
                }
            }
            currentPlayers.get(playerToKill).goCommitDie(playerToKill);
            currentPlayers.remove(playerToKill);
            game.removeCurrentPlayerWorker();
            game.removeCurrentPlayer();
            game.setNumOfPlayers(2);
            if (game.getIndexOfCurrentPlayer() == game.getNumOfPlayers()){
                game.nextPlayer();
            }
        }
        game.setThereIsALoser(false);
    }

    /**
     * This method is called when someone won the game, send to the all non-winning player the LoseMessage
     */
    private void winConditionHandler(){
        String winner = game.getCurrentPlayer().getNickname();
        for (String player : currentPlayers.keySet()){
            if (! player.equals(winner)){
                currentPlayers.get(player).send(new LoseMessage(winner));
            }
        }
        endGame();
    }

    /**
     * This method handle the message received from the client and manage the state machine
     * @param message from the client
     */
    public synchronized void readMessage(ControllerMessage message){
        requestingView = message.getVirtualView();
        requestingPlayer = requestingView.getPlayer().getName();
        if (requestingPlayer.equals(game.getCurrentPlayer().getNickname())){
            gameState = gameState.execute(message.getMessage(),requestingView);
            currentPlayers.get(game.getCurrentPlayer().getNickname()).send(gameState.stateMessage());
            if (game.isThereIsALoser()){
                loseConditionHandler();
                gameState = new StartTurnState(game);
                currentPlayers.get(game.getCurrentPlayer().getNickname()).send(gameState.stateMessage());
            }
            if (game.isThereIsAWinner()){
                winConditionHandler();
            }
        }
        else{
            requestingView.sendMessage(new NotYourTurnMessage(game.getCurrentPlayer().getNickname()));
        }
    }

    /**
     * function used to start the game
     */
    public void start (){
        ClientSocketConnection firstPlayerConnection;
        game.startGame();
        this.gameState = new SelectGameGodsState(game);
        firstPlayerConnection = currentPlayers.get(game.getCurrentPlayer().getNickname());
        firstPlayerConnection.send(gameState.stateMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ControllerMessage message) {
        readMessage(message);
    }
}
