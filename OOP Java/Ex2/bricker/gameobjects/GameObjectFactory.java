package bricker.gameobjects;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.brick_strategies.CollisionStrategy;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;
import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents the factory for the game object.
 * author Tal Yehezkel
 */
public class GameObjectFactory {
    private final ImageReader imageReader;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final BrickerGameManager brickerGameManager;
    private final SoundReader soundReader;
    private final int numBricksRows;
    private final int numBrickCols;
    private Map<GameObjectTypes, Function<Void, List<GameObject>>> gameObjectCreationMap;
    private CounterGameObject ball;


    /**
     * @param imageReader        The image reader.
     * @param inputListener      Get User's press on the keyboard.
     * @param brickerGameManager The manager of the game.
     * @param soundReader        The sound reader.
     */
    public GameObjectFactory(ImageReader imageReader, UserInputListener inputListener,
                             BrickerGameManager brickerGameManager, SoundReader soundReader,
                             int numBricksRows, int numBrickCols) {
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = brickerGameManager.getWindowDimension();
        this.brickerGameManager = brickerGameManager;
        this.soundReader = soundReader;
        this.numBricksRows = numBricksRows;
        this.numBrickCols = numBrickCols;
        initializeGameObjectCreationMapMapping();
    }

    /**
     * Initialize the mapping between the game objects names to their creating functions.
     */
    private void initializeGameObjectCreationMapMapping() {
        gameObjectCreationMap = new HashMap<>();
        gameObjectCreationMap.put(GameObjectTypes.Ball, v -> createBall());
        gameObjectCreationMap.put(GameObjectTypes.Background, v -> createBackground());
        gameObjectCreationMap.put(GameObjectTypes.Paddle, v -> createPaddle());
        gameObjectCreationMap.put(GameObjectTypes.ExtraPaddle, v -> createExtraPaddle());
        gameObjectCreationMap.put(GameObjectTypes.Bricks, v -> createBricks());
        gameObjectCreationMap.put(GameObjectTypes.Walls, v -> createWalls());
        gameObjectCreationMap.put(GameObjectTypes.GraphicLifeCounter, v -> createGraphicLifeCounter());
        gameObjectCreationMap.put(GameObjectTypes.NumericLifeCounter, v -> createNumericLifeCounter());
        gameObjectCreationMap.put(GameObjectTypes.Heart, v -> createHeart());
        gameObjectCreationMap.put(GameObjectTypes.LifeHeart, v -> createLifeHeart());
        gameObjectCreationMap.put(GameObjectTypes.Puck, v -> createPuck());
        gameObjectCreationMap.put(GameObjectTypes.CameraFollower, v -> createCameraFollower());
    }


    /**
     * The function that creates the wanted game objects.
     *
     * @param wantedGameObjects The game object to create.
     * @return a listof the wanted game objects.
     */
    public List<GameObject> buildGameObjects(GameObjectTypes wantedGameObjects) {
        return gameObjectCreationMap.get(wantedGameObjects).apply(null);
    }

    /**
     * Create the game-object CameraFollower.
     *
     * @return The created camera follower object.
     */
    private List<GameObject> createCameraFollower() {
        List<GameObject> createdCameraFollower = new ArrayList<>();
        CameraFollower cameraFollower = new CameraFollower(Vector2.ZERO, Vector2.ZERO, null,
                brickerGameManager,
                ball, Constants.StrategiesConstants.MAX_BALL_COLLISION_CAMERA);
        createdCameraFollower.add(cameraFollower);
        return createdCameraFollower;
    }

    /**
     * @return The dimensions of the puck.
     */
    private Vector2 getPuckDimensions() {
        Vector2 originBallDimension = new Vector2(Constants.GameObjectsConstants.BALL_DIM_X,
                Constants.GameObjectsConstants.BALL_DIM_Y);
        float puckDimensionX = (float) (originBallDimension.x() *
                Constants.GameObjectsConstants.PUCK_PART_OF_THE_BALL_SIZE);
        float puckDimensionY = (float) (originBallDimension.y() *
                Constants.GameObjectsConstants.PUCK_PART_OF_THE_BALL_SIZE);
        return new Vector2(puckDimensionX, puckDimensionY);
    }


    /**
     * Create the game object - puck.
     *
     * @return the created gameobjects.
     */
    private List<GameObject> createPuck() {
        List<GameObject> createdPuck = new ArrayList<>();
        Renderable puckImage = this.imageReader.readImage(Constants.PathConstants.PUCK_PATH,
                true);
        Sound puckSound = soundReader.readSound(Constants.PathConstants.PUCK_COLLISION_SOUND_PATH);
        Vector2 puckDimension = getPuckDimensions();
        GameObject puck = new Puck(Vector2.ZERO, puckDimension, puckImage, puckSound, brickerGameManager);
        createdPuck.add(puck);
        return createdPuck;
    }

