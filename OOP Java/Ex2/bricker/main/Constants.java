package bricker.main;
/**
 * I did default ctor in the static classes because the submmision didnt passed without it.
 */

/**
 * Contains all the constants in the project.
 * author Tal Yehezkel
 */
public class Constants {

    /**
     * Constructs default constructor.
     */
    public Constants() {
    }

    /**
     * The game constants.
     */
    public static class GameConstants {
        /**
         * Constructs default constructor.
         */
        public GameConstants() {
        }

        /**
         * The initial lives.
         */
        public static final int INITIAL_LIVES = 3;
        /**
         * The maximum life number.
         */
        public static final int MAX_LIFE_NUM = 4;
        /**
         * The title of the game.
         */
        public static final String WINDOW_TITLE = "Bricker";
        /**
         * The losing message.
         */
        public static final String LOSE_MSG = "You Lose! Play again?";
        /**
         * The winning message.
         */
        public static final String WIN_MSG = "You Win! Play again?";
        /**
         * The window's x dimension.
         */
        public static final float WINDOW_DIM_X = 1000;
        /**
         * The window's y dimension.
         */
        public static final float WINDOW_DIM_Y = 600;
        /**
         * The time scale  for the window-controller.
         */
        public static final float TIME_SCALE = 0.4F;
        /**
         * The default number of rows
         */
        public static final int BRICKS_ROWS = 7;
        /**
         * The default number of cols.
         */
        public static final int BRICKS_COLS = 8;
        /**
         * The minimal y value of the winsow.
         */
        public static final double MINIMAL_WINDOW_Y = 0;
        /**
         * The initial number of extra paddles.
         */
        public static final int INITIAL_EXTRA_PADDLE_NUM = 0;
        /**
         * The number og arguments expected beside the default -zer0.
         */
        public static final int NUM_ALLOWED_ARGS = 2;

    }

    /**
     * The game objects constants.
     */
    public static class GameObjectsConstants {
        /**
         * Constructs default constructor.
         */
        public GameObjectsConstants() {
        }

        /**
         * Extra paddle max appearances.
         */
        public static final int EXTRA_PADDLE_MAX_APPEARANCES = 1;
        /**
         * Space between bricks.
         */
        public static final int SPACE_BETWEEN_BRICKS = 10;
        /**
         * The wall wisth.
         */
        public static final int WALL_WIDTH = 15;
        /**
         * The camera factor.
         */
        public static final float CAMERA_FACTOR = 1.2F;
        /**
         * The brick height.
         */
        public static final float BRICK_HEIGHT = 15;
        /**
         * Ball X dimension.
         */
        public static final float BALL_DIM_X = 20;
        /**
         * Vall y dimension.
         */
        public static final float BALL_DIM_Y = 20;
        /**
         * Paddle x dimension.
         */
        public static final float PADDLE_DIM_X = 270;
        /**
         * Paddle y dimension.
         */
        public static final float PADDLE_DIM_Y = 30;
        /**
         * Paddle minimum distance from edge.
         */
        public static final int PADDLE_MIN_DIST_FROM_EDGE = 10;
        /**
         * Numeric life counter dimension x.
         */
        public static final float NUMERIC_COUNTER_DIM_X = 20;
        /**
         * Graphic counter dimension y.
         */
        public static final float GRAPHIC_COUNTER_DIM_Y = 20;
        /**
         * The numeric life counter tip left corner x.
         */
        public static final float NUMERIC_COUNTER_TOP_LEFT_CORNER_X = 15;
        /**
         * The heart x dimensiom.
         */
        public static final float HEART_DIM_X = 20;
        /**
         * The heart y dimension.
         */
        public static final float HEART_DIM_Y = 20;
        /**
         * The part of the origin ball that is the size og the puck.
         */
        public static final double PUCK_PART_OF_THE_BALL_SIZE = 0.75;
        /**
         * Ball x velocity.
         */
        public static final int BALL_VELOCITY_X = 490;
        /**
         * Ball y velocity.
         */
        public static final int BALL_VELOCITY_Y = 490;
        /**
         * The center factor.
         */
        public static final float CENTER_FACTOR = 0.5F;
        /**
         * The paddle movement speed.
         */
        public static final float PADDLE_MOVEMENT_SPEED = 700;
        /**
         * The space between the graphic counter to the numeric counter.
         */
        public static final float SPACE_NUMERIC_GRAPHIC = 7;
        /**
         * The heart y velocity.
         */
        public static final float HEART_VELOCITY_Y = 100;
        /**
         * The heart x velocity.
         */
        public static final float HEART_VELOCITY_X = 0;
        /**
         * The righ wall top left corner y.
         */
        public static final float RIGHT_WALL_TOP_LEFT_Y = 0;

