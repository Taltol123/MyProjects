package pepse.util;

/**
 * Contains helping operations for another classes use.
 * @author Tal Yehezkel
 */
public class HelpingOperations {

    /**
     * Round the number according to the given factor.
     *
     * @param number the given number.
     * @param factor - the given factor.
     * @return the round result.
     */
    public static int round(int number, int factor) {
        if (number >= 0) {
            return number - (number % factor);
        } else {
            return number - (factor + (number % factor));
        }
    }
}
