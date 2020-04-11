package it.polimi.ingsw.PSP11;

import it.polimi.ingsw.PSP11.client.Client;

import java.io.IOException;

public class ClientApp {

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 50000);
        try {
            client.start();
        } catch (IOException e){
            System.err.println("Cannot initialize the client " + e.getMessage() + "!");
        }
    }
}
