package it.polimi.ingsw.PSP11;

import it.polimi.ingsw.PSP11.client.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        System.out.print("Insert the ip address of the server\n>>>");
        String ipAddress = stdin.nextLine();
        Client client = new Client(ipAddress, 50000);
        try {
            client.start();
        } catch (IOException e){
            System.err.println("Cannot initialize the client " + e.getMessage() + "!");
        }
    }
}
