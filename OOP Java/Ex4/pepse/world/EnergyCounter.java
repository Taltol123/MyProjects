package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.WorldConstants;

import java.util.function.Supplier;

/**
 * Represents the numeric life counter object.
 * author Tal Yehezkel
 */
public class EnergyCounter extends GameObject {
    private final TextRenderable textRenderable;
    private final Supplier<Double> energyNumberGetter;

    /**
     * Create instance of the EnergyCounter game object.
     *
     * @param topLeftCorner      - the top left corner of the counter.
     * @param dimensions         - the dimension of the counter.
     * @param textRenderable     - the text renderable.
     * @param energyNumberGetter - callback that return the current energy of the avatar.
     */
    public EnergyCounter(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable textRenderable,
                         Supplier<Double> energyNumberGetter) {
        super(topLeftCorner, dimensions, textRenderable);
        this.textRenderable = textRenderable;
        this.energyNumberGetter = energyNumberGetter;
        setTag(WorldConstants.ENERGY_TAG);
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
        double lifeCount = energyNumberGetter.get();
        textRenderable.setString(lifeCount + WorldConstants.ENERGY_COUNTER_SIGN);
    }
}
