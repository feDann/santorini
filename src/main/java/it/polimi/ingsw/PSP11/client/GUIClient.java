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

public class GUIClient extends Client{

    private GUIController guiController;


    public GUIClient(){
        super("", 50000);
    }

    public void setController(GUIController controller) {
        this.guiController = controller;
    }

    public GUIController getGuiController() {
        return guiController;
    }


    public void asyncRead(){
        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    setSocketTimeout(6000);
                    while(isActive()){
                        Message msg = (Message) getSocketIn().readObject();
                        if(!(msg instanceof Ping)) {
                            guiController.handleMessage(msg);
                        }
                    }
                }catch(SocketTimeoutException t){
                    System.out.println("Server Down");
                    guiController.handleMessage(new ConnectionClosedMessage("Server Down. "));
                }
                catch (Exception e){
                    System.err.println("Error: " + e.getMessage());
                }
                finally {
                    setActive(false);
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
                    synchronized (getSocketOut()) {
                        getSocketOut().writeObject(message);
                        getSocketOut().flush();
                        getSocketOut().notifyAll();
                    }
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }.start();
    }


    public void close() throws IOException {
        setActive(false);
        killPinger();
        super.close();
    }


    public void start() throws IOException{
        setClientSocket(new Socket(getIp(),getPort())) ;
        setSocketIn(new ObjectInputStream(getClientSocket().getInputStream()));
        setSocketOut( new ObjectOutputStream(getClientSocket().getOutputStream()));
        setActive(true);
        asyncRead();
        pinger();

    }

}
