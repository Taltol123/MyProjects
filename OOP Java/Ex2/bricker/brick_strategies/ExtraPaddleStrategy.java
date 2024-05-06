package bricker.brick_strategies;

import bricker.gameobjects.GameObjectFactory;
import bricker.gameobjects.GameObjectTypes;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;

/**
 * Represents the extra paddle collision strategy.
 * author Tal Yehezkel
 */
public class ExtraPaddleStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;
    private final GameObjectFactory gameObjectCollectionFactory;
    private final BasicCollisionStrategy basicCollisionStrategy;

    /**
     * @param brickerGameManager          The manager of the game.
     * @param gameObjectCollectionFactory The game objects factory.
     * @param basicCollisionStrategy      The basuc collision strategy this strategy have.
     */
    public ExtraPaddleStrategy(BrickerGameManager brickerGameManager,
                               GameObjectFactory gameObjectCollectionFactory,
                               BasicCollisionStrategy basicCollisionStrategy) {
        this.brickerGameManager = brickerGameManager;
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        this.basicCollisionStrategy = basicCollisionStrategy;
    }

    /**
     * After collision adding extra paddle if it possibe.
     *
     * @param thisObj  The current object.
     * @param otherObj The other object collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.basicCollisionStrategy.onCollision(thisObj, otherObj);

        if (brickerGameManager.getCounterValue(CounterKinds.ExtraPaddleCounter) <
                Constants.GameObjectsConstants.EXTRA_PADDLE_MAX_APPEARANCES) {
            GameObject extraPaddle =
                    gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.ExtraPaddle).get(0);
            brickerGameManager.incrementCounter(CounterKinds.ExtraPaddleCounter);
            brickerGameManager.addGameObject(extraPaddle);
        }
    }
}
