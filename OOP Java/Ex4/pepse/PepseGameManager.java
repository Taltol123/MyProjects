package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.*;

import java.util.*;

/**
 * Represents the Pepse game manager.
 *
 * @author Tal Yehezkel
 */
public class PepseGameManager extends GameManager {
    private Vector2 windowsDimension;
    private Map<String, Integer> gameObjectLayerMapping;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Terrain terrain;
    private Vector2 avatarInitialPlace;
    private Avatar avatar;

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

        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowsDimension = windowController.getWindowDimensions();
        initializeGameObjectLayerMapping();
        initializeGameObjects();
    }


    /**
     * Initialize the objects of the game.
     */
    private void initializeGameObjects() {
        addSky();
        addGround();
        addNight();
        addSun();
        addAvatar();
        addTrees();
        addEnergyCounter();
    }

    /**
     * Initialize mapping between the game object's tags and their layers.
     */
    private void initializeGameObjectLayerMapping() {
        this.gameObjectLayerMapping = new HashMap<>();
        gameObjectLayerMapping.put(WorldConstants.SKY_TAG, Layer.BACKGROUND);
        gameObjectLayerMapping.put(WorldConstants.GROUND_TAG, Layer.STATIC_OBJECTS);
        gameObjectLayerMapping.put(WorldConstants.NIGHT_TAG, Layer.BACKGROUND);
        gameObjectLayerMapping.put(WorldConstants.SUN_TAG, Layer.BACKGROUND);
        gameObjectLayerMapping.put(WorldConstants.SUN_HALO_TAG, Layer.BACKGROUND);
        gameObjectLayerMapping.put(WorldConstants.AVATAR_TAG, Layer.DEFAULT);
        gameObjectLayerMapping.put(WorldConstants.TRUNK_TAG, Layer.STATIC_OBJECTS);
        gameObjectLayerMapping.put(WorldConstants.LEAF_TAG, Layer.BACKGROUND);
        gameObjectLayerMapping.put(WorldConstants.FRUIT_TAG, Layer.DEFAULT);
        gameObjectLayerMapping.put(WorldConstants.ENERGY_TAG, Layer.UI);
    }

    /**
     * Create the energy counter.
     */
    private void addEnergyCounter() {
        TextRenderable energyRenderable =
                new TextRenderable(String.valueOf(WorldConstants.INITIAL_ENERGY));
        GameObject energyCounter =
                new EnergyCounter(WorldConstants.ENERGY_COUNTER_TOP_LEFT,
                        new Vector2(WorldConstants.ENERGY_COUNTER_DIMENSION_X,
                                WorldConstants.ENERGY_COUNTER_DIMENSION_Y), energyRenderable,
                        avatar::getEnergyCount);
        gameObjects().addGameObject(energyCounter, gameObjectLayerMapping.get(
                WorldConstants.ENERGY_TAG));
    }

    /**
     * Create the trees.
     */
    private void addTrees() {
        Flora flora = new Flora(terrain::groundHeightAt, (int) avatarInitialPlace.x());
        List<Tree> trees = flora.createInRange(0, (int) windowsDimension.x());
        for (Tree tree : trees) {
            addSingleTree(tree);
        }
    }

    /**
     * Add single tree.
     *
     * @param tree - the tree to add.
     */
    private void addSingleTree(Tree tree) {
        addTreeTrunk(tree);
        addLeafsForTree(tree);
        addFruitsForTree(tree);
    }

    /**
     * Add leafs for tree.
     *
     * @param tree - the tree to add leafs for.
     */
    private void addLeafsForTree(Tree tree) {
        List<Leaf> leafs = tree.getLeafs();
        addGameObjects(leafs, gameObjectLayerMapping.get(WorldConstants.LEAF_TAG));
        for (Leaf leaf : leafs) {
            avatar.addJumpObserver(leaf::updateAfterAvatarJump);
        }
    }

    /**
     * Add fruits for tree.
     *
     * @param tree - the tree to add fruits for.
     */
    private void addFruitsForTree(Tree tree) {
        List<Fruit> fruits = tree.getFruits();
        addGameObjects(fruits, gameObjectLayerMapping.get(WorldConstants.FRUIT_TAG));
        for (Fruit fruit : fruits) {
            avatar.addJumpObserver(fruit::updateAfterAvatarJump);
        }
    }

    /**
     * Add tree-trunk.
     *
     * @param tree - the tree.
     */
    private void addTreeTrunk(Tree tree) {
        TreeTrunk trunk = tree.getTreeTrunk();
        List<GameObject> trunkBlocks = trunk.getTrunkBlocks();
        addGameObjects(trunkBlocks, gameObjectLayerMapping.get(WorldConstants.TRUNK_TAG));
        avatar.addJumpObserver(trunk::updateAfterAvatarJump);
    }


    /**
     * Create the avatar.
     */
    private void addAvatar() {
        this.avatarInitialPlace =
                new Vector2(windowsDimension.x() - WorldConstants.AVATAR_DIMENSION,
                        windowsDimension.y() * WorldConstants.START_HEIGHT_GROUND_PART);

        this.avatar = new Avatar(avatarInitialPlace, inputListener, imageReader);

        gameObjects().addGameObject(avatar, gameObjectLayerMapping.get(
                WorldConstants.AVATAR_TAG));
    }

    /**
     * Create the sun.
     */
    private void addSun() {
        GameObject sun = Sun.create(windowsDimension, WorldConstants.DAY_TIME);
        gameObjects().addGameObject(sun, gameObjectLayerMapping.get(
                WorldConstants.SUN_TAG));

        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, gameObjectLayerMapping.get(
                WorldConstants.SUN_HALO_TAG));
    }

    /**
     * Create the night.
     */
    private void addNight() {
        GameObject night = Night.create(windowsDimension, WorldConstants.DAY_TIME);
        gameObjects().addGameObject(night, gameObjectLayerMapping.get(
                WorldConstants.NIGHT_TAG));
    }

    /**
     * Create the ground.
     */
    private void addGround() {
        this.terrain = new Terrain(windowsDimension, WorldConstants.SEED_FOR_NOISE_GENERATOR);
        List<Block> blocks = terrain.createInRange(WorldConstants.STARTING_WINDOW_X,
                (int) windowsDimension.x());
        for (Block block : blocks) {
            gameObjects().addGameObject(block, gameObjectLayerMapping.
                    get(WorldConstants.GROUND_TAG));
        }
    }

    /**
     * Create the sky.
     */
    private void addSky() {
        GameObject sky = Sky.create(windowsDimension);
        gameObjects().addGameObject(sky,
                gameObjectLayerMapping.get(WorldConstants.SKY_TAG));
    }

    /**
     * Add game objects to the game objects collection.
     *
     * @param gameObjects - the game objects to add.
     * @param layer       - the layer to add to.
     */
    public void addGameObjects(List<? extends GameObject> gameObjects, int layer) {
        for (GameObject singleGameObject : gameObjects) {
            gameObjects().addGameObject(singleGameObject, layer);
        }
    }

    /**
     * The main program.
     *
     * @param args - program arguments.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }


}


