package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

import java.awt.*;

public class BuildUpdateMessage extends SimpleMessage{

    public BuildUpdateMessage(PlayerInfo player, Point point) {
        super( player.getColor().getEscape() + player.getName()  + Color.RESET + " has built in (" + (point.x + 1) + "," + (point.y + 1) + ")\n\n");
    }
}
