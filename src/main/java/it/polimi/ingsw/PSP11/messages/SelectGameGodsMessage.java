package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.controller.state.SelectPlayerGodState;
import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.model.Deck;

public class SelectGameGodsMessage extends SimpleMessage {

    public SelectGameGodsMessage(Deck deck){
        super("");
        String formattedMessage = "Insert the number of god to use in game:\n";

        for(Card card : deck.getCards()){
            formattedMessage +=  card.getIdCard() + ") " + card.getName() + "\n   Description: "+ card.getDescription() + "\n";
        }

        formattedMessage += ">>>";
        formattedMessage = formattedMessage.replaceAll("Your Move", Color.BLUE.getEscape() + "Your Move" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Build", Color.BROWN.getEscape() + "Your Build" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Win Condition", Color.GREEN.getEscape() + "Win Condition" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Your Turn", Color.PURPLE.getEscape() + "Your Turn" + Color.RESET );
        formattedMessage = formattedMessage.replaceAll("Opponent’s Turn", Color.RED.getEscape() + "Opponent's Turn" + Color.RESET );

        setMessage(formattedMessage);
    }
}
