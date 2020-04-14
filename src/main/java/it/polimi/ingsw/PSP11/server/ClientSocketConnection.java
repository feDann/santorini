package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.NicknameMessage;
import it.polimi.ingsw.PSP11.messages.PlayerSetupMessage;
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
    private static int counter = 0;

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

    private synchronized int getCounter(){
        return counter;
    }

    private synchronized void incrementCounter(){
        counter++;
    }

    @Override
    public void run() {
        ObjectInputStream in;
        Message message;
        String nickname;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            send(new WelcomeMessage());
            message = (NicknameMessage)in.readObject();
            nickname = message.getMessage();
            server.lobby(this, nickname);
            incrementCounter();
            if(nickname.equals(server.getFirstOfWaitlingList())){
                System.out.println(nickname);
                message = (PlayerSetupMessage) in.readObject();
                int numOfPlayers = Integer.parseInt(message.getMessage());
                server.setNumOfPlayers(numOfPlayers);
            }
            while (isActive()){
                //message = (Message) in.readObject();
                //do the notify
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
