package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.controller.Controller;
import it.polimi.ingsw.PSP11.messages.ConnectionMessage;
import it.polimi.ingsw.PSP11.messages.TooManyPeopleMessage;
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
import java.util.concurrent.TimeUnit;

public class Server {

    private static final int serverPort = 50000;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(64);
    private Map<String,ClientSocketConnection> waitingList = new HashMap<>();
    private Map<String,ClientSocketConnection> playingList = new HashMap<>();
    private List<String> waitingNameList = new ArrayList<>();
    private List<String> playingNameList = new ArrayList<>();
    int numOfPlayers = -1;

    public Server () throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    public synchronized void setNumOfPlayers (int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
    }

    public synchronized String getFirstOfWaitlingList(){
        return waitingNameList.get(0);
    }

    public synchronized void insertInWaitingList(ClientSocketConnection connection, String nickname){
        waitingList.put(nickname,connection);
        waitingNameList.add(nickname);
    }

    private void bouncer(int startingIndex) throws InterruptedException {
        if(waitingNameList.size() == startingIndex){
            waitingNameList.clear();
            waitingList.clear();
            return;
        }
        for (int i = startingIndex; i < waitingList.size(); i++){
            ClientSocketConnection clientToDisconnect = waitingList.get(waitingNameList.get(i));
            clientToDisconnect.asyncSend(new TooManyPeopleMessage());
            TimeUnit.SECONDS.sleep(1);
            clientToDisconnect.closeConnection();
        }
        waitingList.clear();
        waitingNameList.clear();
    }

    public synchronized void lobby() throws InterruptedException {
        if (waitingList.size() >= numOfPlayers) {
            if (numOfPlayers == 2) {
                String nickname1 = waitingNameList.get(0);
                String nickname2 = waitingNameList.get(1);

                playingNameList.add(nickname1);
                playingNameList.add(nickname2);
                playingList.put(nickname1, waitingList.get(nickname1));
                playingList.put(nickname2, waitingList.get(nickname2));

            }
            if (numOfPlayers == 3) {
                String nickname1 = waitingNameList.get(0);
                String nickname2 = waitingNameList.get(1);
                String nickname3 = waitingNameList.get(2);

                playingNameList.add(nickname1);
                playingNameList.add(nickname2);
                playingNameList.add(nickname3);
                playingList.put(nickname1, waitingList.get(nickname1));
                playingList.put(nickname2, waitingList.get(nickname2));
                playingList.put(nickname3, waitingList.get(nickname3));
            }
            bouncer(numOfPlayers);

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
