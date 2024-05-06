package exceptions;

/**
 * Incorrect format exception.
 * author Tal Yehezkel
 */
public class IncorrectFormatException extends Exception {
    /**
     * Construct the exception.
     * @param msg message.
     */
    public IncorrectFormatException(String msg){super(msg);}
}

