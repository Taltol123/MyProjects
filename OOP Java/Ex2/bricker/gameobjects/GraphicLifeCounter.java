package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import bricker.main.CounterKinds;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.ArrayList;

/**
 * Represents the graphic life counter object.
 * author Tal Yehezkel
 */
public class GraphicLifeCounter extends GameObject {
    private final Vector2 widgetDimensions;
    private final BrickerGameManager brickerGameManager;
    private final float hearsHeights;
    private final GameObjectFactory gameObjectCollectionFactory;
    private final ArrayList<GameObject> hearts;


    /**
     * Construct a new GameObject instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param heartDimensions     Width and height in window coordinates.
     * @param brickerGameManager  The manager of the game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 heartDimensions,
                              BrickerGameManager brickerGameManager,
                              GameObjectFactory gameObjectCollectionFactory) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.widgetDimensions = heartDimensions;
        this.brickerGameManager = brickerGameManager;
        this.hearsHeights = widgetTopLeftCorner.y();
        this.gameObjectCollectionFactory = gameObjectCollectionFactory;
        this.hearts = new ArrayList<>();
        setTag(Constants.TagConstants.GRAPHIC_LIFE_TAG);
    }


    /**
     * Update number of hearts as the life counter.
     *
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
        int currentLifeCount = brickerGameManager.getCounterValue(CounterKinds.LifeCounter);
        int prevLifeCount = hearts.size();
        if (currentLifeCount != prevLifeCount) {
            changeHearts(currentLifeCount, prevLifeCount);
        }
    }

    /**
     * Check if nneeded adding/deleting of hearts according to the current life counter.
     *
     * @param currentLifeCount The number of the current life.
     * @param prevLifeCount    The number of the previous life.
     */
    private void changeHearts(int currentLifeCount, int prevLifeCount) {
        if (currentLifeCount > prevLifeCount) {
            int numLifeToAdd = currentLifeCount - prevLifeCount;
            for (int i = 0; i < numLifeToAdd; i++) {
                addHeart();
            }
        } else {
            int numLifeToRemove = prevLifeCount - currentLifeCount;
            for (int i = 0; i < numLifeToRemove; i++) {
                removeHeart();
            }
        }
    }

    /**
     * Removing the last heart.
     */
    private void removeHeart() {
        GameObject lastHeart = hearts.get(hearts.size() - 1);
        brickerGameManager.removeGameObject(lastHeart, Layer.BACKGROUND);
        hearts.remove(hearts.size() - 1);
    }

    /**
     * Adding a single heart.
     */
    private void addHeart() {
        float x = hearts.size() * widgetDimensions.x() + widgetDimensions.x();
        Vector2 heartTopLeftCorner = new Vector2(x, this.hearsHeights);
        GameObject heart = gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.LifeHeart).get(0);
        heart.setTopLeftCorner(heartTopLeftCorner);
        hearts.add(heart);
        brickerGameManager.addGameObject(heart, Layer.BACKGROUND);
    }
}
