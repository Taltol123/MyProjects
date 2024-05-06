package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents the game object - heart.
 * author Tal Yehezkel
 */
public class Heart extends GameObject {
    private final Vector2 windowDim;
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner      Position of the object, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param brickerGameManager The manager of the game.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDim = brickerGameManager.getWindowDimension();
        this.brickerGameManager = brickerGameManager;
        setTag(Constants.TagConstants.HEART_TAG);
    }

    /**
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

        if (getCenter().y() > this.windowDim.y() || getCenter().y() <
                Constants.GameConstants.MINIMAL_WINDOW_Y) {
            brickerGameManager.removeGameObject(this);
        }
    }

    /**
     * Makes this object to collided only with the paddle.
     *
     * @param other The other GameObject.
     * @return True when other is paddle false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(Constants.TagConstants.PADDLE_TAG)) {
            return true;
        }
        return false;
    }

    /**
     * Incerase lide count if collided with the paddle.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        int currentLifeCount = brickerGameManager.getCounterValue(CounterKinds.LifeCounter);
        if (currentLifeCount < Constants.GameConstants.MAX_LIFE_NUM) {
            brickerGameManager.incrementCounter(CounterKinds.LifeCounter);
        }
        brickerGameManager.removeGameObject(this);
    }
}
