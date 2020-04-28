package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.server.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketConnection extends Observable<Message> implements Runnable{

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
        String nickname = "";
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            send(new WelcomeMessage());
            message = (NicknameMessage) in.readObject();
            nickname = message.getMessage();
            //control for different nicknames
            while (!server.insertInWaitingList(this, nickname)){
                send(new DuplicateNicknameMessage());
                message = (NicknameMessage) in.readObject();
                nickname = message.getMessage();
            }
            //set the num of player for matchmaking
            send(new ConnectionMessage());
            message = (PlayerSetupMessage) in.readObject();
            int numOfPlayers = Integer.parseInt(message.getMessage());
            //choose the correct method
            if(numOfPlayers == 2){
                server.lobbyForTwoPlayer(nickname, this);
            }else{//in this case numofplayer ==3
                server.lobbyForThreePlayer(nickname, this);
            }

            while (isActive()){
                message = (Message) in.readObject();
                notify(message);
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.err.println(e.getMessage());
            server.killLobby(nickname);
        }
    }

}