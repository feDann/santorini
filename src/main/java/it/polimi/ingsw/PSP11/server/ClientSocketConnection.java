package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.*;
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


    public synchronized void closeConnection() {
        send(new ConnectionClosedMessage());
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
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
            message = (NicknameMessage) in.readObject();
            nickname = message.getMessage();
            server.insertInWaitingList(this, nickname);
            if(nickname.equals(server.getFirstOfWaitlingList())){
                send(new ConnectionMessage());
                message = (PlayerSetupMessage) in.readObject();
                int numOfPlayers = Integer.parseInt(message.getMessage());
                server.setNumOfPlayers(numOfPlayers);
            }
            server.lobby();
            while (isActive()){
                //message = (Message) in.readObject();
                //do the notify
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
