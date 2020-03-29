package it.polimi.ingsw.PSP11.model;

public abstract class Card {
    private GodTurn cardTurn;
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


    public void setTurn(Turn sharedTurn) {

    }

    public GodTurn getTurn() {
        return cardTurn;
    }

}
