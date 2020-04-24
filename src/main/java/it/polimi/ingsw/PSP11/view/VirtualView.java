package it.polimi.ingsw.PSP11.view;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.observer.Observer;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

public class VirtualView extends Observable<ControllerMessage> implements Observer<UpdateMessage> {

    private ClientSocketConnection connection;
    private PlayerInfo player;

    private class MessageReceiver implements Observer<Message> {

        @Override
        public void update(Message message) {
            handleControllerMessage(message);
        }
    }

    private void handleControllerMessage(Message message) {
        notify(new ControllerMessage(message, this));
    }

    //due giocatori
    public VirtualView(ClientSocketConnection connection, String opponent, PlayerInfo player) {
        connection.asyncSend(new OpponentMessage(opponent, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    //tres jugadores
    public VirtualView(ClientSocketConnection connection, String opponent1, String opponent2, PlayerInfo player){
        connection.asyncSend(new OpponentMessage(opponent1, opponent2, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    public PlayerInfo getPlayer() {
        return player;
    }

    public void sendMessage(Message message){
        connection.asyncSend(message);
    }

    @Override
    public void update(UpdateMessage message) {
        updateBoard(message);
    }

    private void updateBoard(UpdateMessage message) {
        connection.asyncSend(new SimpleMessage(message.getBoard().printBoard()));
        if (!player.getName().equals(message.getPlayer().getName())) {
            connection.asyncSend(message.getUpdateMessage());
        }
    }
}
