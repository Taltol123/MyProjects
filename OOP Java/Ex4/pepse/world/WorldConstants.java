package pepse.world;

import danogl.util.Vector2;

import java.awt.*;

/**
 * World constansts.
 */
public class WorldConstants {

    /**
     * Private ctor.
     */
    private WorldConstants() {
    }

    /**
     * Energy counter size.
     */
    public static final String ENERGY_COUNTER_SIGN = "%";
    /**
     * Base ground color.
     */
    public static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    /**
     * Seed for noise generator.
     */
    public static final int SEED_FOR_NOISE_GENERATOR = 0;
    /**
     * Noise factor.
     */
    public static final int NOISE_FACTOR = 7;
    /**
     * Starting window x.
     */
    public static final int STARTING_WINDOW_X = 0;

    /**
     * Ground block depth.
     */
    public static final int GROUND_BLOCK_DEPTH = 20;
    /**
     * Start height ground part.
     */
    public static final float START_HEIGHT_GROUND_PART = 2 / 3f;
    /**
     * Ground relative area.
     */
    public static final float GROUND_RELATIVE_AREA = 1 / 3f;
    /**
     * Day time.
     */
    public static final float DAY_TIME = 30;
    /**
     * Basic sky color.
     */
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    /**
     * Sky tag.
     */
    public static final String SKY_TAG = "sky";
    /**
     * Ground tag.
     */
    public static final String GROUND_TAG = "ground";
    /**
     * Energy tag.
     */
    public static final String ENERGY_TAG = "energy";
    /**
     * Energy counter top left.
     */
    public static final Vector2 ENERGY_COUNTER_TOP_LEFT = new Vector2(0, 10);
    /**
     * Energy counter dimension x.
     */
    public static final float ENERGY_COUNTER_DIMENSION_X = 60;
    /**
     * Energy counter dimension y.
     */
    public static final float ENERGY_COUNTER_DIMENSION_Y = 50;


    //Avatar constants :


