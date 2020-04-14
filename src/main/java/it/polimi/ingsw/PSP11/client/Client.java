package it.polimi.ingsw.PSP11.client;


import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private boolean active = true;
    private Message message;

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Thread asyncRead(final ObjectInputStream socketIn){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        message =  (Message)socketIn.readObject();
                        ClientMessageDecoder.decodeMessage(message);
                    }
                } catch (Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWrite(final Scanner stdin, final ObjectOutputStream socketOut){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isActive()) {
                        try {
                            String inputLine = stdin.nextLine();
                            Message answer = null;
                            answer = ClientMessageEncoder.encodeMessage(message, inputLine);
                            socketOut.writeObject(answer);
                            socketOut.flush();
                        } catch (IllegalInputException e) {
                            System.out.println(e.getErrorMessage());
                            System.out.print(">>>");
                        }
                    }
                }catch(Exception e){
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    public void start() throws IOException {
        Socket clientSocket = new Socket(ip,port);
        System.out.println("connection established");
        ObjectInputStream socketIn = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncRead(socketIn);
            Thread t1 = asyncWrite(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            clientSocket.close();
        }

    }

}
