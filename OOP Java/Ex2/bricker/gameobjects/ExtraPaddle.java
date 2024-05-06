package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the Game object extra-paddle.
 * author Tal Yehezkel
 */
public class ExtraPaddle extends Paddle {
    private final BrickerGameManager brickerGameManager;
    private int collisionCount;
    private List<String> objectTagsCauseDisappear;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner      Position of the object, in window coordinates (pixels).
     *                           Note that (0,0) is the top-left corner of the window.
     * @param dimensions         Width and height in window coordinates.
     * @param renderable         The renderable representing the object. Can be null, in which case
     *                           the GameObject will not be rendered.
     * @param inputListener      Helps to move the paddle according to what the user press on the keyboard.
     * @param brickerGameManager The manager of the game.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable, inputListener, brickerGameManager);
        this.brickerGameManager = brickerGameManager;
        this.collisionCount = 0;
        setTag(Constants.TagConstants.EXTRA_PADDLE_TAG);
        initializeTagsOfObjectsCausedDisappear();
    }

    /**
     * Initialize the object's tags that can collided with the extra-paddle and make it disappear.
     */
    private void initializeTagsOfObjectsCausedDisappear() {
        objectTagsCauseDisappear = new ArrayList<>();
        objectTagsCauseDisappear.add(Constants.TagConstants.BALL_TAG);
        objectTagsCauseDisappear.add(Constants.TagConstants.PUCK_TAG);
    }


    /**
     * Handle the disappear after it collided the max time it can.
     * The operations that  need to be done when the Extra-paddle had a collision.
     *
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        if (objectTagsCauseDisappear.contains(other.getTag())) {
            this.collisionCount += 1;
        }
        if (this.collisionCount == Constants.StrategiesConstants.EXTRA_PADDLE_MAX_COLLISION) {
            brickerGameManager.removeGameObject(this);
            brickerGameManager.setCounterValue(CounterKinds.ExtraPaddleCounter,
                    Constants.GameConstants.INITIAL_EXTRA_PADDLE_NUM);
        }
    }
}
