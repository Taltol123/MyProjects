package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;
import pepse.world.WorldConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Represents the tree trunk.
 *
 * @author Tal Yehezkel
 */
public class TreeTrunk {
    private final float trunkXPlace;
    private final Function<Float, Float> getGroundPlace;
    List<GameObject> trunkBlocks;

    /**
     * Create instance of tree trunk.
     *
     * @param x              - the x place to build the trunk in.
     * @param getGroundPlace - the function thar return the ground place according to the x.
     */
    public TreeTrunk(float x, Function<Float, Float> getGroundPlace) {
        this.trunkXPlace = x;
        this.getGroundPlace = getGroundPlace;
        Color trunkColor = ColorSupplier.approximateColor(WorldConstants.TRUNK_COLOR);
        createBlocks(trunkColor);
    }

    /**
     * Create the blocks that creation the tree trunk.
     *
     * @param trunkColor - the given color of the tree trunk.
     */
    public void createBlocks(Color trunkColor) {
        trunkBlocks = new ArrayList<>();
        float initialY = getGroundPlace.apply(trunkXPlace);
        for (float y = initialY; y > initialY - WorldConstants.TRUNK_HEIGHT; y -= Block.SIZE) {
            Block block = new Block(new Vector2(trunkXPlace, y), new RectangleRenderable(trunkColor));
            trunkBlocks.add(block);
            block.setTag(WorldConstants.TRUNK_TAG);
        }
    }

    /**
     * @return the blocks which creating the trunk.
     */
    public List<GameObject> getTrunkBlocks() {
        return trunkBlocks;
    }

    /**
     * Handle the updating needed after the avatar jumps.
     */
    public void updateAfterAvatarJump() {
        Color trunkColor = ColorSupplier.approximateColor(WorldConstants.TRUNK_COLOR);
        for (GameObject block : trunkBlocks) {
            block.renderer().setRenderable(new RectangleRenderable(trunkColor));
        }
    }
}
