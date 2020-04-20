package it.polimi.ingsw.PSP11.messages;

public class SelectPlayerGodResponse extends Message{

    private int id;

    public SelectPlayerGodResponse(int id) {
        super("id of the chosen card: " + id);
        this.id = id;

    }

    public int getId() {
        return id;
    }

}
