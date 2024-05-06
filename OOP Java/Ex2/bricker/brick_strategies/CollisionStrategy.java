package bricker.brick_strategies;

import danogl.GameObject;

/**
 * Collision strategy interface.
 */
public interface CollisionStrategy {
    /**
     * Handling the situation of objects collision.
     *
     * @param thisObj  The current object.
     * @param otherObj The other object collided with.
     */
    void onCollision(GameObject thisObj, GameObject otherObj);
}
