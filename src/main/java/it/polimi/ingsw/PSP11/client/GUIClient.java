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

/**
 * This class implements features for a player who chooses to play with GUI
 */
public class GUIClient extends Client{

    private GUIController guiController;

    /**
     * Constructs a GUI client
     */
    public GUIClient(){
        super("", 50000);
    }

    /**
     * Setter method for the client's {@link GUIController}
     * @param controller the appropriate controller for the current game scene
     */
    public void setController(GUIController controller) {
        this.guiController = controller;
    }

    /**
     * Getter method for the client's {@link GUIController}
     * @return the current scene controller
     */
    public GUIController getGuiController() {
        return guiController;
    }

    /**
     * Creates and starts a Thread, as a daemon, that continuously reads incoming messages from the server, as long as the client is active
     */
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
                    guiController.handleMessage(new ConnectionClosedMessage("A network error has occurred"));
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


    /**
     * Creates and starts a Thread that sends a message to the server
     * @param message the message to send to the server
     */
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

    /**
     * Marks this client as inactive and closes its resources
     * @throws IOException when there is a problem closing this client's resources
     */
    public void close() throws IOException {
        setActive(false);
        killPinger();
        super.close();
    }

    /**
     * Allocates resources for this client and initializes the Thread for server communication
     * @throws IOException when there is a problem initialising resources for this client
     */
    public void start() throws IOException{
        setClientSocket(new Socket(getIp(),getPort())) ;
        setSocketIn(new ObjectInputStream(getClientSocket().getInputStream()));
        setSocketOut( new ObjectOutputStream(getClientSocket().getOutputStream()));
        setActive(true);
        asyncRead();
        pinger();

    }

}
