package it.polimi.ingsw.PSP11.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {

    private Cell[][] board = new Cell[5][5];


    /**
     * initialize all the cell in the board
     */
    public void init() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = new Cell();
            }
        }
    }


    /**
     * @param point indicates the spot in witch we want to know if there is a player on top
     * @return true if the cell indicated by the point has a worker on top
     */
    public boolean hasWorkerOnTop(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].hasWorkerOnTop();
    }

    /**
     * @param point indicates the spot in witch we want to know if there is a dome on top
     * @return true id the cell indicated by the point has a dome on top
     */
    public boolean hasDomeOnTop(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].hasDomeOnTop();
    }

    /**
     * place the worker in the designated point
     * @param point indicates the spot in witch the player wants to put the worker
     */
    public void placeWorker(Point point, Worker worker) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].placeWorker(worker);
    }

    /**
     * @param point the position of worker
     * @return the worker on top of the cell
     */
    public Worker getWorker(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].getCurrentWorker();
    }

    /**
     * Remove the worker on top of the cell
     * @param point the position of worker
     */
    public void removeWorker(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].removeWorker();
    }


    /**
     * place the dome in the designated point
     * @param point indicates the spot in witch the player wants to put the dome
     */
    public void addDome(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].addDome();
    }

    /**
     * place the block in the designated point
     * @param point indicates the spot in witch the player wants to put the block
     */
    public void addBlock(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        board[x][y].addBlock();
    }

    /**
     * return the height of the building on the designated cell
     * @param point indicates the spot in witch we want to know the height of the building
     * @return the name of the block
     */
    public Block getCurrentLevel(Point point) {
        int x = (int) point.getX();
        int y = (int) point.getY();

        return board[x][y].getCurrentLevel();
    }

    /**
     * find all the neighbouring point to a certain point
     * @param position point of witch i want to know the adjacent points
     * @return ArrayList of point witch are adjacent to the position
     */
    public ArrayList<Point> getNeighbouringPoints(Point position) {
        ArrayList<Point> positions = new ArrayList<>();
        int x = (int) position.getX();
        int y = (int) position.getY();

        int startX = ((x - 1) < 0) ? x : x - 1;
        int startY = ((y - 1) < 0) ? y : y - 1;
        int endX = ((x + 1) > 4) ? x : x + 1;
        int endY = ((y + 1) > 4) ? y : y + 1;

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                positions.add(new Point(i, j));
            }
        }

        return positions;
    }

    /**
     * build a formatted string with the current state of the board
     * @return a string containing the board
     */
    public String printBoard() {
        String formattedMessage = "\n\n\n\n\n";
        formattedMessage = formattedMessage.concat("    1      2      3      4      5   y/x\n");

        for (int i = 0; i < 5; i++) {
            formattedMessage = formattedMessage.concat("------------------------------------\n");
            for (int j = 0; j < 5; j++) {
                formattedMessage = formattedMessage.concat("|" + board[i][j].getCurrentLevel().ordinal());
                if (board[i][j].hasWorkerOnTop()) {
                    formattedMessage = formattedMessage.concat(board[i][j].getCurrentWorker().getColor().getEscape() + "  W  " + Color.RESET);
                } else if (board[i][j].hasDomeOnTop()) {
                    formattedMessage = formattedMessage.concat(" D   ");
                } else {
                    formattedMessage = formattedMessage.concat("     ");
                }
            }
            formattedMessage = formattedMessage.concat("|");
            formattedMessage = formattedMessage.concat(" " + (i+1));
            formattedMessage = formattedMessage.concat("\n");
        }
        formattedMessage = formattedMessage.concat("------------------------------------\n");
        return formattedMessage;
    }

    /**
     * clone the current state of the board
     * @return a copy of the board
     */
    public Board boardClone() {
        Board clonedBoard = new Board();
        clonedBoard.init();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Cell copiedCell = board[i][j].cellClone();
                clonedBoard.addCell(copiedCell, i, j);
            }
        }
        return clonedBoard;
    }


    private void addCell (Cell newCell, int x, int y){
        this.board[x][y] = newCell;
    }
}