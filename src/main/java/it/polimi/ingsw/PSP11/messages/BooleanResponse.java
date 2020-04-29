package it.polimi.ingsw.PSP11.messages;

public class BooleanResponse extends Message{

    private boolean response;

    public BooleanResponse(boolean response) {
        super("");
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }
}
