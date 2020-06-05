package it.polimi.ingsw.PSP11;

import it.polimi.ingsw.PSP11.server.Server;
import it.polimi.ingsw.PSP11.utils.TimeStamp;

public class ServerApp {

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            server.start();
        } catch (Exception e) {
            System.err.println(TimeStamp.getTimeSTamp() + "Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }

}
