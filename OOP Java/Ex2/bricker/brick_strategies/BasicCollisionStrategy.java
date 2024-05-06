package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Represents the basic collision strategy.
 * author Tal Yehezkel
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final BrickerGameManager brickerGameManager;

    /**
     * Construct a new instance of the basic collision strategy.
     *
     * @param brickerGameManager The manager of the game.
     */
    public BasicCollisionStrategy(BrickerGameManager brickerGameManager) {
        this.brickerGameManager = brickerGameManager;
    }

    /**
     * Handle the remove brick when collided/
     *
     * @param thisObj  the object that was collided (the brick).
     * @param otherObj hte object that collided with the brick (the ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if (brickerGameManager.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            brickerGameManager.decrementCounter(CounterKinds.BricksCounter);
        }
    }
}
