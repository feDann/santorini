package it.polimi.ingsw.PSP11.model;

public class Player {

    private String nickname;
    private boolean eliminated;
    private Turn playerTurn;
    private Card god;


    public Player (String chosenName) {

        this.nickname = chosenName;
        this.eliminated = false;
        this.playerTurn = null;
        this.god = null;

    }





}
