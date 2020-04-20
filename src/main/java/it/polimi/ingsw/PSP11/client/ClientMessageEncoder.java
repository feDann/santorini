package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.*;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientMessageEncoder {

    public static Message encodeMessage(Message lastServerMessage, String inputLine) throws IllegalInputException{

        if(lastServerMessage instanceof WelcomeMessage || lastServerMessage instanceof DuplicateNicknameMessage){
            return new NicknameMessage(inputLine);
        }

        else if(lastServerMessage instanceof ConnectionMessage){
            int i=0;
            try{
                i = Integer.parseInt(inputLine);
                if (i < 2 || i > 3 ){
                    throw new IllegalInputException("Invalid input, insert 2 o 3!");
                }
            }
            catch (NumberFormatException e){
                throw new IllegalInputException("Invalid input, insert 2 o 3!");
            }
            return new PlayerSetupMessage(inputLine);
        }

        else if(lastServerMessage instanceof SelectGameGodsRequest){
            inputLine = inputLine.replaceAll(" ","");
            String[] selectedGods = inputLine.split(",");
            ArrayList<Integer> idOfChosenGods = new ArrayList<>();
            for(String string : selectedGods){
                try {
                    int i = Integer.parseInt(string);
                    if (i-1 >= 0 && i-1 < ((SelectGameGodsRequest) lastServerMessage).getNumOfDeckCards()) {
                        if (! idOfChosenGods.contains(i-1)) {
                            idOfChosenGods.add(i-1);
                        } else throw new IllegalInputException("You cannot choose the same god twice!\nChoose your gods again");
                    } else throw new IllegalInputException("The id must be between 1 and " + ((SelectGameGodsRequest) lastServerMessage).getNumOfDeckCards() + "\nChoose your gods again");
                }catch (NumberFormatException e){
                    throw new IllegalInputException("Invalid input, try again with the correct format, e.g. --> x,y");
                }
            }
            if (idOfChosenGods.size() != ((SelectGameGodsRequest) lastServerMessage).getNumOfPlayers()){
                throw new IllegalInputException("Incorrect number of gods\nChoose gods equal to the number of players!");
            }
            return new SelectGameGodResponse(idOfChosenGods);
        }

        else if (lastServerMessage instanceof SelectPlayerGodRequest){
            inputLine = inputLine.replaceAll(" ","");
            try {
                int index = Integer.parseInt(inputLine);
                if (index < 1 || index > ((SelectPlayerGodRequest) lastServerMessage).getNumOfCards()){
                    throw new IllegalInputException("The id must be between 1 and " + ((SelectPlayerGodRequest) lastServerMessage).getNumOfCards() + "\nChoose another god");
                }
                return new SelectPlayerGodResponse(index-1);
            }catch (NumberFormatException e){
                throw new IllegalInputException("Invalid input, try again, EDDAI IMPEGNATEVI UN PO ANCHE VOI FIGA");
            }
        }

        return null;
    }

}
