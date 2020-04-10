package it.polimi.ingsw.PSP11.observer;

public interface Observer<T> {

    void update(T message);

}