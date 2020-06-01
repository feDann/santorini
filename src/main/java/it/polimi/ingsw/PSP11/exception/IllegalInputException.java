package it.polimi.ingsw.PSP11.exception;

/**
 * Class exception for an illegal input from {@link it.polimi.ingsw.PSP11.client.CLIClient}
 */
public class IllegalInputException extends Throwable {

    private String errorMessage;

    /**
     * Class constructor
     * @param s the message to be displayed to the player
     */
    public IllegalInputException(String s) {
        errorMessage = s;
    }

    /**
     * Getter method
     * @return the error message
     */
    public String getErrorMessage(){
        return errorMessage;
    }
}