    /**
     * Idle images path.
     */
    public static final String[] IDLE_IMAGES_PATH = new String[]{"assets/idle_0.png",
            "assets/idle_1.png", "assets/idle_2.png", "assets/idle_3.png"};
    /**
     * Jumping images path.
     */
    public static final String[] JUMP_IMAGES_PATH = new String[]{"assets/jump_0.png",
            "assets/jump_1.png", "assets/jump_2.png", "assets/jump_3.png"};
    /**
     * Running images path.
     */
    public static final String[] RUN_IMAGES_PATH = new String[]{"assets/run_0.png", "assets/run_1.png",
            "assets/run_2.png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
    /**
     * Avatar time between clips.
     */
    public static final double AVATAR_TIME_BETWEEN_CLIPS = 0.2;
    /**
     * Energy for running right or left.
     */
    public static final double ENERGY_FOR_RUNNING_RIGHT_LEFT = 0.5;
    /**
     * Energy for jumping.
     */
    public static final double ENERGY_FOR_JUMPING = 10;
    /**
     * Added energy for staying.
     */
    public static final double ADDED_ENERGY_FOR_STAYING = 1;
    /**
     * Energy added for fruit eating.
     */
    public static final double ADDED_ENERGY_FOR_FRUIT_EATING = 10;
    /**
     * Avatar velocity x.
     */
    public static final float AVATAR_VELOCITY_X = 400;
    /**
     * Avatar velocity y.
     */
    public static final float AVATAR_VELOCITY_Y = -650;
    /**
     * INITIAL ENERGY.
     */
    public static final int INITIAL_ENERGY = 100;
    /**
     * Maximum energy.
     */
    public static final int MAXIMUM_ENERGY = 100;
    /**
     * Gravity.
     */
    public static final float GRAVITY = 600;
    /**
     * Avatar tag.
     */
    public static final String AVATAR_TAG = "avatar";
    /**
     * Avatar dimension.
     */
    public static final float AVATAR_DIMENSION = 50;

    // Tree constants :

    /**
     * Leaf transition cycle length.
     */
    public static final float LEAF_TRANSITIONS_CYCLE_LENGTH = 0.4f;
    /**
     * Leaf angle transition ending value.
     */
    public static final Float LEAF_ANGLE_TRANSITION_ENDING_VALUE = 2.0f;
    /**
     * Leaf angle transition starting value.
     */
    public static final Float LEAF_ANGLE_TRANSITION_STARTING_VALUE = -2.0f;
    /**
     * Leaf size transition starting value.
     */
    public static final Float LEAF_SIZE_TRANSITION_STARTING_VALUE = 1.0f;
    /**
     * Lleaf size transition ending value.
     */
    public static final Float LEAF_SIZE_TRANSITION_ENDING_VALUE = 0.95f;
    /**
     * Fruit part of leaf width.
     */
    public static final float FRUIT_PART_OF_LEAF_WIDTH = 0.6f;
    /**
     * Fruit part of leaf height.
     */
    public static final float FRUIT_PART_OF_LEAF_HEIGHT = 0.6f;
    /**
     * Trunk width.
     */
    public static final int TRUNK_WIDTH = Block.SIZE;
    /**
     * Trunk height.
     */
    public static final int TRUNK_HEIGHT = 200;
    /**
     * Probability for create tree.
     */
    public static final double PROBABILITY_FOR_CREATE_TREE = 0.1f;
    /**
     * Num of leafs in tree.
     */
    public static final int NUM_OF_LEAFS_IN_TREE = 30;
    /**
     * Number of fruits in tree.
     */
    public static final int NUM_OF_FRUITS_IN_TREE = 10;
    /**
     * Minimum diff between trees.
     */
    public static final int MIN_DIFF_BETWEEN_TREES = 10;
    /**
     * Fruit initial color.
     */
    public static final Color FRUIT_INITIAL_COLOR = new Color(150, 0, 0);
    /**
     * Leaf color.
     */
    public static final Color LEAF_COLOR = new Color(50, 200, 30);
    /**
     * Trunk color.
     */
    public static final Color TRUNK_COLOR = new Color(100, 50, 20);
    /**
     * Fruit color when avatar jumping.
     */
    public static final Color FRUIT_COLOR_WHEN_AVATAR_JUMPING = new Color(120, 110, 180);
    /**
     * Fruit dimension.
     */
    public static final Vector2 FRUIT_DIMENSION =
            new Vector2(Block.SIZE * WorldConstants.FRUIT_PART_OF_LEAF_WIDTH,
                    Block.SIZE * WorldConstants.FRUIT_PART_OF_LEAF_HEIGHT);

    /**
     * Fruit tag.
     */
    public static final String FRUIT_TAG = "fruit";
    /**
     * Trunk tag.
     */
    public static final String TRUNK_TAG = "trunk";
    /**
     * Leaf tag.
     */
    public static final String LEAF_TAG = "leaf";
    /**
     * Starting leaf wait time.
     */
    public static final double STARTING_LEAF_WAIT_TIME = 0.1;
    /**
     * Factor for random leaf extra wait time.
     */
    public static final double FACTOR_FOR_RANDOM_LEAF_EXTRA_WAIT_TIME = 3.9;
    /**
     * Fruit initial place in leaf.
     */
    public static final float FRUIT_INITIAL_PLACE_IN_LEAF = 2f;
    /**
     * Part of trunk starting leafs.
     */
    public static final float PART_OF_TRUNK_STARTING_LEAFS = 1 / 3f;
    /**
     * Extra place to leafs.
     */
    public static final float EXTRA_PLACE_TO_LEAFS = 60;
    /**
     * Leaf initial angle.
     */
    public static final Float LEAF_INITIAL_ANGLE = 0f;
    /**
     * Leaf turning angle.
     */
    public static final Float LEAF_TURNING_ANGLE = 90f;
    /**
     * Leaf turning time.
     */
    public static final float LEAF_TURNING_TIME = 0.7f;

    // Day Night constants :
    /**
     * Midnight opacity.
     */
    public static final float MIDNIGHT_OPACITY = 0.5f;
    /**
     * Halo color.
     */
    public final static Color HALO_COLOR = new Color(255, 255, 0, 20);
    /**
     * Part of day night transition.
     */
    public static final float PART_OF_DAY_NIGHT_TRANSITION = 0.5f;

    /**
     * Sun size.
     */
    public final static float SUN_SIZE = 80;
    /**
     * Sun tag.
     */
    public final static String SUN_TAG = "sun";
    /**
     * Initial sun transition.
     */
    public static final float INITIAL_SUN_TRANSITION = 0f;
    /**
     * End sun transition.
     */
    public static final float END_SUN_TRANSITION = 360f;
    /**
     * Initial night opacity.
     */
    public static final float INITIAL_NIGHT_OPACITY = 0f;
    /**
     * Night tag.
     */
    public static final String NIGHT_TAG = "night";
    /**
     * Sub halo tag.
     */
    public static final String SUN_HALO_TAG = "halo";
    /**
     * Halo size relative to sun.
     */
    public static final float HALO_SIZE_RELATIVE_TO_SUN = 60;

}
