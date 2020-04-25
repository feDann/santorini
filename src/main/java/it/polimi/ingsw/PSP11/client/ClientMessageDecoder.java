package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;

public class ClientMessageDecoder {


    public static void decodeMessage(Message message) {

        if (message instanceof SimpleMessage) {
            //actually calls view methods to show messages
            String messaggino = message.getMessage();
            System.out.print(messaggino);
        }

    }

}
