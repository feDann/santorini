package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.*;

import java.util.Scanner;

public class ClientMessageEncoder {

    public static Message encodeMessage(Message lastServerMessage, String inputLine) throws IllegalInputException{
        if(lastServerMessage instanceof WelcomeMessage){
            return new NicknameMessage(inputLine);
        }
        else if(lastServerMessage instanceof ConnectionMessage){
            int i=0;
            try{
                 i = Integer.parseInt(inputLine);
            }
            catch (NumberFormatException e){
                //remind to do a log
            }

            if (i < 2 || i > 3 ){
                throw new IllegalInputException("Invalid input, insert 2 o 3!");
            }
            return new PlayerSetupMessage(inputLine);
        }
        return null;
    }

}
