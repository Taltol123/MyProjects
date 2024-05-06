package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.HelpingOperations;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Create the ground.
 *
 * @author Tal Yehezkel
 */
public class Terrain {
    private final float groundHeightAtX0;
    private final Vector2 windowDimensions;
    private final NoiseGenerator noiseGenerator;


    /**
     * Create instance of Terrian.
     *
     * @param windowDimensions - the dimension of the window.
     * @param seed             - the seed for the noise.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = (windowDimensions.y() * WorldConstants.START_HEIGHT_GROUND_PART);
        this.windowDimensions = windowDimensions;
        this.noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * Compute the ground height at given place.
     *
     * @param x - the given place.
     * @return the ground height at given place.
     */
    public float groundHeightAt(float x) {
        return groundHeightAtX0 + (float) noiseGenerator.noise(x,
                Block.SIZE * WorldConstants.NOISE_FACTOR);
    }

    /**
     * Create ground in the given range.
     *
     * @param minX - the minimum place for starting the ground.
     * @param maxX - the maximum place for ending the ground.
     * @return the blocks thar represent the ground.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        minX = HelpingOperations.round(minX, Block.SIZE);
        maxX = HelpingOperations.round(maxX, Block.SIZE);

        for (int x = minX; x <= maxX; x += Block.SIZE) {
            double height =
                    (float) Math.min(Math.floor((groundHeightAt(x)) / Block.SIZE) * Block.SIZE,
                            this.windowDimensions.y() - Block.SIZE);
            blocks.addAll(addingBlocksForSinglePlace(x, height));
        }
        return blocks;
    }


    /**
     * Creating the blocks in a given place.
     *
     * @param x      - the place to create the blocks at.
     * @param height the height of the ground in the given place.
     */
    private List<Block> addingBlocksForSinglePlace(int x, double height) {
        List<Block> createdBlocks = new ArrayList<>();
        for (int i = 0; i < WorldConstants.GROUND_BLOCK_DEPTH; i++) {
            height = height + Block.SIZE;
            Renderable renderable =
                    new RectangleRenderable(ColorSupplier.approximateColor(
                            WorldConstants.BASE_GROUND_COLOR));
            Block block = new Block(new Vector2(x, (float) height), renderable);
            block.setTag(WorldConstants.GROUND_TAG);
            createdBlocks.add(block);
        }
        return createdBlocks;
    }


}
