package it.polimi.ingsw.PSP11.controller;

import it.polimi.ingsw.PSP11.controller.state.GameState;
import it.polimi.ingsw.PSP11.controller.state.SelectGameGodsState;
import it.polimi.ingsw.PSP11.controller.state.StartTurnState;
import it.polimi.ingsw.PSP11.messages.ControllerMessage;
import it.polimi.ingsw.PSP11.messages.LoseMessage;
import it.polimi.ingsw.PSP11.messages.NotYourTurnMessage;
import it.polimi.ingsw.PSP11.messages.WinMessage;
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


    public Controller(Game game, Map<String, ClientSocketConnection> currentPlayers) {
        this.game = game;
        this.currentPlayers = currentPlayers;
    }

    private void endGame(){
        String nickname = game.getCurrentPlayer().getNickname();
        currentPlayers.get(nickname).killGame(nickname);
        currentPlayers.clear();
    }

    private void looseConditionHandler() {
        if (game.getNumOfPlayers() == 2){
            game.nextPlayer();
            currentPlayers.get(game.getCurrentPlayer().getNickname()).send(new WinMessage());
            endGame();
        }
        if (game.getNumOfPlayers() == 3){
            String playerToKill = game.getCurrentPlayer().getNickname();
            for (String player : currentPlayers.keySet()){
                if (! player.equals(playerToKill)){
                    currentPlayers.get(player).send(new LoseMessage(playerToKill));
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

    private void winConditionHandler(){
        String winner = game.getCurrentPlayer().getNickname();
        for (String player : currentPlayers.keySet()){
            if (! player.equals(winner)){
                currentPlayers.get(player).send(new WinMessage(winner));
            }
        }
        endGame();
    }


    public synchronized void readMessage(ControllerMessage message){
        requestingView = message.getVirtualView();
        requestingPlayer = requestingView.getPlayer().getName();
        if (requestingPlayer.equals(game.getCurrentPlayer().getNickname())){
            gameState = gameState.execute(message.getMessage(),requestingView);
            currentPlayers.get(game.getCurrentPlayer().getNickname()).send(gameState.stateMessage());
            //TODO
            if (game.isThereIsALoser()){
                looseConditionHandler();
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


    public void start (){
        ClientSocketConnection firstPlayerConnection;
        game.startGame();
        this.gameState = new SelectGameGodsState(game, gameState);
        firstPlayerConnection = currentPlayers.get(game.getCurrentPlayer().getNickname());
        firstPlayerConnection.send(gameState.stateMessage());
    }

    @Override
    public void update(ControllerMessage message) {
        readMessage(message);
    }
}
