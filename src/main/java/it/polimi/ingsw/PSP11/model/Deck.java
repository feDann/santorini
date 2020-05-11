package it.polimi.ingsw.PSP11.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Deck implements Serializable {

    private ArrayList<Card> cards;

    /**
     * class constructor
     */
    public Deck(){
        cards = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
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

    /**
     * clone the deck
     * @return a copy of the deck
     */
    public Deck deckClone() {
        Deck deckCopy = new Deck();
        for(Card card : cards){
            Card cardCopy = card.cardClone();
            deckCopy.addCard(cardCopy);
        }
        return deckCopy;
    }
}
