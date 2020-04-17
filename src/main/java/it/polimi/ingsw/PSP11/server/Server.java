package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.client.Client;
import it.polimi.ingsw.PSP11.controller.Controller;
import it.polimi.ingsw.PSP11.messages.ConnectionMessage;
import it.polimi.ingsw.PSP11.messages.TooManyPeopleMessage;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
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
    private Map<ClientSocketConnection, ArrayList<ClientSocketConnection>> playingConnections = new HashMap<>();
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
                ClientSocketConnection connection1 = waitingList.get(nickname1);
                ClientSocketConnection connection2 = waitingList.get(nickname2);

                playingNameList.add(nickname1);
                playingNameList.add(nickname2);
                playingList.put(nickname1, connection1);
                playingList.put(nickname2, connection2);

                Player player1 = new Player(nickname1);
                Player player2 = new Player(nickname2);

                VirtualView vv1 = new VirtualView(connection1, nickname2, nickname1);
                VirtualView vv2 = new VirtualView(connection2, nickname1, nickname2);

                Game game = new Game();
                game.setNumOfPlayer(numOfPlayers);
                game.addPlayer(player1);
                game.addPlayer(player2);

                Controller controller = new Controller(game);
                game.addObserver(vv1);
                game.addObserver(vv2);
                vv1.addObserver(controller);
                vv2.addObserver(controller);

                playingConnections.put(connection1, new ArrayList<>(Collections.singletonList(connection2)));
                playingConnections.put(connection2, new ArrayList<>(Collections.singletonList(connection1)));


            }
            if (numOfPlayers == 3) {
                String nickname1 = waitingNameList.get(0);
                String nickname2 = waitingNameList.get(1);
                String nickname3 = waitingNameList.get(2);
                ClientSocketConnection connection1 = waitingList.get(nickname1);
                ClientSocketConnection connection2 = waitingList.get(nickname2);
                ClientSocketConnection connection3 = waitingList.get(nickname3);

                playingNameList.add(nickname1);
                playingNameList.add(nickname2);
                playingNameList.add(nickname3);
                playingList.put(nickname1, connection1);
                playingList.put(nickname2, connection2);
                playingList.put(nickname3, connection3);

                Player player1 = new Player(nickname1);
                Player player2 = new Player(nickname2);
                Player player3 = new Player(nickname3);

                VirtualView vv1 = new VirtualView(connection1, nickname2, nickname3, nickname1);
                VirtualView vv2 = new VirtualView(connection2, nickname1, nickname3, nickname2);
                VirtualView vv3 = new VirtualView(connection3, nickname1, nickname2, nickname3);

                Game game = new Game();
                game.setNumOfPlayer(numOfPlayers);
                game.addPlayer(player1);
                game.addPlayer(player2);
                game.addPlayer(player3);

                Controller controller = new Controller(game);
                game.addObserver(vv1);
                game.addObserver(vv2);
                game.addObserver(vv3);
                vv1.addObserver(controller);
                vv2.addObserver(controller);
                vv3.addObserver(controller);

                playingConnections.put(connection1, new ArrayList<>(Arrays.asList(connection2, connection3)));
                playingConnections.put(connection2, new ArrayList<>(Arrays.asList(connection1, connection3)));
                playingConnections.put(connection3, new ArrayList<>(Arrays.asList(connection1, connection2)));

            }
            bouncer(numOfPlayers);
            numOfPlayers = -1;


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
