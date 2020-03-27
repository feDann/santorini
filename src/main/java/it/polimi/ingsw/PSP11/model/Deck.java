package it.polimi.ingsw.PSP11.model;

import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cards;

    /**
     * class constructor
     */
    public Deck(){
        cards = new ArrayList<>();
    }


    /**
     * add a card to the deck
     * @param card card that will be added to the deck
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /**
     * return the card with the index i
     * @param i index of the card that get returned
     * @return the card with the index i
     */
    public Card pickGod(int i){
        return cards.get(i);
    }

}
