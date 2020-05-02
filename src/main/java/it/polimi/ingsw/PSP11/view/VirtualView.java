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

        @Override
        public void update(Message message) {
            handleControllerMessage(message);
        }
    }

    private void handleControllerMessage(Message message) {
        notify(new ControllerMessage(message, this));
    }

    //due giocatori
    public VirtualView(ClientSocketConnection connection, PlayerInfo opponent, PlayerInfo player) {
        connection.send(new OpponentMessage(opponent, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    //tres jugadores
    public VirtualView(ClientSocketConnection connection, PlayerInfo opponent1, PlayerInfo opponent2, PlayerInfo player){
        connection.send(new OpponentMessage(opponent1, opponent2, player.getColor()));
        connection.addObserver(new MessageReceiver());
        this.connection = connection;
        this.player = player;
    }

    public PlayerInfo getPlayer() {
        return player;
    }

    public void sendMessage(Message message){
        connection.send(message);
    }

    @Override
    public void update(UpdateMessage message) {
        //TODO trovare un metodo migliore
        if(message.getUpdateMessage() instanceof StartGameMessage){
            startGameUpdate(message);

        }else {
            updatePlayerView(message);
        }
    }

    public void startGameUpdate(UpdateMessage message){
        connection.send(message.getUpdateMessage());
        if(player.getName().equals(message.getPlayer().getName())){
            connection.send(new SimpleMessage(message.getBoard().printBoard()));
        }
        else{
            connection.send(new SimpleMessage("\n\n\n"+message.getPlayer().getColor().getEscape() + message.getPlayer().getName() + Color.RESET + " started his turn!\n"));
        }
    }

    private void updatePlayerView(UpdateMessage message) {
        if (message.getBoard() != null){
            connection.send(new SimpleMessage(message.getBoard().printBoard()));
        }
        if (!player.getName().equals(message.getPlayer().getName())) {
            connection.send(message.getUpdateMessage());
        }
    }
}
