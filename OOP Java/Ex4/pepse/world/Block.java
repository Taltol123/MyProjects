package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a block.
 *
 * @author Tal Yehezkel
 */
public class Block extends GameObject {
    /**
     * The block size.
     */
    public static final int SIZE = 30;

    /**
     * Create instance of a block.
     *
     * @param topLeftCorner - the block's top left corner.
     * @param renderable    - the renderable for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}