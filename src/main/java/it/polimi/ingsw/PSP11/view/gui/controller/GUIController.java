package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.messages.Message;

public abstract class GUIController {
    private GUIClient client;

    public abstract void changeScene();
    public abstract void handleMessage(Message message);

    public void send(Message message){
        client.asyncSend(message);
    }

    public GUIClient getClient() {
        return client;
    }

    public void setClient(GUIClient client) {
        this.client = client;
    }
}
