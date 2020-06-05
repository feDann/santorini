package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.model.Color;
import it.polimi.ingsw.PSP11.utils.PlayerInfo;

import java.awt.*;

public class WorkerUpdateMessage extends SimpleMessage{
    /**
     * message used to update all the player about the move of the current player
     * @param player the current player
     * @param point in which the current player has moved
     */
    public WorkerUpdateMessage(PlayerInfo player, Point point) {
        super( player.getColor().getEscape() + player.getName()  + Color.RESET + " has placed his worker in (" + (point.x + 1) + "," + (point.y + 1) + ")\n\n");
    }

}
