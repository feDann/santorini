package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;

import java.util.ArrayList;

public class SelectPlayerGodRequest extends SimpleMessage{

    private int numOfCards;
    //TODO
    private ArrayList<Card> chosenCardsCopy = new ArrayList<Card>();

    public SelectPlayerGodRequest(ArrayList<Card> chosenCards) {
        super("");
        int id = 1;
        numOfCards = chosenCards.size();
        chosenCardsCopy = chosenCards;
        String formattedMessage = "\n\n\nAvailable Gods:\n";

        for (Card card : chosenCards){
            formattedMessage += id + ")" + card.getName() + "\n   Description: " + card.getDescription() + "\n";
            id++;
        }
        formattedMessage = formattedMessage.replaceAll("Your Move", Color.BLUE.getEscape() + "Your Move" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Build", Color.BROWN.getEscape() + "Your Build" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Win Condition", Color.GREEN.getEscape() + "Win Condition" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Turn", Color.PURPLE.getEscape() + "Your Turn" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Opponent’s Turn", Color.RED.getEscape() + "Opponent's Turn" + Color.RESET );

        formattedMessage = formattedMessage.concat("\nInsert the desired id\n>>>");

        setMessage(formattedMessage);
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    public ArrayList<Card> getChosenCardsCopy() {
        return chosenCardsCopy;
    }
}
