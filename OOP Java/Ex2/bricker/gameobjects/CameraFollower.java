package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents the Camera that follows after game object with counting ability.
 * Stop following after the count gets to the maximum value.
 * author Tal Yehezkel
 */
public class CameraFollower extends GameObject {
    private final BrickerGameManager brickerGameManager;
    private final CounterGameObject gameObjectToFollow;
    private final int maxCollisionTillStopFollowing;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner             Position of the object, in window coordinates (pixels).
     *                                  Note that (0,0) is the top-left corner of the window.
     * @param dimensions                Width and height in window coordinates.
     * @param renderable                The renderable representing the object. Can be null, in which case
     *                                  the GameObject will not be rendered.
     * @param brickerGameManager        The manager of the game.
     * @param gameObjectToFollow        The game object the camera should follow.
     * @param maxCountTillStopFollowing The maximum number of count until reset camera.
     */
    public CameraFollower(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                          BrickerGameManager brickerGameManager, CounterGameObject gameObjectToFollow,
                          int maxCountTillStopFollowing) {
        super(topLeftCorner, dimensions, renderable);
        this.brickerGameManager = brickerGameManager;
        this.gameObjectToFollow = gameObjectToFollow;
        this.maxCollisionTillStopFollowing = maxCountTillStopFollowing;
        startingCamera();
    }

    /**
     * Starting the camera and the counting to zero
     */
    private void startingCamera() {
        brickerGameManager.setCameraToFollow(gameObjectToFollow);
        gameObjectToFollow.setCount(0);
    }

    /**
     * Set the camera after the followed object get to his maximun count.
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
        int gameObjectToFollowCountNum = gameObjectToFollow.getCount();
        if (gameObjectToFollowCountNum > maxCollisionTillStopFollowing) {
            brickerGameManager.setCamera(null);
            brickerGameManager.removeGameObject(this);
        }
    }
}
