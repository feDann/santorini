package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.model.Card;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

import java.util.ArrayList;

public abstract class GUIController {
    private GUIClient client;
    private static String nickname;
    private static Color color;
    private static ArrayList<PlayerInfo> opponents = new ArrayList<>();
    private static Card playerCard;

    /**
     * This method load the new fxml file and change the current stage with the new one
     */
    public abstract void changeStage();

    /**
     * this method handle the message received by the {@link GUIClient}
     * @param message the message received by the {@link GUIClient}
     */
    public abstract void handleMessage(Message message);

    /**
     * This method send a message to the server
     * @param message the message to send
     */
    public void send(Message message){
        client.asyncWrite(message);
    }

    /**
     *
     * @return the {@link GUIController#client} reference
     */
    public GUIClient getClient() {
        return client;
    }

    /**
     * Set the {@link GUIController#client} attribute
     * @param client the {@link GUIClient} object
     */

    public void setClient(GUIClient client) {
        this.client = client;
    }

    /**
     *
     * @return the {@link GUIController#nickname} reference
     */

    public static String getNickname() {
        return nickname;
    }

    /**
     *
     * @return the {@link GUIController#color} associated to the player
     */

    public static Color getColor() {
        return color;
    }

    /**
     * Set the {@link GUIController#color } of the player
     * @param color the {@link Color } object of the player
     */

    public static void setColor(Color color) {
        GUIController.color = color;
    }

    /**
     * Set the {@link GUIController#nickname} attribute of the player
     * @param nickname the nickname of the player as string
     */

    public static void setNickname(String nickname) {
        GUIController.nickname = nickname;
    }

    /**
     *
     * @return the {@link GUIController#opponents} arraylist of {@link PlayerInfo} representing the opponents attributes
     */

    public static ArrayList<PlayerInfo> getOpponents() {
        return opponents;
    }

    /**
     * set the {@link GUIController#opponents} attribute
     * @param opponents the {@code ArrayList<PlayerInfo>} structure
     */

    public static void setOpponents(ArrayList<PlayerInfo> opponents) {
        GUIController.opponents = opponents;
    }

    /**
     *
     * @return the {@link GUIController#playerCard} object representing the God's information of the player
     */

    public static Card getPlayerCard() {
        return playerCard;
    }

    /**
     * Set the {@link GUIController#playerCard} attribute
     * @param playerCard the {@link Card} object representing the God's information of the player
     */

    public static void setPlayerCard(Card playerCard) {
        GUIController.playerCard = playerCard;
    }
}
