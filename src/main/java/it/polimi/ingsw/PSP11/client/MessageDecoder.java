package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.WelcomeMessage;

public class MessageDecoder {


    public static void decodeMessage(Message message) {

        if (message instanceof WelcomeMessage) {
            //actually calls view methods to show messages
            System.out.println(message.getMessage());
        }

    }


}
