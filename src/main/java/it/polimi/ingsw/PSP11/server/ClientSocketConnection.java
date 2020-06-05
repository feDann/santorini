package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.client.Pinger;
import it.polimi.ingsw.PSP11.messages.ConnectionClosedMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.Ping;
import it.polimi.ingsw.PSP11.messages.WelcomeMessage;
import it.polimi.ingsw.PSP11.observer.Observable;
import it.polimi.ingsw.PSP11.utils.TimeStamp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ClientSocketConnection extends Observable<Message> implements Runnable, Pinger {

    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pingHandler;

    private boolean active = true;
    private String nickname ="";

    /**
     * Allocates a new ClientSocketConnection Object
     * @param socket the socket
     * @param server the server
     */

    public ClientSocketConnection(Socket socket, Server server){
        this.clientSocket = socket;
        this.server = server;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return true if the connection is active, false otherwise
     */

    private synchronized boolean isActive(){
        return active;
    }

    /**
     * Send an Object to the client with {@link ClientSocketConnection#out}
     * @param message the message sent to client
     */

    public synchronized void send(Object message) {
        try {
            synchronized (out) {
                out.reset();
                out.writeObject(message);
                out.flush();
                out.notifyAll();
            }
        } catch(Exception e){
            System.err.println(TimeStamp.getTimeSTamp() + "Error during send of " + clientSocket.getInetAddress().toString());
        }
    }

    /**
     * Send a message to client and close the {@link ClientSocketConnection#clientSocket} connection
     * @param message the reason of the close
     */

    public synchronized void closeConnection(String message) {
        System.out.println(TimeStamp.getTimeSTamp() + "Closing connection for " + clientSocket.getInetAddress().toString());
        send(new ConnectionClosedMessage(message));
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println(TimeStamp.getTimeSTamp() + "Error when closing socket!");
        }
        active = false;
    }

    /**
     * Called by the {@link it.polimi.ingsw.PSP11.controller.Controller} when a game is ended.
     * Disconnect all the player in game with {@code nickname} calling {@link Server#killLobby(String nickname)}
     * @param nickname the nickname of the player to disconnect
     */

    public void killGame(String nickname){
        server.killLobby(nickname);
        closeConnection("Game Ended! ");
    }

    /**
     * Called by the {@link it.polimi.ingsw.PSP11.controller.Controller} when a player lose in a three player  game and the game continue.
     * Close the connection of {@code playerToKill} and call {@link Server#looserDisconnect(String playerToKill)}
     * @param playerToKill the player to disconnect from the server
     */

    public void goCommitDie(String playerToKill){
        server.looserDisconnect(playerToKill);
        closeConnection("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void killPinger() {
        pingHandler.cancel(true);
        scheduler.shutdown();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void pinger() {
        Runnable ping = () -> {
            try {
                synchronized (out) {
//                    System.out.println("sending ping to socket + " + this.toString());
                    out.reset();
                    out.writeObject(new Ping());
                    out.flush();
                    out.notifyAll();
                }
            } catch (IOException e) {
                System.err.println(TimeStamp.getTimeSTamp() + "Error writing to client: " + e.getMessage());
                killPinger();
            }
        };
        pingHandler = scheduler.scheduleAtFixedRate(ping, 0, 3, TimeUnit.SECONDS);
    }

    /**
     * Allocate the {@link ObjectOutputStream} ,{@link ObjectInputStream},call the {@link Pinger#pinger()} and handle the message sent from client
     */
    @Override
    public void run() {
        Message message;
        try {
            System.out.println(TimeStamp.getTimeSTamp() + clientSocket.getInetAddress().toString() + " connected to the server");
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            clientSocket.setSoTimeout(6000);
            pinger();
            send(new WelcomeMessage());

            while (isActive()){
                message = (Message) in.readObject();
                if(!(message instanceof Ping)){
                    ServerMessageDecoder.decodeMessage(server,this,message);
                }
            }
        } catch (IOException | ClassNotFoundException e ) {
            System.out.println(TimeStamp.getTimeSTamp() + "Error with + " + clientSocket.getInetAddress().toString() + ": "+ e.getMessage());
            server.killLobby(nickname);
        }
        finally {
            killPinger();
        }
    }


}