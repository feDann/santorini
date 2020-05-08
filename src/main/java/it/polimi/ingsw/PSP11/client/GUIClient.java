package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.view.gui.controller.GUIController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GUIClient {
    private String ip;
    private int port = 50000;
    private boolean active = false;

    private GUIController guiController;

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Socket socket;


    public GUIClient(String ip) {
        this.ip = ip;
    }

    public void start() throws IOException {
        socket = new Socket(ip,port);
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());

    }


    public void asyncSend(Message message) {
    }

    public void setController(GUIController controller) {
        this.guiController = controller;
    }
}
