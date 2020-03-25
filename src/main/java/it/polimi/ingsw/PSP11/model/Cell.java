package it.polimi.ingsw.PSP11.model;

public class Cell {

    private Block currentLevel;
    private boolean dome;
    private boolean worker;

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
    public void placeWorker(){
        this.worker = true;
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

}
