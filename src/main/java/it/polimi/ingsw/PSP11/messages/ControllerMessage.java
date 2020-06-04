package it.polimi.ingsw.PSP11.messages;

import it.polimi.ingsw.PSP11.view.VirtualView;

public class ControllerMessage {

    private Message message;
    private VirtualView virtualView;

    /**
     * message used to update the controller
     */
    public ControllerMessage(Message message, VirtualView virtualView) {
        this.message = message;
        this.virtualView = virtualView;
    }

    public Message getMessage() {
        return message;
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }
}
