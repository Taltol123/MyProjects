package exceptions;

/**
 *  Invalid command exception.
 * author Tal Yehezkel
 */
public class InvalidCommandException extends Exception{
    /**
     * Construct invalidCommandException.
     * @param msg ,essage.
     */
    public InvalidCommandException(String msg){super(msg);}
}
