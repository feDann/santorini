package it.polimi.ingsw.PSP11.messages;

public class SelectPlayerGodResponse extends Message{

    private int id;

    /**
     * message used to communicate to the server the id of the card that a player has chosen
     * @param id of the chosen card
     */
    public SelectPlayerGodResponse(int id) {
        super("id of the chosen card: " + id);
        this.id = id;

    }

    public int getId() {
        return id;
    }

}
