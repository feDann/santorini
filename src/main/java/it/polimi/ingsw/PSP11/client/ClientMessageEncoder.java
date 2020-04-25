package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.*;

import java.awt.*;
import java.util.ArrayList;

public class ClientMessageEncoder {

    private static Point checkLegalInput(String[] position) throws IllegalInputException{
        try {
            int x = Integer.parseInt(position[0]) - 1;
            int y = Integer.parseInt(position[1]) - 1;
            if (x < 0 || x > 4 || y < 0 || y > 4){
                throw new IllegalInputException("Stay between 1 and 5 pls");
            }
            Point point = new Point(x,y);
            if (position.length != 2){
                throw new IllegalInputException("DONT YOU KNOW THAT WE ARE IN A BI-DIMENTIONAL SPACE YOU MORON!");
            }
            return point;
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new IllegalInputException("Invalid input");
        }
    }

    public static Message encodeMessage(Message lastServerMessage, String inputLine) throws IllegalInputException{

        if(lastServerMessage instanceof WelcomeMessage || lastServerMessage instanceof DuplicateNicknameMessage){
            return new NicknameMessage(inputLine);
        }

        else if(lastServerMessage instanceof ConnectionMessage){
            try{
                int i = Integer.parseInt(inputLine);
                if (i < 2 || i > 3 ){
                    throw new IllegalInputException("Invalid input, insert 2 o 3!");
                }
                return new PlayerSetupMessage(inputLine);
            }
            catch (NumberFormatException e){
                throw new IllegalInputException("Invalid input, insert 2 o 3!");
            }
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

        else if (lastServerMessage instanceof PlaceWorkerRequest || lastServerMessage instanceof InvalidWorkerPosition){
            inputLine = inputLine.replaceAll(" ","");
            String[] workerPosition = inputLine.split(",");
            Point checkedPoint = checkLegalInput(workerPosition);
            return new PlaceWorkerResponse(checkedPoint);
        }

        else if (lastServerMessage instanceof SelectWorkerRequest){
            inputLine = inputLine.replaceAll(" ","");
            try {
                int chosenWorker = Integer.parseInt(inputLine);
                if (chosenWorker < 1 || chosenWorker > 2){
                    throw new IllegalInputException("Please insert 1 or 2");
                }
                return new SelectWorkerResponse((chosenWorker-1));
            }catch (NumberFormatException e){
                throw new IllegalInputException("Invalid input, please insert 1 or 2");
            }
        }


        else if (lastServerMessage instanceof BuildBeforeMoveRequest){
            inputLine = inputLine.replaceAll(" ","");
            String response = inputLine.toLowerCase();
            if (response.equals("y") || response.equals("yes")){
                return new BuildBeforeMoveResponse(true);
            }
            else if (response.equals("n") || response.equals("no")){
                return new BuildBeforeMoveResponse(false);
            }
            else{
                throw new IllegalInputException("invalid input, please insert y or n");
            }

        }

        else if (lastServerMessage instanceof MoveRequest){
            inputLine = inputLine.replaceAll(" ","");
            String[] movePosition = inputLine.split(",");
            Point checkedPoint = checkLegalInput(movePosition);
            if (! ((MoveRequest) lastServerMessage).getPossibleMoves().contains(checkedPoint)){
                throw new IllegalInputException("You cannot move to this point, please choose one from the list above!");
            }
            return new MoveResponse(checkedPoint);
        }

        return null;
    }

}