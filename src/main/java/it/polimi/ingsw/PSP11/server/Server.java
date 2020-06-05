package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.controller.Controller;
import it.polimi.ingsw.PSP11.messages.WaitMessage;
import it.polimi.ingsw.PSP11.model.Game;
import it.polimi.ingsw.PSP11.model.Player;
import it.polimi.ingsw.PSP11.utils.TimeStamp;
import it.polimi.ingsw.PSP11.view.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Server {

    private static final int serverPort = 50000;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(64);
    private Map<String,ClientSocketConnection> waitingListForTwo = new HashMap<>();
    private Map<String,ClientSocketConnection> waitingListForThree = new HashMap<>();
    private Map<String,ClientSocketConnection> playingList = new HashMap<>();
    private List<String> waitingNameList = new ArrayList<>();
    private List<String> playingNameList = new ArrayList<>();
    private Map<ClientSocketConnection, ArrayList<ClientSocketConnection>> playingConnections = new HashMap<>();

    /**
     * Allocates a new Server object and initialize serverSocket
     * @throws IOException if cannot initialize the ServerSocket
     */

    public Server () throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    /**
     * Check if a nickname is already in use and if it's not, add them in {@link Server#waitingNameList}
     * @param connection the {@link ClientSocketConnection} of the player
     * @param nickname the nickname of the player
     * @return true if the nickname is not in the {@link Server#waitingNameList}, false otherwise
     */

    public synchronized boolean insertInWaitingList(ClientSocketConnection connection, String nickname){
        if (waitingNameList.contains(nickname) || playingNameList.contains(nickname)){
            return false;
        }
        waitingNameList.add(nickname);
        return true;
    }

    /**
     * Disconnect {@code playerToKill} from the server and remove it from {@link Server#playingList} and {@link Server#playingConnections}
     * @param playerToKill the player to disconnect from the server
     */
    public synchronized void looserDisconnect(String playerToKill){
        System.out.println(TimeStamp.getTimeSTamp() + playerToKill +" has lost!");
        playingNameList.remove(playerToKill);
        ClientSocketConnection playerToKillSocket = playingList.get(playerToKill);
        for (ClientSocketConnection c : playingConnections.get(playerToKillSocket)){
            playingConnections.get(c).remove(playerToKillSocket);
        }
        playingConnections.remove(playerToKillSocket);
        playingList.remove(playerToKill);
    }

    /**
     * Method called when an exception occurred in the {@link ClientSocketConnection} of {@code nickname} or the game is ended.
     * If the {@code nickname} is still in the {@link Server#waitingNameList} then it is removed, otherwise disconnect all the player in the same game of {@code nickname} and remove them from the {@link Server#playingList} and {@link Server#playingConnections}
     * @param nickname the nickname of the player to disconnect
     */

    public synchronized void killLobby(String nickname){
        if(waitingNameList.contains(nickname)){
            System.out.println(TimeStamp.getTimeSTamp() +"Removing " + nickname +" from waiting list" );
            waitingListForTwo.remove(nickname);
            waitingListForThree.remove(nickname);
            waitingNameList.remove(nickname);
            return;
        }
        if(playingNameList.contains(nickname)){
            System.out.println(TimeStamp.getTimeSTamp() +"Closing game for " + nickname );
            for (ClientSocketConnection c : playingConnections.get(playingList.get(nickname))){
                c.closeConnection(nickname+" has disconnected from the server. ");
                String nick = playingList.keySet().stream().filter(s -> playingList.get(s).equals(c)).collect(Collectors.toList()).get(0);
                playingList.remove(nick);
                playingNameList.remove(nick);
                playingConnections.remove(c);
            }
            playingConnections.remove(playingList.get(nickname));
            playingList.remove(nickname);
            playingNameList.remove(nickname);
        }
    }

    /**
     * Insert the new nickname and connection in {@link Server#waitingListForTwo} and if the waiting list  is full initialize a new game otherwise send a {@link WaitMessage} to connection
     * @param nickname the nickname of the player
     * @param connection the {@link ClientSocketConnection} of the player
     */

    public synchronized void lobbyForTwoPlayer(String nickname,ClientSocketConnection connection) {
        System.out.println(TimeStamp.getTimeSTamp() +"Adding " + nickname + " to waiting List for two players"  );
        waitingListForTwo.put(nickname, connection);
            if (waitingListForTwo.size() == 2) {
                ArrayList<String> nameList = new ArrayList<>(waitingListForTwo.keySet());
                Collections.shuffle(nameList);
                String nickname1 = nameList.get(0);
                String nickname2 = nameList.get(1);
                System.out.printf(TimeStamp.getTimeSTamp() +"Starting a new game for %s,%s\n" ,nickname1 , nickname2);
                ClientSocketConnection connection1 = waitingListForTwo.get(nickname1);
                ClientSocketConnection connection2 = waitingListForTwo.get(nickname2);

                playingNameList.add(nickname1);
                playingNameList.add(nickname2);
                playingList.put(nickname1, connection1);
                playingList.put(nickname2, connection2);

                Player player1 = new Player(nickname1);
                Player player2 = new Player(nickname2);

                Game game = new Game();
                game.setNumOfPlayers(2);
                game.addPlayer(player1);
                game.addPlayer(player2);
                game.playerColorInit();

                VirtualView vv1 = new VirtualView(connection1, player2.playerClone(), player1.playerClone());
                VirtualView vv2 = new VirtualView(connection2, player1.playerClone(), player2.playerClone());

                Map <String,ClientSocketConnection> currentPlayers = new HashMap<>();
                currentPlayers.put(nickname1,connection1);
                currentPlayers.put(nickname2,connection2);

                Controller controller = new Controller(game, currentPlayers);
                game.addObserver(vv1);
                game.addObserver(vv2);
                vv1.addObserver(controller);
                vv2.addObserver(controller);

                playingConnections.put(connection1, new ArrayList<>(Collections.singletonList(connection2)));
                playingConnections.put(connection2, new ArrayList<>(Collections.singletonList(connection1)));

                controller.start();
                waitingNameList.remove(nickname1);
                waitingNameList.remove(nickname2);
                waitingListForTwo.clear();
            }else{
                connection.send(new WaitMessage());
            }
    }

    /**
     * Insert the new nickname and connection in the {@link Server#waitingListForThree} and if the waiting list  is full initialize a new game otherwise send a {@link WaitMessage} to connection
     * @param nickname the nickname of the player
     * @param connection the ClientSocketConnection of the player
     */

    public synchronized void lobbyForThreePlayer(String nickname,ClientSocketConnection connection){
        System.out.println(TimeStamp.getTimeSTamp() +"Adding " + nickname + " to waiting list for three players"  );
        waitingListForThree.put(nickname, connection);
        if(waitingListForThree.size()==3) {
            ArrayList<String> nameList = new ArrayList<>(waitingListForThree.keySet());
            Collections.shuffle(nameList);
            String nickname1 = nameList.get(0);
            String nickname2 = nameList.get(1);
            String nickname3 = nameList.get(2);
            System.out.printf(TimeStamp.getTimeSTamp() +"Starting a new game for %s,%s,%s\n" ,nickname1 , nickname2, nickname3);
            ClientSocketConnection connection1 = waitingListForThree.get(nickname1);
            ClientSocketConnection connection2 = waitingListForThree.get(nickname2);
            ClientSocketConnection connection3 = waitingListForThree.get(nickname3);

            playingNameList.add(nickname1);
            playingNameList.add(nickname2);
            playingNameList.add(nickname3);
            playingList.put(nickname1, connection1);
            playingList.put(nickname2, connection2);
            playingList.put(nickname3, connection3);

            Player player1 = new Player(nickname1);
            Player player2 = new Player(nickname2);
            Player player3 = new Player(nickname3);

            Game game = new Game();
            game.setNumOfPlayers(3);
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addPlayer(player3);
            game.playerColorInit();

            VirtualView vv1 = new VirtualView(connection1, player2.playerClone(), player3.playerClone(), player1.playerClone());
            VirtualView vv2 = new VirtualView(connection2, player1.playerClone(), player3.playerClone(), player2.playerClone());
            VirtualView vv3 = new VirtualView(connection3, player1.playerClone(),  player2.playerClone(), player3.playerClone());

            Map<String, ClientSocketConnection> currentPlayers = new HashMap<>();
            currentPlayers.put(nickname1, connection1);
            currentPlayers.put(nickname2, connection2);
            currentPlayers.put(nickname3, connection3);

            Controller controller = new Controller(game, currentPlayers);
            game.addObserver(vv1);
            game.addObserver(vv2);
            game.addObserver(vv3);
            vv1.addObserver(controller);
            vv2.addObserver(controller);
            vv3.addObserver(controller);

            playingConnections.put(connection1, new ArrayList<>(Arrays.asList(connection2, connection3)));
            playingConnections.put(connection2, new ArrayList<>(Arrays.asList(connection1, connection3)));
            playingConnections.put(connection3, new ArrayList<>(Arrays.asList(connection1, connection2)));

            controller.start();
            waitingNameList.remove(nickname1);
            waitingNameList.remove(nickname2);
            waitingNameList.remove(nickname3);
            waitingListForThree.clear();
        }else{
            connection.send(new WaitMessage());
        }
    }

    /**
     *Accept all the new connection and launch a thread for the new ClientSocketConnection
     */

    public void start(){
        System.out.println("The Santorini server is up and running...");
        while (true){
            try {
                Socket newClientSocket = serverSocket.accept();
                //System.out.println("\nAccepted new client on port: " + newClientSocket.getPort() + "\n");
                ClientSocketConnection socketConnection = new ClientSocketConnection(newClientSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.err.println(TimeStamp.getTimeSTamp() + "Cannot connect the client " + e.getMessage() + "!");
            }
        }
    }

}