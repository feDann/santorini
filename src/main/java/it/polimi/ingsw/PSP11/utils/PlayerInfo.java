package it.polimi.ingsw.PSP11.utils;

import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;

import java.io.Serializable;

/**
 * Class that contains basic player info
 */
public class PlayerInfo implements Serializable {

    private String name;
    private Color color;
    private Card card;

    /**
     * Class constructor
     * @param name the name of the player
     * @param color the color of the player
     */
    public PlayerInfo (String name, Color color){
        this.name = name;
        this.color = color;
    }

    /**
     * Getter method for name
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for color
     * @return player color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter method for card
     * @return player god card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Setter method for player god card
     * @param card player god card
     */
    public void setCard(Card card) {
        this.card = card;
    }
}
