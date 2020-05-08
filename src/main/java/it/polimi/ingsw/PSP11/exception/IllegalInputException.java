package it.polimi.ingsw.PSP11.exception;

public class IllegalInputException extends Throwable {

    private String errorMessage;

    public IllegalInputException(String s) {
        errorMessage = s;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