    /**
     * @return The extra-paddle top left corner.
     */
    private Vector2 getExtraPaddleTopLeftCorner(float extraPaddleDimX) {
        float extraPaddleTopLeftCornerX = (windowDimensions.x() - extraPaddleDimX) *
                Constants.GameObjectsConstants.EXTRA_PADDLE_PLACE_IN_WINDOW_X;
        float extraPaddleTopLeftCornerY =
                this.windowDimensions.y() * Constants.GameObjectsConstants.EXTRA_PADDLE_PLACE_IN_WINDOW_Y;
        return new Vector2(extraPaddleTopLeftCornerX, extraPaddleTopLeftCornerY);
    }

    /**
     * Create the game object - paddle.
     *
     * @return the created game objects.
     */
    private List<GameObject> createExtraPaddle() {
        List<GameObject> createdExtraPaddle = new ArrayList<>();
        Vector2 extraPaddleDim = new Vector2(Constants.GameObjectsConstants.PADDLE_DIM_X,
                Constants.GameObjectsConstants.PADDLE_DIM_Y);
        Renderable paddleImage = imageReader.readImage(
                Constants.PathConstants.EXTRA_PADDLE_PATH, true);
        Vector2 extraPaddleTopLeftCorner = getExtraPaddleTopLeftCorner(extraPaddleDim.x());
        GameObject extraPaddle = new ExtraPaddle(extraPaddleTopLeftCorner, extraPaddleDim, paddleImage,
                inputListener, brickerGameManager);
        createdExtraPaddle.add(extraPaddle);
        return createdExtraPaddle;
    }

    /**
     * Create the game object - heart.
     *
     * @return the created game objects.
     */
    private List<GameObject> createHeart() {
        List<GameObject> createdHeart = new ArrayList<>();
        Renderable heartImage = imageReader.readImage(
                Constants.PathConstants.HEART_PATH, true);
        Vector2 heartDimension = new Vector2(
                Constants.GameObjectsConstants.HEART_DIM_X, Constants.GameObjectsConstants.HEART_DIM_Y);
        GameObject heart = new Heart(Vector2.ZERO, heartDimension, heartImage, brickerGameManager);
        createdHeart.add(heart);
        return createdHeart;
    }


    /**
     * Create the game object - life heart.
     *
     * @return the created game objects.
     */
    private List<GameObject> createLifeHeart() {
        List<GameObject> createdHeart = new ArrayList<>();
        Renderable heartImage = imageReader.readImage(
                Constants.PathConstants.HEART_PATH, true);
        Vector2 heartDimension = new Vector2(
                Constants.GameObjectsConstants.HEART_DIM_X, Constants.GameObjectsConstants.HEART_DIM_Y);
        GameObject heart = new LifeHeart(Vector2.ZERO, heartDimension, heartImage);
        createdHeart.add(heart);
        return createdHeart;
    }


    /**
     * Create the game object - ball.
     *
     * @return the created game objects.
     */
    private List<GameObject> createBall() {
        List<GameObject> ballCreated = new ArrayList<>();
        Sound ballSound = soundReader.readSound(Constants.PathConstants.COLLISION_SOUND);
        Renderable ballImage = this.imageReader.readImage(
                Constants.PathConstants.BALL_PATH, true);
        Vector2 originBallDimension = new Vector2(
                Constants.GameObjectsConstants.BALL_DIM_X, Constants.GameObjectsConstants.BALL_DIM_Y);

        ball = new Ball(Vector2.ONES, originBallDimension, ballImage, ballSound, brickerGameManager);
        ballCreated.add(ball);
        return ballCreated;
    }

    /**
     * Create the game object - background.
     *
     * @return the created game objects.
     */
    private List<GameObject> createBackground() {
        List<GameObject> backgroundCreated = new ArrayList<>();
        Renderable backgroundRenderable = imageReader.readImage(
                Constants.PathConstants.BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundRenderable);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        backgroundCreated.add(background);
        return backgroundCreated;
    }

    /**
     * Compute the paddle's initial place/
     */
    private Vector2 initializeOriginPaddleTopLeftCorner() {
        float paddleTopLeftCornerX = (this.windowDimensions.x() -
                Constants.GameObjectsConstants.PADDLE_DIM_X) *
                Constants.GameObjectsConstants.PADDLE_PLACE_IN_WINDOW_X;
        float paddleTopLeftCornerY = this.windowDimensions.y() -
                (Constants.GameObjectsConstants.PADDLE_DIM_Y);
        return (new Vector2(paddleTopLeftCornerX, paddleTopLeftCornerY));
    }

