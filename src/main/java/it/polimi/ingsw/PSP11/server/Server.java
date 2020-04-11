package it.polimi.ingsw.PSP11.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int serverPort = 50000;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(64);


    public Server () throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }


    public void start(){
        System.out.println("The Santorini server is up and running...");
        while (true){
            try {
                Socket newClientSocket = serverSocket.accept();
                //System.out.println("\nAccepted new client on port: " + newClientSocket.getPort() + "\n");
                ClientSocketConnection socketConnection = new ClientSocketConnection(newClientSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.err.println("Cannot connect the client " + e.getMessage() + "!");
            }

        }
    }


}
