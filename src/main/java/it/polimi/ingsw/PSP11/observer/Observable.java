package it.polimi.ingsw.PSP11.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class extended by the classes that need to be observed
 * @param <T> type of the update message
 */
public class Observable<T> {

    private final List<Observer<T>> observers = new ArrayList<>();

    /**
     * Adds an observer to the observers list
     * @param observer the observer to add
     */
    public void addObserver(Observer<T> observer){
        synchronized (observers) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the observers list
     * @param observer the observer to be removed
     */
    public void removeObserver(Observer<T> observer){
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    /**
     * Calls update method on all the observers in the observers list
     * @param message the incoming message to be handled
     */
    public void notify(T message){
        synchronized (observers) {
            for(Observer<T> observer : observers){
                observer.update(message);
            }
        }
    }

}
