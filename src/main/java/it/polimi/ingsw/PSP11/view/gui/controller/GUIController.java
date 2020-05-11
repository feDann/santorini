package it.polimi.ingsw.PSP11.view.gui.controller;

import it.polimi.ingsw.PSP11.client.GUIClient;
import it.polimi.ingsw.PSP11.messages.Message;
import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

import java.util.ArrayList;

public abstract class GUIController {
    private GUIClient client;
    private static String nickname;
    private static Color color;
    private static ArrayList<PlayerInfo> opponents = new ArrayList<>();

    public abstract void changeStage();
    public abstract void handleMessage(Message message);

    public void send(Message message){
        client.asyncWrite(message);
    }

    public GUIClient getClient() {
        return client;
    }

    public void setClient(GUIClient client) {
        this.client = client;
    }


    public static String getNickname() {
        return nickname;
    }

    public static Color getColor() {
        return color;
    }

    public static void setColor(Color color) {
        GUIController.color = color;
    }

    public static void setNickname(String nickname) {
        GUIController.nickname = nickname;
    }

    public static ArrayList<PlayerInfo> getOpponents() {
        return opponents;
    }

    public static void setOpponents(ArrayList<PlayerInfo> opponents) {
        GUIController.opponents = opponents;
    }
}
