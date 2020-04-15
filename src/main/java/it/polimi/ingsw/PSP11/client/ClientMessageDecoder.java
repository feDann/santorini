package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.*;

import java.util.Scanner;

public class ClientMessageDecoder {


    public static void decodeMessage(Message message) {

        if (message instanceof SimpleMessage) {
            //actually calls view methods to show messages
            System.out.print((message).getMessage());
        }
//        else if (message instanceof SimpleMessage){
//            System.out.print((message).getMessage());
//        }
//        else if (message instanceof ConnectionClosedMessage){
//            System.out.print((message).getMessage());
//        }

    }


}
