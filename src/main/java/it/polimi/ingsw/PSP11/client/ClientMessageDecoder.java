package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;
import it.polimi.ingsw.PSP11.messages.StartGameMessage;

public class ClientMessageDecoder {


    public static void decodeMessage(Message message) {
        if (message instanceof StartGameMessage){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\033[H");

        }
        if (message instanceof SimpleMessage) {
            //actually calls view methods to show messages
            String messaggino = message.getMessage();
            System.out.print(messaggino);
        }

    }

}
