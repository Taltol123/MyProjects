package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents the Game object puck.
 * author Tal Yehezkel
 */
public class Puck extends GameObject {
    private final Sound sound;
    private final BrickerGameManager brickerGameManager;
    private final Vector2 windowDimensions;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param sound         The sound when the puck collision.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound sound,
                BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.sound = sound;
        this.brickerGameManager = brickerGameManager;
        this.windowDimensions = brickerGameManager.getWindowDimension();
        setTag(Constants.TagConstants.PUCK_TAG);
    }


    /**
     * Check if puck is outside the windows.
     *
     * @return True if the puck is outside the windows and False otherwise.
     */
    private boolean isPuckOutsideWindow() {
        double puckHeight = getCenter().y();
        if (puckHeight < Constants.GameConstants.MINIMAL_WINDOW_Y || puckHeight > windowDimensions.y()) {
            return true;
        }
        return false;
    }

    /**
     * Remove the puck from the game object if it outside the window.
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

        if (isPuckOutsideWindow()) {
            brickerGameManager.removeGameObject(this);
        }
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Random random = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Constants.GameObjectsConstants.BALL_VELOCITY_X;
        float velocityY = (float) Math.sin(angle) * Constants.GameObjectsConstants.BALL_VELOCITY_Y;
        setVelocity((new Vector2(velocityX, velocityY)).flipped(collision.getNormal()));
        sound.play();
    }
}
