package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.controller.Controller;
import it.polimi.ingsw.PSP11.messages.ConnectionMessage;
import it.polimi.ingsw.PSP11.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int serverPort = 50000;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(64);
    private Map<ClientSocketConnection,String> waitingList = new HashMap<>();

    int numOfPlayers = -1;

    public Server () throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    public synchronized void setNumOfPlayers (int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    public synchronized String getFirstOfWaitlingList(){
        List<String> waitingPlayers = new ArrayList<>(waitingList.values());
        return waitingPlayers.get(0);
    }

    public synchronized void lobby(ClientSocketConnection connection, String nickname){
        waitingList.put(connection,nickname);
        if(waitingList.size() == 1){
            connection.asyncSend(new ConnectionMessage());
        }

    }

    public void start(){
        System.out.println("The Santorini server is up and running...");
        while (true){
            try {
                Socket newClientSocket = serverSocket.accept();
                //System.out.println("\nAccepted new client on port: " + newClientSocket.getPort() + "\n");
                ClientSocketConnection socketConnection = new ClientSocketConnection(newClientSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.err.println("Cannot connect the client " + e.getMessage() + "!");
            }

        }
    }


}
