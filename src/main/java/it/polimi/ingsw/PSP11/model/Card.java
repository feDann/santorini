package it.polimi.ingsw.PSP11.model;

import java.io.Serializable;

public class Card implements Serializable {
    private GodTurn godTurnDecorator;
    private String name;
    private String texture;
    private int idCard;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCard() {
        return idCard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }

    public void setTurn(StandardTurn sharedTurn) {
        godTurnDecorator.setSharedTurn(sharedTurn);
    }

    public GodTurn getGodTurnDecorator() {
        return godTurnDecorator;
    }

    public void setGodTurnDecorator(GodTurn cardTurnDecorator) {
        this.godTurnDecorator = cardTurnDecorator;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    /**
     * redefinition of the equals function for this specific class
     * @param obj the card which we want to compare
     * @return true id the two card have the same name, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Card){
            return ((Card) obj).getName().equals(this.getName());
        }
        return false;
    }

    /**
     * redefinition of the clone function for this specific class
     * @return a copy of the card on which this function is called
     */
    public Card cardClone(){
        Card clonedCard = new Card();
        clonedCard.setName(this.getName());
        clonedCard.setDescription(this.getDescription());
        clonedCard.setIdCard(this.getIdCard());
        clonedCard.setTexture(this.getTexture());
        return clonedCard;
    }
}
