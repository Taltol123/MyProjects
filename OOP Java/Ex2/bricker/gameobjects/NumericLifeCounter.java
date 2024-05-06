package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Hashtable;
import java.util.Map;

/**
 * Represents the numeric life counter object.
 * author Tal Yehezkel
 */
public class NumericLifeCounter extends GameObject {
    private final BrickerGameManager brickGameManager;
    private final TextRenderable textRenderable;
    private Map<Integer, Color> lifeCountNumberColorMapping;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param brickGameManager The manager of the game.
     */
    public NumericLifeCounter(BrickerGameManager brickGameManager, Vector2 topLeftCorner, Vector2 dimensions,
                              TextRenderable textRenderable) {
        super(topLeftCorner, dimensions, textRenderable);
        this.brickGameManager = brickGameManager;
        this.textRenderable = textRenderable;
        setTag(Constants.TagConstants.NUMERIC_LIFE_TAG);
        initializeNumberAndColorMapping();
    }

    /**
     * Initializing the mapping between number of lifes to the represented color.
     */
    private void initializeNumberAndColorMapping() {
        lifeCountNumberColorMapping = new Hashtable<>();
        lifeCountNumberColorMapping.put(4, Color.GREEN);
        lifeCountNumberColorMapping.put(3, Color.GREEN);
        lifeCountNumberColorMapping.put(2, Color.YELLOW);
        lifeCountNumberColorMapping.put(1, Color.RED);
    }

    /**
     * Makes the other object to not collided with this object.
     *
     * @param other The other GameObject.
     * @return False - other object should not collided with this object.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return false;
    }


    /**
     * Update the number and color to fit the current lide number.
     *
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        adjustNumberAndColor();
    }

    /**
     * Check what is the current life counter and adjust the number and color.
     */
    private void adjustNumberAndColor() {
        int lifeCount = brickGameManager.getCounterValue(CounterKinds.LifeCounter);
        textRenderable.setString(String.valueOf(lifeCount));
        Color color = lifeCountNumberColorMapping.get(lifeCount);
        if (color == null) {
            color = Color.GREEN;
        }
        textRenderable.setColor(color);
    }
}