    /**
     * Create the game object - paddle.
     *
     * @return the created game objects.
     */
    private List<GameObject> createPaddle() {
        List<GameObject> paddleCreated = new ArrayList<>();
        Renderable paddleImage = imageReader.readImage(
                Constants.PathConstants.PADDLE_PATH, true);
        Vector2 paddleDimensions = new Vector2(
                Constants.GameObjectsConstants.PADDLE_DIM_X, Constants.GameObjectsConstants.PADDLE_DIM_Y);
        Vector2 originPaddleTopLeftCorner = initializeOriginPaddleTopLeftCorner();
        Paddle paddle = new Paddle(originPaddleTopLeftCorner, paddleDimensions, paddleImage,
                inputListener, brickerGameManager);
        paddleCreated.add(paddle);
        return paddleCreated;
    }

    /**
     * Create the game objects - bricks.
     *
     * @return the created game objects.
     */
    private List<GameObject> createBricks() {
        List<GameObject> brickCreated = new ArrayList<>();
        BrickStrategyFactory brickStrategyFactory = createBrickStrategyFactory();
        Renderable brickImage = imageReader.readImage(
                Constants.PathConstants.BRICK_PATH, true);
        int brickWidth = getBrickWidth();

        for (int row = 0; row < numBricksRows; row++) {
            for (int col = 0; col < numBrickCols; col++) {
                GameObject brick = createBrick(brickStrategyFactory, col, row, brickWidth, brickImage);
                brickCreated.add(brick);
            }
        }
        return brickCreated;
    }

    /**
     * Create a single brick.
     *
     * @param brickStrategyFactory The cricksstrategt factory.
     * @param col                  The col to create brick in.
     * @param row                  The row to create brick in.
     * @param brickWidth           The width of the brick.
     * @param brickImage           The image of the brick.
     * @return
     */
    private GameObject createBrick(BrickStrategyFactory brickStrategyFactory, int col, int row,
                                   int brickWidth, Renderable brickImage) {
        CollisionStrategy brickStrategy = brickStrategyFactory.getStrategy();
        float x =
                (col * (brickWidth + Constants.GameObjectsConstants.SPACE_BETWEEN_BRICKS)) +
                        Constants.GameObjectsConstants.WALL_WIDTH +
                        Constants.GameObjectsConstants.SPACE_BETWEEN_BRICKS;
        float y = (row * (Constants.GameObjectsConstants.BRICK_HEIGHT +
                Constants.GameObjectsConstants.SPACE_BETWEEN_BRICKS)) +
                Constants.GameObjectsConstants.BRICK_HEIGHT + Constants.GameObjectsConstants.WALL_WIDTH;
        Vector2 brickTopLeftCorner = new Vector2(x, y);
        Vector2 brickDimension = new Vector2(brickWidth, Constants.GameObjectsConstants.BRICK_HEIGHT);
        return new Brick(brickTopLeftCorner, brickDimension, brickImage, brickStrategy);
    }

    /**
     * Calculate brick width.
     *
     * @return the width.
     */
    private int getBrickWidth() {
        int spaceSize = Constants.GameObjectsConstants.SPACE_BETWEEN_BRICKS * numBrickCols;
        int sideWallsSize = Constants.GameObjectsConstants.WALL_WIDTH +
                Constants.GameObjectsConstants.WALL_WIDTH;
        int windowSize = (int) this.windowDimensions.x();
        int sumBricksLength = windowSize - (spaceSize + sideWallsSize);
        return sumBricksLength / numBrickCols;
    }


    /**
     * Create the bricks strategy factory.
     *
     * @return the created factory.
     */
    private BrickStrategyFactory createBrickStrategyFactory() {
        return new BrickStrategyFactory(brickerGameManager, this);
    }

    /**
     * Created the game object - walls.
     *
     * @return the created game objects.
     */
    private List<GameObject> createWalls() {
        List<GameObject> wallsCreated = new ArrayList<>();
        GameObject leftWall = createLeftWall();
        wallsCreated.add(leftWall);

        GameObject rightWall = createRightWall();
        wallsCreated.add(rightWall);

        GameObject topWall = createTopWall();
        wallsCreated.add(topWall);

        return wallsCreated;
    }

