package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Represents the avatar.
 *
 * @author Tal Yehezkel
 */
public class Avatar extends GameObject {
    private final UserInputListener inputListener;
    private double currentEnergyCount;
    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;
    private final List<Runnable> jumpObservers;


    /**
     * Construct instance of avatar.
     *
     * @param pos           - the avatar's position.
     * @param inputListener - the input listener.
     * @param imageReader   - the image reader.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, Vector2.ONES.mult(WorldConstants.AVATAR_DIMENSION),
                imageReader.readImage(WorldConstants.IDLE_IMAGES_PATH[0], true));
        this.jumpObservers = new ArrayList<>();
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(WorldConstants.GRAVITY);
        this.inputListener = inputListener;
        this.currentEnergyCount = WorldConstants.INITIAL_ENERGY;
        setTag(WorldConstants.AVATAR_TAG);
        setAnimations(imageReader);
    }

    /**
     * Add observer.
     *
     * @param observer - the observer to add.
     */
    public void addJumpObserver(Runnable observer) {
        jumpObservers.add(observer);
    }


    /**
     * Remove observer.
     *
     * @param observer - the observer to remove.
     */
    public void removeObserver(Runnable observer) {
        jumpObservers.remove(observer);
    }

    /**
     * Initialize the optional animations.
     *
     * @param imageReader - the image reader.
     */
    private void setAnimations(ImageReader imageReader) {
        idleAnimation = new AnimationRenderable(WorldConstants.IDLE_IMAGES_PATH, imageReader,
                true, WorldConstants.AVATAR_TIME_BETWEEN_CLIPS);

        jumpAnimation = new AnimationRenderable(WorldConstants.JUMP_IMAGES_PATH, imageReader,
                true, WorldConstants.AVATAR_TIME_BETWEEN_CLIPS);

        runAnimation = new AnimationRenderable(WorldConstants.RUN_IMAGES_PATH, imageReader
                , true, WorldConstants.AVATAR_TIME_BETWEEN_CLIPS);
    }

    /**
     * @return the energy count.
     */
    public double getEnergyCount() {
        return currentEnergyCount;
    }

    /**
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && isRunningRightLeftAllowed()) {
            xVel = runRightLeft(xVel, (i, j) -> i - j, true);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && isRunningRightLeftAllowed()) {
            xVel = runRightLeft(xVel, Float::sum, false);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && isJumpingAllowed()) {
            jump();
        } else {
            stay();
        }
        transform().setVelocityX(xVel);
    }

    /**
     * Handling stay logic, when avatar not moving.
     */
    private void stay() {
        renderer().setRenderable(idleAnimation);
        if (isAllowedToGetMoreEnergy()) {
            currentEnergyCount += WorldConstants.ADDED_ENERGY_FOR_STAYING;
        }
    }

    /**
     * @return true if is allowed to get more energy and false otherwise.
     */
    private boolean isAllowedToGetMoreEnergy() {
        return currentEnergyCount < WorldConstants.MAXIMUM_ENERGY &
                getVelocity().x() == 0 & getVelocity().y() == 0;
    }

    /**
     * @return true if it is allowed to run right or left and false otherwise.
     */
    private boolean isRunningRightLeftAllowed() {
        return currentEnergyCount >= WorldConstants.ENERGY_FOR_RUNNING_RIGHT_LEFT;
    }

    /**
     * @return true if it is allowed to jump and false otherwise.
     */
    private boolean isJumpingAllowed() {
        return currentEnergyCount >= WorldConstants.ENERGY_FOR_JUMPING &
                getVelocity().y() == 0;
    }

    /**
     * Operate running.
     *
     * @param xVel                   - the x velocity.
     * @param operator               - the operation to do on xVel.
     * @param setFlippedHorizontally - is needed set flipped horizontally.
     * @return the new x velocity.
     */
    private float runRightLeft(float xVel, BinaryOperator<Float> operator, boolean setFlippedHorizontally) {
        xVel = operator.apply(xVel, WorldConstants.AVATAR_VELOCITY_X);
        renderer().setRenderable(runAnimation);
        renderer().setIsFlippedHorizontally(setFlippedHorizontally);
        currentEnergyCount -= WorldConstants.ENERGY_FOR_RUNNING_RIGHT_LEFT;
        return xVel;
    }

    /**
     * Operate jumping.
     */
    private void jump() {
        transform().setVelocityY(WorldConstants.AVATAR_VELOCITY_Y);
        renderer().setRenderable(jumpAnimation);
        currentEnergyCount -= WorldConstants.ENERGY_FOR_JUMPING;
        updateObservers();
    }

    /**
     * Update the avatar's jumping observers.
     */
    private void updateObservers() {
        for (Runnable observer : jumpObservers) {
            observer.run();
        }
    }

    /**
     * @param other     The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(WorldConstants.FRUIT_TAG)) {
            if (currentEnergyCount <= (WorldConstants.MAXIMUM_ENERGY -
                    WorldConstants.ADDED_ENERGY_FOR_FRUIT_EATING)) {
                currentEnergyCount += WorldConstants.ADDED_ENERGY_FOR_FRUIT_EATING;
            }
        }
    }
}
