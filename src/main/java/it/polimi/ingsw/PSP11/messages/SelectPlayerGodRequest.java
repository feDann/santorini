package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;

import java.util.ArrayList;

public class SelectPlayerGodRequest extends SimpleMessage{

    private int numOfCards;
    private ArrayList<Card> chosenCards = new ArrayList<Card>();

    /**
     * message used to ask the player to chose his personal god
     * @param chosenCards the gods chosen for the game by the challenger
     */
    public SelectPlayerGodRequest(ArrayList<Card> chosenCards) {
        super("");
        int id = 1;
        numOfCards = chosenCards.size();
        this.chosenCards= chosenCards;
        String formattedMessage = "\n\n\nAvailable Gods:\n\n";

        for (Card card : chosenCards){
            formattedMessage += id + ")" + card.getName() + "\n   Description: " + card.getDescription() + "\n\n";
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

    public ArrayList<Card> getChosenCards() {
        return chosenCards;
    }
}
