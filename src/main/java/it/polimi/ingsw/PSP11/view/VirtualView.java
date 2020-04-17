package it.polimi.ingsw.PSP11.view;

import it.polimi.ingsw.PSP11.messages.ControllerMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.OpponentMessage;
import it.polimi.ingsw.PSP11.messages.UpdateMessage;
import it.polimi.ingsw.PSP11.model.Player;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.observer.Observer;
import it.polimi.ingsw.PSP11.server.ClientSocketConnection;

public class VirtualView extends Observable<ControllerMessage> implements Observer<UpdateMessage> {

    private ClientSocketConnection connection;
    private String player;

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
    public VirtualView(ClientSocketConnection connection, String opponent, String player) {
        connection.asyncSend(new OpponentMessage(opponent));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    //tres jugadores
    public VirtualView(ClientSocketConnection connection, String opponent1, String opponent2, String player){
        connection.asyncSend(new OpponentMessage(opponent1, opponent2));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
    }

    public String getPlayer() {
        return player;
    }

    public void sendMessage(Message message){
        connection.asyncSend(message);
    }

    @Override
    public void update(UpdateMessage message) {

    }

}
