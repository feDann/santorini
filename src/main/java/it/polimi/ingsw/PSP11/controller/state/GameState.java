package it.polimi.ingsw.PSP11.controller.state;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.awt.*;
import java.util.ArrayList;

public interface GameState {

    /**
     * method used to allow the challenger to select the gods that will be used in the game
     * @param ids arraylist of all the possibles gods
     */
    public void selectGameGods(ArrayList<Integer> ids);

    /**
     * method used to allow the player to chose his god
     * @param index of the chosen god
     */
    public void selectPlayerGod(int index);

    /**
     * method used to place the worker during the setup of the game
     * @param point the desired location of the worker
     */
    public void placeWorker(Point point);

    /**
     * method used to check if some god powers can be activated
     * also check for eventual player who have lost
     */
    public void startTurn();

    /**
     * method used to select the worker that the player wants to utilize this turn
     */
    public void selectWorker();

    /**
     * method used to give the player all the possible move position
     */
    public void moveWorker();

    /**
     * method used to check if someone lost the game
     * @return true if there is a loser
     */
    public boolean checkLose();

    /**
     * method used to actually move the worker to the desired place
     * @param point in witch the player wants to move
     */
    public void applyMove(Point point);

    /**
     * method used to check if there is a winner
     * @return true is there is a winner
     */
    public boolean checkWin();

    /**
     * method used to give the player all the possible build position
     */
    public void workerBuild();

    /**
     * method used to actually build in the desired place
     * @param point in witch the player wants to move
     * @param forceBuildDome true if atlas power is activated
     */
    public void applyBuild(Point point, boolean forceBuildDome);


    public void endTurn();

    /**
     * method used to send the player the right message
     * @return the message to send to the player
     */
    public Message stateMessage();

    /**
     * method used to decide witch action to do within the game state, based on the god's power
     * @param message the player last received
     * @param virtualView of the current player
     * @return the new state of the game
     */
    public GameState execute(Message message, VirtualView virtualView);
}