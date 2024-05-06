package bricker.gameobjects;

/**
 * Interface thar represents the ability of counting.
 */
public interface Countable {
    /**
     * @return The count value.
     */
    int getCount();

    /**
     * Change the count value to the given value.
     *
     * @param newCount The new Count value to be saved.
     */
    void setCount(int newCount);

}
