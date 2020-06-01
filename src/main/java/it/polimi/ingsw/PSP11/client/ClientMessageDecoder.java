package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.messages.SimpleMessage;
import it.polimi.ingsw.PSP11.messages.StartGameMessage;

/**
 * Class that handles server messages for {@link CLIClient}
 */
public class ClientMessageDecoder {


    /**
     * Decodes server message, printing it to the standard output
     * @param message message from the server
     */
    public static void decodeMessage(Message message) {
        if (message instanceof StartGameMessage){
            //reset the screen without making the clear
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\033[H");

        }
        else if (message instanceof SimpleMessage) {
            //actually calls view methods to show messages
            String messaggino = message.getMessage();
            System.out.print(messaggino);
        }

    }

}
