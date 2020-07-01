package it.polimi.ingsw.PSP11;

import it.polimi.ingsw.PSP11.client.CLIClient;
import it.polimi.ingsw.PSP11.view.gui.Gui;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    /**
     *Launches GUI or CLI according to args parameters
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            Application.launch(Gui.class);
        }
        else if (args[0].equals("-nogui")) {
            Scanner stdin = new Scanner(System.in);
            System.out.print("Insert the ip address of the server\n>>>");
            String ipAddress = stdin.nextLine();
            CLIClient client = new CLIClient(ipAddress, 50000);
            try {
                client.start();
            } catch (IOException e) {
                System.err.println("Cannot initialize the client " + e.getMessage() + "!");
            }

        }
        else{
            System.out.println("Invalid Argument!");
        }
    }
}
