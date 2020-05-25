package it.polimi.ingsw.PSP11.server;

import it.polimi.ingsw.PSP11.client.Pinger;
import it.polimi.ingsw.PSP11.messages.*;
import it.polimi.ingsw.PSP11.observer.Observable;

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
    //private ExecutorService executor = Executors.newSingleThreadExecutor();

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

    private synchronized boolean isActive(){
        return active;
    }

//    public void asyncSend(final Object message){
//        Thread thread;
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                send(message);
//            }
//        });
//        executor.submit(thread);
//    }

    public synchronized void send(Object message) {
        try {
            synchronized (out) {
                out.reset();
                out.writeObject(message);
                out.flush();
                out.notifyAll();
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }


    public synchronized void closeConnection(String message) {
        send(new ConnectionClosedMessage(message));
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket! ");
        }
        active = false;
    }

    public void killGame(String nickname){
        server.killLobby(nickname);
        closeConnection("Game Ended! ");
    }

    public void goCommitDie(String playerToKill){
        server.looserDisconnect(playerToKill);
        closeConnection("");
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
                synchronized (out) {
//                    System.out.println("sending ping to socket + " + this.toString());
                    out.reset();
                    out.writeObject(new Ping());
                    out.flush();
                    out.notifyAll();
                }
            } catch (IOException e) {
                System.err.println("Error writing to client: " + e.getMessage());
                killPinger();
            }
        };
        pingHandler = scheduler.scheduleAtFixedRate(ping, 0, 3, TimeUnit.SECONDS);
    }


    @Override
    public void run() {
        Message message;
        try {
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
            System.err.println(e.getMessage());
            server.killLobby(nickname);
        }
        finally {
            killPinger();
        }
    }


}