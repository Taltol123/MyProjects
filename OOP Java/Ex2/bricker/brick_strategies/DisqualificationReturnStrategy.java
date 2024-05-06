package bricker.brick_strategies;

import bricker.gameobjects.GameObjectFactory;
import bricker.gameobjects.GameObjectTypes;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Represents the disqualification return collision strategy.
 * author Tal Yehezkel
 */
public class DisqualificationReturnStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final GameObjectFactory gameObjectCollectionFactory;
    private final BasicCollisionStrategy basicCollisionStrategy;


    /**
     * Construct disqualification return strategy.
     *
     * @param brickerGameManager          The manager of the game.
     * @param gameObjectCollectionFactory The game objects factory.
     * @param basicCollisionStrategy      The basic collision strategy this strategy have.
     */
    public DisqualificationReturnStrategy(BrickerGameManager brickerGameManager,
                                          GameObjectFactory gameObjectCollectionFactory,
                                          BasicCollisionStrategy basicCollisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * Creates an heart to go down when collided.
     *
     * @param thisObj  Current object.
     * @param otherObj The other object collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basicCollisionStrategy.onCollision(thisObj, otherObj);
        float topLeftCornerX =
                thisObj.getTopLeftCorner().x() +
                        (thisObj.getDimensions().x() - Constants.GameObjectsConstants.HEART_DIM_X) *
                                Constants.GameObjectsConstants.HEART_PLACE_IN_BRICK;
        float topLeftCornerY = thisObj.getTopLeftCorner().y();
        GameObject heart = gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.Heart).get(0);
        heart.setTopLeftCorner(new Vector2(topLeftCornerX, topLeftCornerY));
        heart.setVelocity(new Vector2(Constants.GameObjectsConstants.HEART_VELOCITY_X,
                Constants.GameObjectsConstants.HEART_VELOCITY_Y));
        brickerGameManager.addGameObject(heart);
    }
}
