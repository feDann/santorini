package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.*;

import java.util.Scanner;

public class ClientMessageEncoder {

    public static Message encodeMessage(Message message, String inputLine) throws IllegalInputException{
        if(message instanceof WelcomeMessage){
            return new NicknameMessage(inputLine);
        }
        else if(message instanceof ConnectionMessage){
            int i = Integer.parseInt(inputLine);
            if (i < 2 || i > 3 ){
                throw new IllegalInputException("Invalid input, coglione, insert 2 o 3!");
            }
            return new PlayerSetupMessage(inputLine);
        }
        return null;
    }

}
