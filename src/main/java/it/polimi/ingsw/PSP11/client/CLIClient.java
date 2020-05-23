package it.polimi.ingsw.PSP11.client;


import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.ConnectionClosedMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CLIClient implements Pinger {

    private String ip;
    private int port;
    private boolean active = true;
    private Message message;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Socket clientSocket;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> pingHandler;
    private Scanner stdin;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public CLIClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }



    public Thread asyncRead(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientSocket.setSoTimeout(6000);
                    while (isActive()) {
                        Message tmp = (Message)socketIn.readObject();
                        if(!(tmp instanceof Ping)){
                            message = tmp ;
                            ClientMessageDecoder.decodeMessage(message);
                            if(message instanceof ConnectionClosedMessage){
                                killPinger();
                                setActive(false);
                            }
                        }
                    }
                }
                catch (SocketTimeoutException t){
                    System.out.println("(read)Server Down\nConnection closed.\n");
                    pingHandler.cancel(true);
                    scheduler.shutdown();
                    setActive(false);
                }catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWrite(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        try {
                            String inputLine = stdin.nextLine();
                            Message answer = null;
                            answer = ClientMessageEncoder.encodeMessage(message, inputLine);
                            synchronized (socketOut) {
                                socketOut.writeObject(answer);
                                socketOut.flush();
                                socketOut.notifyAll();
                            }
                        } catch (IllegalInputException e) {
                            System.out.println(e.getErrorMessage());
                            System.out.print(">>>");
                        }
                    }
                }catch(Exception e){
                    System.err.println("(write)Error: " + e.getMessage());
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    @Override
    public void killPinger(){
        pingHandler.cancel(true);
        scheduler.shutdown();
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
                System.err.println("(pinger)Error writing to server: " + e.getMessage());
                setActive(false);
                killPinger();
            }
        };
        pingHandler = scheduler.scheduleAtFixedRate(ping, 0, 3, TimeUnit.SECONDS);


    }

    public void start() throws IOException {

        try (Scanner stdin = new Scanner(System.in); Socket clientSocket = new Socket(ip, port);
             ObjectInputStream socketIn = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream())) {
            this.stdin = stdin;
            this.clientSocket = clientSocket;
            this.socketIn = socketIn;
            this.socketOut = socketOut;
            Thread t0 = asyncRead();
            Thread t1 = asyncWrite();
            pinger();
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("(start)Connection closed from the client side");
        } finally {
            killPinger();
            stdin.close();
            socketIn.close();
            socketOut.close();
            clientSocket.close();
        }

    }
}
