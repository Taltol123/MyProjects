package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents the Game object ball.
 * author Tal Yehezkel
 */
public class Brick extends GameObject {
    private final CollisionStrategy strategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param strategy      The brick strategy when it collided by another object.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;
        setTag(Constants.TagConstants.BRICK_TAG);
    }


    /**
     * The operations that  need to be done when the brick had a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.strategy.onCollision(this, other);
    }

}
