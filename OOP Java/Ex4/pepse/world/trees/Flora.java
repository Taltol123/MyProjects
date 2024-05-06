package pepse.world.trees;

import pepse.world.Block;
import pepse.world.WorldConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Create trees.
 *
 * @author Tal Yehezkel
 */
public class Flora {
    private final Function<Float, Float> getGroundPlace;
    private final int avatarInitialPlace;

    /**
     * Create instance of Flora.
     * @param getGroundPlace a callback that gets x and return the ground value at this x.
     * @param avatarInitialPlace the initial place of the avatar.
     */
    public Flora(Function<Float, Float> getGroundPlace, int avatarInitialPlace) {
        this.getGroundPlace = getGroundPlace;
        this.avatarInitialPlace = avatarInitialPlace;
    }

    /**
     * Builf trees in the gicen range.
     * @param minX - the minimum optional x to build tree in.
     * @param maxX - the maximum optional x to build tree in.
     * @return the builded trees.
     */
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> createdTrees = new ArrayList<>();
        for (int x = minX; x < maxX; x += WorldConstants.TRUNK_WIDTH +
                WorldConstants.MIN_DIFF_BETWEEN_TREES) {
            if (avatarInitialPlace > x + Block.SIZE || avatarInitialPlace +
                    WorldConstants.AVATAR_DIMENSION < x) {
                boolean isAllowedToBuild = isAllowedToBuildTree();
                if (isAllowedToBuild) {
                    Tree tree = new Tree(x, getGroundPlace);
                    createdTrees.add(tree);
                }
            }
        }
        return createdTrees;
    }

    /**
     * @return true if it is allowed to build tree or flase otherwise.
     */
    private static boolean isAllowedToBuildTree() {
        Random random = new Random();
        double randomNumber = random.nextDouble();
        return randomNumber <= WorldConstants.PROBABILITY_FOR_CREATE_TREE;
    }
}
