package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents the Paddle object.
 * author Tal Yehezkel
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


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
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, BrickerGameManager brickerGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = brickerGameManager.getWindowDimension();
        setTag(Constants.TagConstants.PADDLE_TAG);
    }


    /**
     * Updating the paddle +
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
        Vector2 movement = Vector2.ZERO;
        movement = updateMovementByKeyPressed(movement);
        setVelocity(movement.mult(Constants.GameObjectsConstants.PADDLE_MOVEMENT_SPEED));
        updateMovementIfPaddleOutOfWindow();
    }

    /**
     * Change the movement according to what the user pressed.
     *
     * @param movement The paddle movement.
     * @return The updated movement.
     */
    private Vector2 updateMovementByKeyPressed(Vector2 movement) {
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);
        }
        return movement;
    }

    /**
     * Change paddle's movement if the paddle is closed to the walls.
     */
    private void updateMovementIfPaddleOutOfWindow() {
        Vector2 movement;
        float maxRightPlace = this.windowDimensions.x() -
                Constants.GameObjectsConstants.PADDLE_MIN_DIST_FROM_EDGE;
        float PaddleRightCorner = getTopLeftCorner().x() + getDimensions().x();
        if (PaddleRightCorner > maxRightPlace) {
            movement = Vector2.LEFT;
            setVelocity(movement.mult(Constants.GameObjectsConstants.PADDLE_MOVEMENT_SPEED));
        }
        if (getTopLeftCorner().x() < Constants.GameObjectsConstants.PADDLE_MIN_DIST_FROM_EDGE) {
            movement = Vector2.RIGHT;
            setVelocity(movement.mult(Constants.GameObjectsConstants.PADDLE_MOVEMENT_SPEED));
        }
    }
}

