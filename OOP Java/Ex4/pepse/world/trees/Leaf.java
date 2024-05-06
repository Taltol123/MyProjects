package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.WorldConstants;

/**
 * Represents the leaf.
 *
 * @author Tal Yehezkel
 */
public class Leaf extends Block {

    /**
     * Create instance of a leaf game object.
     *
     * @param topLeftCorner - the top left corner of the created leaf.
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner,
                new RectangleRenderable(ColorSupplier.approximateColor(
                        WorldConstants.LEAF_COLOR)));

        setTag(WorldConstants.LEAF_TAG);
        scheduleLeafsMovement();
    }

    /**
     * Schedule the leaf's movement.
     */
    private void scheduleLeafsMovement() {
        double waitTime =
                WorldConstants.STARTING_LEAF_WAIT_TIME +
                        (Math.random() *
                                WorldConstants.FACTOR_FOR_RANDOM_LEAF_EXTRA_WAIT_TIME);

        new ScheduledTask(
                this, (float) waitTime, false, this::moveLeafAngle);

        new ScheduledTask(
                this, (float) waitTime, false, this::moveLeafWidth);
    }

    /**
     * Operate leaf width changing.
     */
    private void moveLeafWidth() {
        new Transition<>(
                this,
                (factor) -> this.setDimensions(new Vector2(Block.SIZE, Block.SIZE).mult(factor)),
                WorldConstants.LEAF_SIZE_TRANSITION_STARTING_VALUE,
                WorldConstants.LEAF_SIZE_TRANSITION_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WorldConstants.LEAF_TRANSITIONS_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }


    /**
     * Operate leaf angle changing.
     */
    private void moveLeafAngle() {
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                WorldConstants.LEAF_ANGLE_TRANSITION_STARTING_VALUE,
                WorldConstants.LEAF_ANGLE_TRANSITION_ENDING_VALUE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WorldConstants.LEAF_TRANSITIONS_CYCLE_LENGTH,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Handle updation ofter the avatar jumped.
     */
    public void updateAfterAvatarJump() {
        turnLeafDegrees();
    }

    /**
     * Operate 90 degrees turning in the leafs.
     */
    private void turnLeafDegrees() {
        new Transition<>(
                this,
                renderer()::setRenderableAngle,
                WorldConstants.LEAF_INITIAL_ANGLE,
                WorldConstants.LEAF_TURNING_ANGLE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                WorldConstants.LEAF_TURNING_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                null);
    }
}
