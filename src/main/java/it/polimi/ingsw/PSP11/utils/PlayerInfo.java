package it.polimi.ingsw.PSP11.utils;

import it.polimi.ingsw.PSP11.model.Color;

import java.io.Serializable;

public class PlayerInfo implements Serializable {

    private String name;
    private Color color;

    public PlayerInfo (String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

}
