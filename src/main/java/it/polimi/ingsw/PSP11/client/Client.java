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

public class Client implements Pinger{

    private String ip;
    private int port;
    private boolean active = true;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Socket clientSocket;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pingHandler;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ObjectInputStream getSocketIn() {
        return socketIn;
    }

    public void setSocketIn(ObjectInputStream socketIn) {
        this.socketIn = socketIn;
    }

    public ObjectOutputStream getSocketOut() {
        return socketOut;
    }

    public void setSocketOut(ObjectOutputStream socketOut) {
        this.socketOut = socketOut;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }



    public void close() throws IOException {
        socketIn.close();
        socketOut.close();
        clientSocket.close();
    }

    public void setSocketTimeout(int millis) throws SocketException {
        clientSocket.setSoTimeout(millis);
    }

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

    @Override
    public void killPinger() {
        pingHandler.cancel(true);
        scheduler.shutdown();
    }
}