        /**
         * The place from 0 to 1 the heart placed relative to the crick.
         */
        public static final float HEART_PLACE_IN_BRICK = 0.5F;
        /**
         * The extra paddle place from 0 to 1 related to the window x.
         */
        public static final float EXTRA_PADDLE_PLACE_IN_WINDOW_X = 0.5F;
        /**
         * The extra paddle place from 0 to 1 related to the window y.
         */
        public static final float EXTRA_PADDLE_PLACE_IN_WINDOW_Y = 0.5F;
        /**
         * The paddle place from 0 to 1 related to the window x.
         */
        public static final float PADDLE_PLACE_IN_WINDOW_X = 0.5F;

    }

    /**
     * The path constants.
     */
    public static class PathConstants {
        /**
         * Constructs default constructor.
         */
        public PathConstants() {
        }

        /**
         * The path for the collision sound.
         */
        public static final String COLLISION_SOUND = "assets/blop_cut_silenced.wav";
        /**
         * The path for the paddle picture.
         */
        public static final String PADDLE_PATH = "assets/paddle.png";
        /**
         * The path for the ball picture.
         */
        public static final String BALL_PATH = "assets/ball.png";
        /**
         * The path for the heart picture.
         */
        public static final String HEART_PATH = "assets/heart.png";
        /**
         * The path for the brick picture.
         */
        public static final String BRICK_PATH = "assets/brick.png";
        /**
         * The path for the background picture.
         */
        public static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
        /**
         * The path for the extra paddle picture.
         */
        public static final String EXTRA_PADDLE_PATH = "assets/paddle.png";
        /**
         * The path for the puck collision sound.
         */
        public static final String PUCK_COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
        /**
         * The path for the puck picture.
         */
        public static final String PUCK_PATH = "assets/mockBall.png";

    }

    /**
     * The strategies constants.
     */
    public static class StrategiesConstants {
        /**
         * Constructs default constructor.
         */
        public StrategiesConstants() {
        }

        /**
         * The number of strategies.
         */
        public static final int STRATEGIES_NUM = 5;
        /**
         * The maximum time ball can collided with another objects until the camera stop following after.
         */
        public static final int MAX_BALL_COLLISION_CAMERA = 4;
        /**
         * Maximum strategies double bejavior can have.
         */
        public static final int MAX_STRATEGIES_FOR_DOUBLE_BEHAVIOR = 3;
        /**
         * Number of pucks can appear when collided a brick with MoreBallStrategy..
         */
        public static final int NUM_PUCKS = 2;
        /**
         * The index for the camera strategy.
         */
        public static final int CAMERA_STRATEGY_INDEX = 0;
        /**
         * The index for disqualification strategy.
         */
        public static final int DISQUALIFICATION_STRATEGY_INDEX = 1;
        /**
         * The index for double behavior strategy.
         */
        public static final int DOUBLE_STRATEGY_INDEX = 2;
        /**
         * The index for extra paddle strategy.
         */
        public static final int EXTRA_PADDLE_STRATEGY_INDEX = 3;
        /**
         * The index for ,ore ball strategy.
         */
        public static final int MORE_BALL_STRATEGY_INDEX = 4;
        /**
         * The diff between the allowed double behavior to the maximum strategies for double behavior.
         */
        public static final int DOUBLE_DIFF = 1;
        /**
         * Number of special strategies for the double behavior.
         */
        public static final int NUM_OF_SPECIAL_STRATEGIES = 2;
        /**
         * The number of allowed collided with the extra-paddle until it disappear.
         */
        public static final int EXTRA_PADDLE_MAX_COLLISION = 4;

    }

    /**
     * The tags constants.
     */
    public static class TagConstants {
        /**
         * Constructs default constructor.
         */
        public TagConstants() {
        }

        /**
         * Puck tag.
         */
        public static final String PUCK_TAG = "Puck";
        /**
         * Ball tag.
         */
        public static final String BALL_TAG = "Ball";
        /**
         * Paddle tag.
         */
        public static final String PADDLE_TAG = "Paddle";
        /**
         * Brick tag.
         */
        public static final String BRICK_TAG = "Brick";
        /**
         * Top-wall tag.
         */
        public static final String TOP_WALL_TAG = "Top Wall";
        /**
         * left-wall tag.
         */
        public static final String LEFT_WALL_TAG = "Left Wall";
        /**
         * right-wall tag.
         */
        public static final String RIGHT_WALL_TAG = "Right Wall";
        /**
         * Heart tag.
         */
        public static final String HEART_TAG = "Heart";
        /**
         * graphic life tag.
         */
        public static final String GRAPHIC_LIFE_TAG = "Graphic life";
        /**
         * numeric life tag.
         */
        public static final String NUMERIC_LIFE_TAG = "Numeric Life";
        /**
         * extra paddle tag.
         */
        public static final String EXTRA_PADDLE_TAG = "Extra Paddle";
    }
}
