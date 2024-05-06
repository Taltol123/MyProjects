package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Represents the Game object ball.
 * author Tal Yehezkel
 */
public class Ball extends CounterGameObject {
    private final Sound sound;
    private final Vector2 windowDimensions;
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new instance of ball.
     *
     * @param topLeftCorner      Position of the object, in window coordinates.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object.
     * @param sound              The sound when the ball collision.
     * @param brickerGameManager The manager of the game.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound sound,
                BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.sound = sound;
        this.windowDimensions = brickerGameManager.getWindowDimension();
        this.brickerGameManager = brickerGameManager;
        setTag(Constants.TagConstants.BALL_TAG);
        setStartingBallVelocity();
    }


    /**
     * The operations that need to be done when the ball had a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        setCount(getCount() + 1);
        Vector2 newVec = getVelocity().flipped(collision.getNormal());
        setVelocity(newVec);
        sound.play();
    }

    /**
     * Update the ball place when it go out of windows.
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

        double ballHeight = getCenter().y();
        if (ballHeight < Constants.GameConstants.MINIMAL_WINDOW_Y) {
            setStartingBallVelocity();
        }

        if (ballHeight > this.windowDimensions.y()) {
            brickerGameManager.decrementCounter(CounterKinds.LifeCounter);
            setStartingBallVelocity();
        }
    }

    /**
     * Set the starting velocity of the ball.
     */
    private void setStartingBallVelocity() {
        setCenter(this.windowDimensions.mult(Constants.GameObjectsConstants.CENTER_FACTOR));
        int ballVelX = Constants.GameObjectsConstants.BALL_VELOCITY_X;
        int ballVelY = Constants.GameObjectsConstants.BALL_VELOCITY_Y;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }

        setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * @return the collision count.
     */
    public int getCollisionCounter() {
        return getCount();
    }
}
