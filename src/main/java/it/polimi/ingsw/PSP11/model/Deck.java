package it.polimi.ingsw.PSP11.model;

import java.util.ArrayList;

public class Deck{

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

    public Deck deckClone() {
        Deck deckCopy = new Deck();
        for(Card card : cards){
            Card cardCopy = new Card();
            cardCopy.setIdCard(card.getIdCard());
            cardCopy.setName(card.getName());
            cardCopy.setDescription(card.getDescription());
            deckCopy.addCard(cardCopy);
        }
        return deckCopy;
    }
}
