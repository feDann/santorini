package it.polimi.ingsw.PSP11.messages;

public class BooleanResponse extends Message{

    private boolean response;

    /**
     * receive a yes or no answer and set a variable to true or false
     * @param response the response of the player
     */
    public BooleanResponse(boolean response) {
        super("");
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }
}
