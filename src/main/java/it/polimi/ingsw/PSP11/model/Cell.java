package it.polimi.ingsw.PSP11.model;

import java.io.Serializable;

public class Cell implements Serializable {

    private Block currentLevel;
    private boolean dome;
    private boolean worker;
    private Worker currentWorker;

    /**
     * constructor of the default cell
     */
    public Cell(){
        this.dome = false;
        this.worker = false;
        this.currentLevel = Block.GROUND;
    }

    /**
     * @return true if the cell has a worker on top, false otherwise
     */
    public boolean hasWorkerOnTop(){
        return this.worker;
    }

    /**
     * @return true if the cell has a dome on top, false otherwise
     */
    public boolean hasDomeOnTop(){
        return this.dome;
    }

    /**
     * place the worker on the cell
     */
    public void placeWorker(Worker worker){
        this.worker = true;
        this.currentWorker = worker;
    }

    /**
     * place a dome on the cell
     */
    public void addDome(){
        this.dome = true;
    }

    /**
     * add a block on the current cell
     */
    public void addBlock(){
        currentLevel = Block.values()[currentLevel.ordinal() + 1];
    }

    /**
     * @return the block on the current cell
     */
    public Block getCurrentLevel(){
        return this.currentLevel;
    }

    /**
     * place the desired building on the cell
     */
    private void setCurrentLevel(Block block){
        this.currentLevel = block;
    }

    /**
     * remove the worker from the cell
     */
    public void removeWorker(){
        this.worker = false;
        currentWorker = null;
    }

    /**
     * getter for the worker on the cell
     * @return the worker if present, null otherwise
     */
    public Worker getCurrentWorker() {
        if(worker = true){
            return currentWorker;
        }
        return null;
    }

    /**
     * clone the current cell
     * @return a copy of the cell
     */
    public Cell cellClone(){
        Cell cellCopy = new Cell();
        if (this.hasDomeOnTop()){
            cellCopy.addDome();
        }
        if (this.hasWorkerOnTop()){
            cellCopy.placeWorker(this.currentWorker.workerClone());
        }
        cellCopy.setCurrentLevel(this.getCurrentLevel());
        return cellCopy;
    }


}