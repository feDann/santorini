package it.polimi.ingsw.PSP11.messages;

public class UpdateLoseMessage extends SimpleMessage {

    private String player;

    /**
     * message used to notify all the player when z
     * @param player
     */
    public UpdateLoseMessage(String player){
        super(player + " ha perso, F in the chat!\n");
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
