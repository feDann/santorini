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


    public void setController(GUIController controller) {
        this.guiController = controller;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void asyncRead(){
        new Thread(){
            @Override
            public void run() {
                try{
                    while(isActive()){
                        Message msg = (Message) socketIn.readObject();
                        //System.out.println(msg.getMessage());
                        guiController.handleMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }.start();
    }

    public GUIController getGuiController() {
        return guiController;
    }

    public void asyncWrite(Message message) {
        new Thread(){
            @Override
            public void run() {
                try {
                    socketOut.writeObject(message);
                    socketOut.flush();
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }.start();
    }


    public void close() throws IOException {
        setActive(false);
        socketIn.close();
        socketOut.close();
        socket.close();
    }


    public void start() throws IOException {
        socket = new Socket(ip,port);
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        setActive(true);
        asyncRead();

    }


}
