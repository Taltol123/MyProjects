package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.world.WorldConstants;

import java.awt.*;

/**
 * Represents the fruit in the game.
 *
 * @author Tal Yehezkel
 */
public class Fruit extends GameObject {
    private Color currentFruitsColor;

    /**
     * Create instance of fruit game object
     * * @param topLeftCorner - the top left corner of the created fruit
     */
    public Fruit(Vector2 topLeftCorner) {
        super(topLeftCorner, WorldConstants.FRUIT_DIMENSION,
                new OvalRenderable(WorldConstants.FRUIT_INITIAL_COLOR));
        this.currentFruitsColor = WorldConstants.FRUIT_INITIAL_COLOR;
        setTag(WorldConstants.FRUIT_TAG);
    }

    /**
     * Handle situation where the fruit and other object have a collision
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(WorldConstants.AVATAR_TAG)) {
            setDimensions(Vector2.ZERO);
            new ScheduledTask(
                    this,
                    WorldConstants.DAY_TIME,
                    false,
                    () -> setDimensions(WorldConstants.FRUIT_DIMENSION));
        }
    }

    /**
     * Handle the operations after the avatar jump.
     */
    public void updateAfterAvatarJump() {
        if (currentFruitsColor == WorldConstants.FRUIT_INITIAL_COLOR) {
            currentFruitsColor = WorldConstants.FRUIT_COLOR_WHEN_AVATAR_JUMPING;
        } else {
            currentFruitsColor = WorldConstants.FRUIT_INITIAL_COLOR;
        }
        renderer().setRenderable(new OvalRenderable(currentFruitsColor));
    }
}