    /**
     * Create the left wall.
     *
     * @return The created wall.
     */
    private GameObject createLeftWall() {
        Vector2 leftWallDimension = new Vector2(Constants.GameObjectsConstants.WALL_WIDTH,
                windowDimensions.y());
        Renderable renderable = new RectangleRenderable(Color.ORANGE);
        GameObject leftWall = new GameObject(Vector2.ZERO, leftWallDimension, renderable);
        leftWall.setTag(Constants.TagConstants.LEFT_WALL_TAG);
        return leftWall;
    }

    /**
     * Create the right wall.
     *
     * @return The created wall.
     */
    private GameObject createRightWall() {
        Vector2 topLeftCorner = new Vector2(
                windowDimensions.x() - Constants.GameObjectsConstants.WALL_WIDTH,
                Constants.GameObjectsConstants.RIGHT_WALL_TOP_LEFT_Y);
        Vector2 dimension = new Vector2(Constants.GameObjectsConstants.WALL_WIDTH, windowDimensions.y());
        Renderable renderable = new RectangleRenderable(Color.ORANGE);
        GameObject rightWall = new GameObject(topLeftCorner, dimension, renderable);
        rightWall.setTag(Constants.TagConstants.RIGHT_WALL_TAG);
        return rightWall;
    }

    /**
     * Create the top wall.
     *
     * @return The created wall.
     */
    private GameObject createTopWall() {
        Vector2 dimension = new Vector2(windowDimensions.x(), Constants.GameObjectsConstants.WALL_WIDTH);
        Renderable renderable = new RectangleRenderable(Color.ORANGE);
        GameObject topWall = new GameObject(Vector2.ZERO, dimension, renderable);
        topWall.setTag(Constants.TagConstants.TOP_WALL_TAG);
        return topWall;
    }

    /**
     * @return The heart that represents the lives top left corner.
     */
    private Vector2 getHeartLifeCounterTopLeftCounter() {
        float heartTopLeftCornerX = Constants.GameObjectsConstants.WALL_WIDTH;
        float heartTopLeftCornerY =
                windowDimensions.y() - (Constants.GameObjectsConstants.HEART_DIM_Y +
                        Constants.GameObjectsConstants.PADDLE_DIM_Y);
        return new Vector2(heartTopLeftCornerX, heartTopLeftCornerY);
    }

    /**
     * Created the game object - graphic life counter.
     *
     * @return the created game objects.
     */
    private List<GameObject> createGraphicLifeCounter() {
        List<GameObject> graphicLifeCounterCreated = new ArrayList<>();
        Vector2 topLeftCorner = getHeartLifeCounterTopLeftCounter();
        Vector2 dimension = new Vector2(Constants.GameObjectsConstants.HEART_DIM_X,
                Constants.GameObjectsConstants.HEART_DIM_Y);
        GameObject graphicLifeCounter = new GraphicLifeCounter(topLeftCorner, dimension,
                brickerGameManager, this);
        graphicLifeCounterCreated.add(graphicLifeCounter);
        return graphicLifeCounterCreated;
    }

    /**
     * @return The numeric-life-counter top left corner.
     */
    private Vector2 getNumericLifeCounterTopLeftCorner() {
        float counterTopLeftCornerY = windowDimensions.y() -
                (Constants.GameObjectsConstants.GRAPHIC_COUNTER_DIM_Y +
                        Constants.GameObjectsConstants.NUMERIC_COUNTER_DIM_X +
                        Constants.GameObjectsConstants.SPACE_NUMERIC_GRAPHIC +
                        Constants.GameObjectsConstants.PADDLE_DIM_Y);
        Vector2 topLeftCorner =
                new Vector2(Constants.GameObjectsConstants.WALL_WIDTH +
                        Constants.GameObjectsConstants.NUMERIC_COUNTER_TOP_LEFT_CORNER_X
                        , counterTopLeftCornerY);
        return topLeftCorner;
    }

    /**
     * Created the game object - numeric life counter.
     *
     * @return the created game objects.
     */
    private List<GameObject> createNumericLifeCounter() {
        List<GameObject> numericLifeCounterCreated = new ArrayList<>();
        Vector2 topLeftCorner = getNumericLifeCounterTopLeftCorner();
        Vector2 dimension = new Vector2(Constants.GameObjectsConstants.NUMERIC_COUNTER_DIM_X,
                Constants.GameObjectsConstants.GRAPHIC_COUNTER_DIM_Y);
        TextRenderable renderable = new TextRenderable(String.valueOf(Constants.GameConstants.INITIAL_LIVES));
        GameObject numericLifeCounter = new NumericLifeCounter(brickerGameManager,
                topLeftCorner, dimension, renderable);
        numericLifeCounterCreated.add(numericLifeCounter);
        return numericLifeCounterCreated;
    }
}