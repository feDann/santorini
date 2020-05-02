package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.Deck;

public class SelectGameGodsRequest extends SimpleMessage {

    private int numOfPlayers;
    private int numOfDeckCards;

    public SelectGameGodsRequest(Deck deck,int numOfPlayers){
        super("");
        this.numOfPlayers = numOfPlayers;
        this.numOfDeckCards = deck.getCards().size();
        String formattedMessage = "\n\nThe Available god are:\n\n";

        for(Card card : deck.getCards()){
            formattedMessage +=  card.getIdCard() + ") " + card.getName() + "\n   Description: "+ card.getDescription() + "\n\n";
        }

        formattedMessage = formattedMessage.replaceAll("Your Move", Color.BLUE.getEscape() + "Your Move" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Build", Color.BROWN.getEscape() + "Your Build" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Win Condition", Color.GREEN.getEscape() + "Win Condition" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Turn", Color.PURPLE.getEscape() + "Your Turn" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Opponent’s Turn", Color.RED.getEscape() + "Opponent's Turn" + Color.RESET );

        formattedMessage = formattedMessage.concat("\nChoose " + numOfPlayers + " gods to use in the game, in this format --> ");
        if(numOfPlayers == 2){
            formattedMessage = formattedMessage.concat("x,y");
        }
        else if (numOfPlayers == 3){
            formattedMessage = formattedMessage.concat("x,y,z");
        }
        formattedMessage += "\n>>>";
        setMessage(formattedMessage);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public int getNumOfDeckCards() {
        return numOfDeckCards;
    }
}
