package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.WorldConstants;

import java.awt.*;

/**
 * Represents the night.
 *
 * @author Tal Yehezkel
 */
public class Night {

    /**
     * @param windowDimensions the dimensions of the window.
     * @param cycleLength      the time that represents a day.
     * @return the night game object created.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(WorldConstants.NIGHT_TAG);
        operateOpaqueness(cycleLength, night);
        return night;
    }

    /**
     * Do the operation of changing the opaqueness according to the day
     *
     * @param cycleLength the time that represents a day.
     * @param night       the night object.
     */
    private static void operateOpaqueness(float cycleLength, GameObject night) {
        new Transition<>(
                night,
                night.renderer()::setOpaqueness,
                WorldConstants.INITIAL_NIGHT_OPACITY,
                WorldConstants.MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength * WorldConstants.PART_OF_DAY_NIGHT_TRANSITION,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }
}
