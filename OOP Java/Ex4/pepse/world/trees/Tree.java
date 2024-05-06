package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.WorldConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Represents the tree object.
 * author Tal Yehezkel
 */
public class Tree {
    private final float treeXPlace;
    private final Function<Float, Float> getGroundPlace;
    private TreeTrunk treeTrunk;
    private final List<Leaf> leafs;
    private final List<Fruit> fruits;

    /**
     * Construct instance of tree.
     *
     * @param x              - the place to build the tree at.
     * @param getGroundPlace - a callback that return the ground height according to the place.
     */
    public Tree(float x, Function<Float, Float> getGroundPlace) {
        this.treeXPlace = x;
        this.getGroundPlace = getGroundPlace;
        leafs = new ArrayList<>();
        fruits = new ArrayList<>();
        createTrunk();
        createLeafs();
        createFruits();
    }

    /**
     * Create tree trunk.
     */
    private void createTrunk() {
        treeTrunk = new TreeTrunk(treeXPlace, getGroundPlace);
    }

    /**
     * create fruits.
     */
    private void createFruits() {
        int currentNumberOfFruits = 0;
        float placeInLeaf = WorldConstants.FRUIT_INITIAL_PLACE_IN_LEAF;
        while (WorldConstants.NUM_OF_FRUITS_IN_TREE > currentNumberOfFruits) {
            for (Leaf leaf : leafs) {
                Vector2 place =
                        new Vector2(leaf.getTopLeftCorner().x() + Block.SIZE / placeInLeaf,
                                leaf.getTopLeftCorner().y() + Block.SIZE);
                Fruit fruit = new Fruit(place);
                if (currentNumberOfFruits < WorldConstants.NUM_OF_FRUITS_IN_TREE & (place.x() < treeXPlace |
                        place.x() > treeXPlace + Block.SIZE)) {
                    fruits.add(fruit);
                    currentNumberOfFruits += 1;
                }
            }
            placeInLeaf += 1;
        }
    }

    /**
     * Create leafs.
     */
    private void createLeafs() {
        float startingOptionalLeafsPlace =
                getGroundPlace.apply(treeXPlace) - WorldConstants.TRUNK_HEIGHT +
                        WorldConstants.TRUNK_HEIGHT * WorldConstants.PART_OF_TRUNK_STARTING_LEAFS;
        float endOptionalLeafPlace = getGroundPlace.apply(treeXPlace) - WorldConstants.TRUNK_HEIGHT -
                WorldConstants.TRUNK_HEIGHT * WorldConstants.PART_OF_TRUNK_STARTING_LEAFS;
        float minX = treeXPlace - WorldConstants.EXTRA_PLACE_TO_LEAFS;
        float maxX = treeXPlace + WorldConstants.EXTRA_PLACE_TO_LEAFS + WorldConstants.TRUNK_WIDTH;
        Random random = new Random();
        for (int i = 0; i < WorldConstants.NUM_OF_LEAFS_IN_TREE; i++) {
            float randomX = random.nextFloat(maxX - minX + 1) + minX;
            float randomY = random.nextFloat(startingOptionalLeafsPlace - endOptionalLeafPlace + 1)
                    + endOptionalLeafPlace;
            Leaf leaf = new Leaf(new Vector2(randomX, randomY));
            leafs.add(leaf);
        }
    }

    /**
     * @return the tree trunk.
     */
    public TreeTrunk getTreeTrunk() {
        return treeTrunk;
    }

    /**
     * @return the leafs.
     */
    public List<Leaf> getLeafs() {
        return leafs;
    }

    /**
     * @return the fruits.
     */
    public List<Fruit> getFruits() {
        return fruits;
    }
}
