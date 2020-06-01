package it.polimi.ingsw.PSP11.client;

/**
 * Interface for implementing client-server ping feature
 */
public interface Pinger {

    /**
     * Uses a ScheduledExecutorService and a Runnable to schedule a ping message
     */
    void pinger();

    /**
     * Cancels the scheduled ping runnable (created in pinger()) using its ScheduledFuture and also shuts down the ScheduledExecutorService
     */
    void killPinger();
}
