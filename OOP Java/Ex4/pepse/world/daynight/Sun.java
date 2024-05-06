package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.WorldConstants;

import java.awt.*;

/**
 * Represent the sun in the game.
 *
 * @author Tal Yehezkel
 */
public class Sun {

    /**
     * Create instance of the sun.
     *
     * @param windowDimensions the dimensions of the window.
     * @param cycleLength      the time that represents a day.
     * @return the sun game object.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        OvalRenderable ovalRenderable = new OvalRenderable(Color.YELLOW);
        float skyMiddleY = windowDimensions.y() * (1 - WorldConstants.GROUND_RELATIVE_AREA) / 2;
        float windowMiddleX = windowDimensions.x() / 2;

        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(WorldConstants.SUN_SIZE,
                WorldConstants.SUN_SIZE),
                ovalRenderable);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setCenter(new Vector2(windowMiddleX, skyMiddleY));
        sun.setTag(WorldConstants.SUN_TAG);

        makeSunCircle(sun, cycleLength, windowMiddleX, windowDimensions);
        return sun;
    }


    /**
     * @param sun              the sun game object.
     * @param cycleLength      the time that represents a day.
     * @param windowMiddleX    the missle of the window in x axis.
     * @param windowDimensions the dimensions of the window.
     */
    private static void makeSunCircle(GameObject sun, float cycleLength, float windowMiddleX,
                                      Vector2 windowDimensions) {
        float groundHeightAtX0 = windowDimensions.y() * WorldConstants.START_HEIGHT_GROUND_PART;
        Vector2 initialSunCenter = sun.getCenter();
        Vector2 cycleCenter = new Vector2(windowMiddleX, groundHeightAtX0);
        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter
                        (initialSunCenter.subtract(cycleCenter)
                                .rotated(angle)
                                .add(cycleCenter)),
                WorldConstants.INITIAL_SUN_TRANSITION,
                WorldConstants.END_SUN_TRANSITION,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
        );
    }


}
