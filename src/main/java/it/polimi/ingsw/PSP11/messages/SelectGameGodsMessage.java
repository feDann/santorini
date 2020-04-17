package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.controller.state.SelectPlayerGodState;
import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Deck;

public class SelectGameGodsMessage extends SimpleMessage {

    public SelectGameGodsMessage(Deck deck){
        super("");
        String formattedMessage = "Insert the number of god to use in game:\n";
        for(Card card : deck.getCards()){
            formattedMessage = formattedMessage + card.getIdCard() + ") " + card.getName() + "\n   Description: "+ card.getDescription()+"\n\n";
        }
        formattedMessage = formattedMessage +"\n>>>";
        setMessage(formattedMessage);
    }
}
