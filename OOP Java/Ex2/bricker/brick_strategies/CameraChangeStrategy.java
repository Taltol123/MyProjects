package bricker.brick_strategies;

import bricker.gameobjects.GameObjectFactory;
import bricker.gameobjects.GameObjectTypes;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the camera changes collision strategy.
 * author Tal Yehezkel
 */
public class CameraChangeStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final BasicCollisionStrategy basicCollisionStrategy;
    private final GameObjectFactory gameObjectCollectionFactory;
    private List<String> objectTagsCauseStartingCamera;

    /**
     * Construct a Camera changes strategy.
     *
     * @param brickerGameManager          The manager of the game.
     * @param gameObjectCollectionFactory The game objects factory.
     * @param basicCollisionStrategy      The basuc collision strategy this strategy have.
     */
    public CameraChangeStrategy(BrickerGameManager brickerGameManager,
                                GameObjectFactory gameObjectCollectionFactory
            , BasicCollisionStrategy basicCollisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        this.basicCollisionStrategy = basicCollisionStrategy;
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        initializeTagsOfObjectsStartingCamera();
    }

    /**
     * Initialize object's tags that can start the camera.
     */
    private void initializeTagsOfObjectsStartingCamera() {
        objectTagsCauseStartingCamera = new ArrayList<>();
        objectTagsCauseStartingCamera.add(Constants.TagConstants.BALL_TAG);
    }

    /**
     * Remove brick and starting camera to follow object.
     *
     * @param thisObj  Current object.
     * @param otherObj The other object collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        if (objectTagsCauseStartingCamera.contains(otherObj.getTag())) {
            if (this.brickerGameManager.camera() == null) {
                GameObject cameraFollower =
                        gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.CameraFollower).get(0);
                brickerGameManager.addGameObject(cameraFollower);
            }
        }
    }
}
