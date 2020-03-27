package it.polimi.ingsw.PSP11.model;

import java.awt.*;

public class Board {

    private Cell[][] board = new Cell[5][5];


    /**
     *  initialize all the cell in the board
     */
    public void init(){
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                board[i][j] = new Cell();
            }
        }
    }

    /**
     * @param point indicates the spot in witch we want to know if there is a player on top
     * @return true if the cell indicated by the point has a worker on top
     */
    public boolean hasWorkerOnTop(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].hasWorkerOnTop();
    }

    /**
     * @param point indicates the spot in witch we want to know if there is a dome on top
     * @return true id the cell indicated by the point has a dome on top
     */
    public boolean hasDomeOnTop(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].hasDomeOnTop();
    }

    /**
     * place the worker in the designated point
     * @param point indicates the spot in witch the player wants to put the worker
     */
    public void placeWorker(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].placeWorker();
    }

    /**
     * place the dome in the designated point
     * @param point indicates the spot in witch the player wants to put the dome
     */
    public void addDome(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].addDome();
    }

    /**
     * place the block in the designated point
     * @param point indicates the spot in witch the player wants to put the block
     */
    public void addBlock(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].addBlock();
    }

    /**
     * return the height of the building on the designated cell
     * @param point indicates the spot in witch we want to know the height of the building
     * @return the name of the block
     */
    public Block getCurrentLevel(Point point){
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].getCurrentLevel();
    }

}
