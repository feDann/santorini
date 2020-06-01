package it.polimi.ingsw.PSP11.observer;

/**
 * Interface for observer
 * @param <T> type of the update message
 */
public interface Observer<T> {

    /**
     * Method called by the observed class to notify the observer
     * @param message the incoming message to be handled
     */
    void update(T message);

}