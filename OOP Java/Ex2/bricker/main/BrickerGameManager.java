package bricker.main;

import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.List;


/**
 * Represents the manager of the game.
 * author Tal Yehezkel
 */
public class BrickerGameManager extends GameManager {
    private Counter bricksCounter;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Counter lifeCounter;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter extraPaddleCounter;
    private final int numBrickRows;
    private final int numBrickCols;

    /**
     * Construct the game manager with the default number of bricks in cols and rows.
     *
     * @param windowTitle      The game title.
     * @param windowDimensions The dimensions of the window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        numBrickCols = Constants.GameConstants.BRICKS_COLS;
        numBrickRows = Constants.GameConstants.BRICKS_ROWS;
    }


    /**
     * Construct the game manager.
     *
     * @param windowTitle      The game title.
     * @param windowDimensions The dimensions of the window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int numBrickRows,
                              int numBrickCols) {
        super(windowTitle, windowDimensions);
        this.numBrickRows = numBrickRows;
        this.numBrickCols = numBrickCols;
    }

    /**
     * Initialize the game.
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from
     *                         disk. See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether
     *                         a given key is currently pressed by the user or not. See its
     *                         documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.soundReader = soundReader;
        this.imageReader = imageReader;
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.lifeCounter = new Counter(Constants.GameConstants.INITIAL_LIVES);
        this.extraPaddleCounter = new Counter(0);

        windowController.setTimeScale(Constants.GameConstants.TIME_SCALE);
        initializeBricksCounter();
        creatingGameObjects();
    }

    /**
     * Initialize the bricks counter.
     */
    private void initializeBricksCounter() {
        int bricksNum =
                numBrickCols * numBrickRows;
        this.bricksCounter = new Counter(bricksNum);
    }

