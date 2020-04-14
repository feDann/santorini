package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.ConnectionMessage;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.WelcomeMessage;

import java.util.Scanner;

public class ClientMessageDecoder {


    public static void decodeMessage(Message message) {

        if (message instanceof WelcomeMessage) {
            //actually calls view methods to show messages
            System.out.print(((WelcomeMessage) message).getMessage());
        }
        else if (message instanceof ConnectionMessage){
            System.out.print(((ConnectionMessage)message).getMessage() + "\n>>>");
        }

    }


}
