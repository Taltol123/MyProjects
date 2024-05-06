package bricker.brick_strategies;

import bricker.gameobjects.GameObjectFactory;
import bricker.gameobjects.GameObjectTypes;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;

/**
 * Represents the more balls collision strategy.
 * author Tal Yehezkel
 */
public class MoreBallsStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final GameObjectFactory gameObjectCollectionFactory;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * @param brickerGameManager          The manager of the game.
     * @param gameObjectCollectionFactory The game objects factory.
     * @param basicCollisionStrategy      The basic collision strategy this strategy have.
     */
    public MoreBallsStrategy(BrickerGameManager brickerGameManager
            , GameObjectFactory gameObjectCollectionFactory,
                             BasicCollisionStrategy basicCollisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * When collided creates the balls.
     *
     * @param thisObj  The current object.
     * @param otherObj The other object collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.basicCollisionStrategy.onCollision(thisObj, otherObj);

        for (int i = 0; i < Constants.StrategiesConstants.NUM_PUCKS; i++) {
            GameObject puck = gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.Puck).get(0);
            puck.setTopLeftCorner(thisObj.getCenter());
            brickerGameManager.addGameObject(puck);
        }
    }
}
