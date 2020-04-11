package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocketConnection implements Runnable{

    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private boolean active = true;

    public ClientSocketConnection(Socket socket, Server server){
        this.clientSocket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }


    @Override
    public void run() {
        ObjectInputStream in;
        Message message;


        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            send("benvenuto nel gioco coglione!");
            while (isActive()){
                message = (Message) in.readObject();
                //do the notify
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void asyncSend(final Object message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

}
