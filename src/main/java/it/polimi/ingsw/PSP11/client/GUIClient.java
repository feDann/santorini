package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.ConnectionClosedMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.Ping;
import it.polimi.ingsw.PSP11.view.gui.controller.GUIController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GUIClient implements Pinger{
    private String ip;
    private int port = 50000;
    private boolean active = false;

    private GUIController guiController;

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Socket socket;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pingHandler;


    public void setController(GUIController controller) {
        this.guiController = controller;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public GUIController getGuiController() {
        return guiController;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void asyncRead(){
        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    socket.setSoTimeout(6000);
                    while(isActive()){
                        Message msg = (Message) socketIn.readObject();
                        guiController.handleMessage(msg);
                    }
                }catch(SocketTimeoutException t){
                    System.out.println("Server Down");
                    guiController.handleMessage(new ConnectionClosedMessage("Server Down. "));
                }
                catch (Exception e){
                    System.err.println("(read Catch2)Error: " + e.getMessage());
                }
                finally {
                    killPinger();
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }


    public void asyncWrite(Message message) {
        new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (socketOut) {
                        socketOut.writeObject(message);
                        socketOut.flush();
                        socketOut.notifyAll();
                    }
                } catch (IOException e) {
                    System.err.println("(write)Error: " + e.getMessage());
                }
            }
        }.start();
    }

    @Override
    public void killPinger() {
        pingHandler.cancel(true);
        scheduler.shutdown();
    }

    @Override
    public void pinger() {
        Runnable ping = () -> {
            try {
                synchronized (socketOut) {
                    System.out.println("Pinging...");
                    socketOut.reset();
                    socketOut.writeObject(new Ping());
                    socketOut.flush();
                    socketOut.notifyAll();
                }
            } catch (IOException e) {
                System.err.println("(pinger)Error writing to server: " + e.getMessage());
                setActive(false);
                killPinger();
            }
        };
        pingHandler = scheduler.scheduleAtFixedRate(ping, 0, 3, TimeUnit.SECONDS);


    }

    public void close() throws IOException {
        setActive(false);
        killPinger();
        socketIn.close();
        socketOut.close();
        socket.close();
    }


    public void start() throws IOException {
        socket = new Socket(ip,port);
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        setActive(true);
        asyncRead();
        pinger();

    }

}