    /**
     * Creating the game objects.
     */
    private void creatingGameObjects() {
        GameObjectFactory gameObjectCollectionFactory = new GameObjectFactory(imageReader,
                inputListener, this, soundReader, numBrickRows, numBrickCols);
        createsSingleGameObjects(gameObjectCollectionFactory);
        GameObject background =
                gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.Background).get(0);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        List<GameObject> bricks = gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.Bricks);
        for (GameObject brick : bricks) {
            gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
        }
        List<GameObject> walls = gameObjectCollectionFactory.buildGameObjects(GameObjectTypes.Walls);
        for (GameObject wall : walls) {
            gameObjects().addGameObject(wall);
        }
    }

    /**
     * Create the single game objects that don't need a special layer.
     *
     * @param gameObjectCollectionFactory The game objects factory.
     */
    private void createsSingleGameObjects(GameObjectFactory gameObjectCollectionFactory) {
        for (GameObjectTypes gameObjectType : new GameObjectTypes[]{GameObjectTypes.Ball,
                GameObjectTypes.Paddle,
                GameObjectTypes.GraphicLifeCounter,
                GameObjectTypes.NumericLifeCounter}) {
            GameObject gameObject = gameObjectCollectionFactory.buildGameObjects(gameObjectType).get(0);
            gameObjects().addGameObject(gameObject);
        }
    }

    /**
     * Check if the game end and show a  msg accordingly.
     */
    private void handlingEndGame() {
        String msg = "";
        if (this.lifeCounter.value() == 0) {
            msg = Constants.GameConstants.LOSE_MSG;
        }
        if (this.bricksCounter.value() <= 0 || this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            msg = Constants.GameConstants.WIN_MSG;
        }
        if (!msg.isEmpty()) {
            if (windowController.openYesNoDialog(msg)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    /**
     * Check if the game end.
     *
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        handlingEndGame();
    }

    /**
     * Set camera to follow after certain game object.
     *
     * @param gameObject The game object to set camera follow after.
     */
    public void setCameraToFollow(GameObject gameObject) {
        if (camera() == null) {

            setCamera(
                    new Camera(
                            gameObject,
                            Vector2.ZERO,
                            this.windowController.getWindowDimensions().mult(
                                    Constants.GameObjectsConstants.CAMERA_FACTOR),
                            this.windowController.getWindowDimensions()
                    )
            );
        }
    }

    /**
     * Remove game object from the game object collection.
     *
     * @param gameObjectToRemove The game object to remove.
     */
    public boolean removeGameObject(GameObject gameObjectToRemove) {
        return gameObjects().removeGameObject(gameObjectToRemove);
    }

    /**
     * Remove game object from the game object collection of specific layer.
     *
     * @param gameObjectToRemove The game object to remove.
     * @param layerId            The layer to remove the object from.
     */
    public boolean removeGameObject(GameObject gameObjectToRemove, int layerId) {
        return gameObjects().removeGameObject(gameObjectToRemove, layerId);
    }

    /**
     * Add game object to the game object collection.
     *
     * @param gameObjectToAdd The game object to add.
     */
    public void addGameObject(GameObject gameObjectToAdd) {
        gameObjects().addGameObject(gameObjectToAdd);
    }

    /**
     * Add game object to the game object collection in specific layer.
     *
     * @param gameObjectToAdd The game object to add.
     * @param layerId         The layer to add to.
     */
    public void addGameObject(GameObject gameObjectToAdd, int layerId) {
        gameObjects().addGameObject(gameObjectToAdd, layerId);
    }

    /**
     * Decrement the counter.
     */
    public void decrementCounter(CounterKinds counterKind) {
        switch (counterKind) {
            case LifeCounter -> lifeCounter.decrement();
            case BricksCounter -> bricksCounter.decrement();
            case ExtraPaddleCounter -> extraPaddleCounter.decrement();
        }
    }

    /**
     * Increment the counter.
     */
    public void incrementCounter(CounterKinds counterKind) {
        switch (counterKind) {
            case LifeCounter -> lifeCounter.increment();
            case BricksCounter -> bricksCounter.increment();
            case ExtraPaddleCounter -> extraPaddleCounter.increment();
        }
    }

    /**
     * Increment the counter.
     */
    public int getCounterValue(CounterKinds counterKind) {
        switch (counterKind) {
            case LifeCounter -> {
                return lifeCounter.value();
            }
            case BricksCounter -> {
                return bricksCounter.value();
            }
            case ExtraPaddleCounter -> {
                return extraPaddleCounter.value();
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Set the counter value to the given value.
     *
     * @param value The value to set the  count to.
     */
    public void setCounterValue(CounterKinds counterKind, int value) {
        switch (counterKind) {
            case LifeCounter -> {
                lifeCounter.reset();
                lifeCounter.increaseBy(value);
            }
            case BricksCounter -> {
                bricksCounter.reset();
                bricksCounter.increaseBy(value);
            }
            case ExtraPaddleCounter -> {
                extraPaddleCounter.reset();
                extraPaddleCounter.increaseBy(value);
            }
        }
    }

    /**
     * @return The window dimension.
     */
    public Vector2 getWindowDimension() {
        return windowController.getWindowDimensions();
    }

    /**
     * Starting the program.
     *
     * @param args
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager;
        Vector2 windowDimension = new Vector2(Constants.GameConstants.WINDOW_DIM_X,
                Constants.GameConstants.WINDOW_DIM_Y);
        if (args.length == Constants.GameConstants.NUM_ALLOWED_ARGS) {
            int numBrickRows = Integer.parseInt(args[1]);
            int numBrickCols = Integer.parseInt(args[0]);
            brickerGameManager = new BrickerGameManager(Constants.GameConstants.WINDOW_TITLE,
                    windowDimension, numBrickRows, numBrickCols);
        } else {
            brickerGameManager = new BrickerGameManager(Constants.GameConstants.WINDOW_TITLE,
                    windowDimension);
        }
        brickerGameManager.run();
    }

}

