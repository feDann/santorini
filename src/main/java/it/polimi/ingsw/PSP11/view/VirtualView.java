package it.polimi.ingsw.PSP11.view;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.observer.Observer;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

public class VirtualView extends Observable<ControllerMessage> implements Observer<UpdateMessage> {

    private ClientSocketConnection connection;
    private PlayerInfo player;

    private class MessageReceiver implements Observer<Message> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void update(Message message) {
            handleControllerMessage(message);
        }
    }

    /**
     * Notify the message received from the client to the controller
     * @param message the {@link Message} received from the client
     */

    private void handleControllerMessage(Message message) {
        notify(new ControllerMessage(message, this));
    }

    /**
     * Allocate a new virtualView Object for two players game
     * @param connection the {@link ClientSocketConnection} of the player
     * @param opponent the opponent, used to create a {@link OpponentMessage}
     * @param player the {@link PlayerInfo} object used to represent the player
     */
    public VirtualView(ClientSocketConnection connection, PlayerInfo opponent, PlayerInfo player) {
        connection.send(new OpponentMessage(opponent, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
        sendMessage(null);
    }

    /**
     * Allocate a new virtualView Object for three players game
     * @param connection the {@link ClientSocketConnection} of the player
     * @param opponent1 the first opponent, used to create a {@link OpponentMessage}
     * @param opponent2 the second opponent, used to create a {@link OpponentMessage}
     * @param player the {@link PlayerInfo} object used to represent the player
     */
    public VirtualView(ClientSocketConnection connection, PlayerInfo opponent1, PlayerInfo opponent2, PlayerInfo player){
        connection.send(new OpponentMessage(opponent1, opponent2, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    /**
     * Get method for {@link VirtualView#player} attribute
     * @return the {@link VirtualView#player} attribute
     */
    public PlayerInfo getPlayer() {
        return player;
    }

    /**
     * Send a message to the client
     * @param message the message to sent at the client
     */
    public void sendMessage(Message message){
        connection.send(message);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void update(UpdateMessage message) {
        if(message.getUpdateMessage() instanceof StartGameMessage){
            startGameUpdate(message);

        }else {
            updatePlayerView(message);
        }
    }

    /**
     * Handle the {@link StartGameMessage} to optimize the visualization on CLI and send an update message to the client
     * @param message the {@link StartGameMessage} object
     */

    private void startGameUpdate(UpdateMessage message){
        connection.send(message.getUpdateMessage());
        if(player.getName().equals(message.getPlayer().getName())){
            connection.send(new BoardUpdate(message.getBoard()));
        }
        else{
            connection.send(new SimpleMessage("\n\n\n"+message.getPlayer().getColor().getEscape() + message.getPlayer().getName() + Color.RESET + " started his turn!\n"));
        }
    }

    /**
     * Update the client side view sending update message to client
     * @param message the {@link UpdateMessage} to send at the client
     */

    private void updatePlayerView(UpdateMessage message) {
        if (message.getBoard() != null){
            connection.send(new BoardUpdate(message.getBoard()));
        }
        if (!player.getName().equals(message.getPlayer().getName())) {
            connection.send(message.getUpdateMessage());
        }
    }
}
