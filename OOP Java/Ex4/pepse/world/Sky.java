package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the sky.
 *
 * @author Tal Yehezkel
 */
public class Sky {

    /**
     * Create instance of the game object - sky.
     *
     * @param windowDimensions - the dimension of the window.
     * @return the sky game object created.
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(
                Vector2.ZERO, windowDimensions,
                new RectangleRenderable(WorldConstants.BASIC_SKY_COLOR));
        new RectangleRenderable(WorldConstants.BASIC_SKY_COLOR);

        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(WorldConstants.SKY_TAG);

        return sky;
    }
}
