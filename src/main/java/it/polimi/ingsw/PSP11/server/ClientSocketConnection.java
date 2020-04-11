package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.WelcomeMessage;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

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

    @Override
    public void run() {
        ObjectInputStream in;
        Message message;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            send(new WelcomeMessage());
            while (isActive()){
                //message = (Message) in.readObject();
                //do the notify
            }
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }

}
