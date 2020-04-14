package it.polimi.ingsw.PSP11.exception;

import it.polimi.ingsw.PSP11.messages.Message;

public class IllegalInputException extends Throwable {

    private String errorMessage;

    public IllegalInputException(String s) {
        errorMessage = s;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
