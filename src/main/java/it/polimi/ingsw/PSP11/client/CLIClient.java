package it.polimi.ingsw.PSP11.client;


import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.ConnectionClosedMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.Ping;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class implements features for a player who chooses to play with CLI
 */
public class CLIClient extends Client{

    private Scanner stdin;
    private Message message;

    /**
     * Constructs a CLIClient object with the given IP address and port number
     * @param ip IP address of the server
     * @param port port number of the server
     */
    public CLIClient(String ip, int port) {
        super(ip,port);
    }

    /**
     * Creates and starts a Thread that continuously reads incoming messages from the server, as long as the client is active
     * @return the created Thread
     */
    public Thread asyncRead(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    setSocketTimeout(6000);
                    while (isActive()) {
                        Message tmp = (Message)getSocketIn().readObject();
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
                    System.out.println("Server Down\nConnection closed.\n");
                    killPinger();
                    setActive(false);
                }catch (Exception e){
                    System.out.println("A network error has occurred\nConnection closed.\n");
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * Creates and starts a Thread that reads user input and sends the corresponding message to the server, as long as the client is active
     * @return the created thread
     */
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
                            synchronized (getSocketOut()) {
                                getSocketOut().writeObject(answer);
                                getSocketOut().flush();
                                getSocketOut().notifyAll();
                            }
                        } catch (IllegalInputException e) {
                            System.out.println(e.getErrorMessage());
                            System.out.print(">>>");
                        }
                    }
                }catch(Exception e){
                    System.err.println("Error: " + e.getMessage());
                    setActive(false);
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * Allocates resources for this client, initializes threads for communicating with the server and ping feature. It then waits for the communication threads to die.
     * @throws IOException when there is a problem initialising resources for this client
     */
    public void start() throws IOException {

        try (Scanner stdin = new Scanner(System.in); Socket clientSocket = new Socket(getIp(), getPort());
             ObjectInputStream socketIn = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream socketOut = new ObjectOutputStream(clientSocket.getOutputStream())) {
            this.stdin = stdin;
            setClientSocket(clientSocket);
            setSocketIn(socketIn);
            setSocketOut(socketOut);
            Thread t0 = asyncRead();
            Thread t1 = asyncWrite();
            pinger();
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the client side");
        } finally {
            if(stdin != null){
                stdin.close();
            }
            if(getPingHandler()!= null && getScheduler() != null){
                killPinger();
            }
            if(getClientSocket() != null){
                close();
            }
        }

    }
}
