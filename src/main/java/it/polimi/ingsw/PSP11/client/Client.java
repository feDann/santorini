package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class with basic client features for both CLI and GUI players
 */
public class Client implements Pinger{

    private String ip;
    private int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Socket clientSocket;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pingHandler;

    /**
     * Constructs a Client object with the given IP address and port number
     * @param ip IP address of the server
     * @param port port number of the server
     */
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    /**
     * Getter method server IP address
     * @return server IP address
     */
    public String getIp() {
        return ip;
    }

    /**
     * Setter method for server IP address
     * @param ip server IP address
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Getter method for server port number
     * @return server port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Setter method for server port number
     * @param port server port number
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Getter method that determines whether this client is active or not
     * @return true if this client is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the corresponding attribute based on the current status of this client
     * @param active true if the client is active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Getter method for the ObjectInputStream resource
     * @return the input stream associated with the client's socket
     */
    public ObjectInputStream getSocketIn() {
        return socketIn;
    }

    /**
     * Setter method for the ObjectInputStream resource
     * @param socketIn the input stream associated with the client's socket
     */
    public void setSocketIn(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    /**
     * Getter method for the ObjectOutputStream resource
     * @return the output stream associated with the client's socket
     */
    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    /**
     * Setter method for the ObjectOutputStream resource
     * @param socketOut the output stream associated with the client's socket
     */
    public void setSocketOut(ObjectOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    /**
     * Getter method for the Socket resource
     * @return the socket associated with the client
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * Setter method for the Socket resource
     * @param clientSocket the socket associated with the client
     */
    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Closes the resources allocated for this client
     * @throws IOException when there is a problem closing the resources
     */
    public void close() throws IOException {
        socketIn.close();
        socketOut.close();
        clientSocket.close();
    }

    /**
     * Sets the timeout for this client's socket
     * @param millis the specified timeout, in milliseconds
     * @throws SocketException when there is a problem setting the timeout for the socket
     */
    public void setSocketTimeout(int millis) throws SocketException {
        clientSocket.setSoTimeout(millis);
    }

    /**
     * Getter method for the client's ScheduledExecutorService
     * @return the executor that schedules the ping message from this client
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }

    /**
     * Getter method for the client's ping handler
     * @return the ping handler for this client
     */
    public ScheduledFuture<?> getPingHandler() {
        return pingHandler;
    }

    /**
     * Creates a Runnable that sends a ping message to the server and schedules it using this client's ScheduledExecutorService
     */
    @Override
    public void pinger() {
        Runnable ping = () -> {
            try {
                synchronized (socketOut) {
                    socketOut.reset();
                    socketOut.writeObject(new Ping());
                    socketOut.flush();
                    socketOut.notifyAll();
                }
            } catch (IOException e) {
                System.err.println("Error writing to server: " + e.getMessage());
                setActive(false);
                killPinger();
            }
        };
        pingHandler = scheduler.scheduleAtFixedRate(ping, 0, 3, TimeUnit.SECONDS);

    }

    /**
     * Cancels the Runnable responsible for pinging the server and shuts down the client's ScheduledExecutorService
     */
    @Override
    public void killPinger() {
        pingHandler.cancel(true);
        scheduler.shutdown();
    }
}
