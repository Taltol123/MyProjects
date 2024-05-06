package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Component;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.WorldConstants;

/**
 * Represents the sun-halo.
 *
 * @author Tal Yehezkel
 */
public class SunHalo {

    /**
     * Create the sun-halo game object.
     *
     * @param sun the sun game-object created.
     * @return the sun-halo game object created.
     */
    public static GameObject create(GameObject sun) {
        OvalRenderable ovalRenderable = new OvalRenderable(WorldConstants.HALO_COLOR);
        GameObject sunHalo = new GameObject(Vector2.ZERO,
                new Vector2(sun.getDimensions().x() +
                        WorldConstants.HALO_SIZE_RELATIVE_TO_SUN,
                        sun.getDimensions().y() +
                                WorldConstants.HALO_SIZE_RELATIVE_TO_SUN),
                ovalRenderable);
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        updateHaloCenter(sunHalo, sun);
        sunHalo.setTag(WorldConstants.SUN_HALO_TAG);

        return sunHalo;
    }

    /**
     * Update the halo center to be as the sun.
     *
     * @param sunHalo the sun-halo game object.
     * @param sun     the sun game object.
     */
    private static void updateHaloCenter(GameObject sunHalo, GameObject sun) {
        Component updateSunHalo = (deltaTime) -> {
            sunHalo.setCenter(sun.getCenter());
        };
        sunHalo.addComponent(updateSunHalo);
    }
}
