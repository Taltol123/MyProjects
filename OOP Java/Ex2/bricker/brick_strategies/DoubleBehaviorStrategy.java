package bricker.brick_strategies;

import danogl.GameObject;

import java.util.List;


/**
 * Represents the double behavior collision strategy.
 * author Tal Yehezkel
 */
public class DoubleBehaviorStrategy implements CollisionStrategy {
    private final List<CollisionStrategy> strategies;

    /**
     * Construct a double behavior collision strategy.
     *
     * @param strategies The strategies that happened after the brick collided with an object.
     */
    public DoubleBehaviorStrategy(List<CollisionStrategy> strategies) {
        this.strategies = strategies;
    }


    /**
     * Activate its strategies when brick collided.
     *
     * @param thisObj  The current object.
     * @param otherObj The other object collided with.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        for (CollisionStrategy strategy : strategies) {
            strategy.onCollision(thisObj, otherObj);
        }
    }
}
