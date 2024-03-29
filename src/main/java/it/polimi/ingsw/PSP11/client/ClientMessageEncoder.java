package it.polimi.ingsw.PSP11.client;

import it.polimi.ingsw.PSP11.exception.IllegalInputException;
import it.polimi.ingsw.PSP11.messages.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used as support by {@link CLIClient} to check user input and generate messages
 */
public class ClientMessageEncoder {

    /**
     * Parses user input for an (x,y) position on the board
     * @param position String array containing the coordinates
     * @return a point representing the position on the board
     * @throws IllegalInputException when the user input is in an incorrect format
     */
    private static Point checkLegalInput(String[] position) throws IllegalInputException{
        try {
            int x = Integer.parseInt(position[0]) - 1;
            int y = Integer.parseInt(position[1]) - 1;
            if (x < 0 || x > 4 || y < 0 || y > 4){
                throw new IllegalInputException("Stay between 1 and 5 please");
            }
            Point point = new Point(x,y);
            if (position.length != 2){
                throw new IllegalInputException("Please insert two coordinates");
            }
            return point;
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new IllegalInputException("Invalid input");
        }
    }

    /**
     * Parses user input and generates the corresponding message for the server
     * @param lastServerMessage the last message this client has received from the server
     * @param inputLine the user input
     * @return the generated message
     * @throws IllegalInputException when the user input is in an incorrect format
     */
    public static Message encodeMessage(Message lastServerMessage, String inputLine) throws IllegalInputException{

        if(lastServerMessage instanceof WelcomeMessage || lastServerMessage instanceof DuplicateNicknameMessage){
            String checkEmptyNick = inputLine.replaceAll(" ","");
            if (checkEmptyNick.equals("")){
                throw new IllegalInputException("Nickname cannot be empty, please choose another nickname");
            }
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
                throw new IllegalInputException("Invalid input");
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
                if ( ((SelectWorkerRequest) lastServerMessage).getAvailableWorkers().stream().noneMatch(worker -> worker.getId() == (chosenWorker-1)) ){
                    throw new IllegalInputException("Please insert a valid worker id");
                }
                return new SelectWorkerResponse((chosenWorker-1));
            }catch (NumberFormatException e){
                throw new IllegalInputException("Invalid input, please insert 1 or 2");
            }
        }


        else if (lastServerMessage instanceof BuildBeforeMoveRequest || lastServerMessage instanceof MoveAgainRequest || lastServerMessage instanceof BuildAgainRequest || lastServerMessage instanceof BuildDomeRequest){
            inputLine = inputLine.replaceAll(" ","");
            String response = inputLine.toLowerCase();
            if (response.equals("y") || response.equals("yes")){
                return new BooleanResponse(true);
            }
            else if (response.equals("n") || response.equals("no")){
                return new BooleanResponse(false);
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

        else if (lastServerMessage instanceof BuildRequest){
            inputLine = inputLine.replaceAll(" ","");
            String[] buildPosition = inputLine.split(",");
            Point checkedPoint = checkLegalInput(buildPosition);
            if (! ((BuildRequest) lastServerMessage).getPossibleBuilds().contains(checkedPoint)){
                throw new IllegalInputException("You cannot build in this point, please choose one from the list above!");
            }
            return new BuildResponse(checkedPoint);
        }


        return null;
    }

}