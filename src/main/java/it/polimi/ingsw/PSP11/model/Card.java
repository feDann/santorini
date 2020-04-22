package it.polimi.ingsw.PSP11.model;

import java.io.Serializable;

public class Card implements Serializable {
    private GodTurn godTurnDecorator;
    private String name;
    private int idCard;
    //texture??
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
}
