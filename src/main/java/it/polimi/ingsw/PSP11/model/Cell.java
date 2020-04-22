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
     * set worker attributes to true
     */
    public void placeWorker(Worker worker){
        this.worker = true;
        this.currentWorker = worker;
    }

    /**
     * set dome attributes to true
     */
    public void addDome(){
        this.dome = true;
    }

    /**
     * increases the value of currentLevel
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

    private void setCurrentLevel(Block block){
        this.currentLevel = block;
    }

    public void removeWorker(){
        this.worker = false;
        currentWorker = null;
    }

    /**
     *
     * @return if there, the current worker on top, null otherwise
     */
    public Worker getCurrentWorker() {
        if(worker = true){
            return currentWorker;
        }
        return null;
    }

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