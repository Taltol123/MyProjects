package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * Represents a Game object with the ability to count.
 * author Tal Yehezkel
 */
public class CounterGameObject extends GameObject implements Countable {
    private final Counter counter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public CounterGameObject(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        counter = new Counter(0);
    }

    /**
     * @return the current count.
     */
    public int getCount() {
        return counter.value();
    }

    /**
     * Set the count to another value.
     *
     * @param count The new count value.
     */
    public void setCount(int count) {
        counter.reset();
        counter.increaseBy(count);
    }
}
