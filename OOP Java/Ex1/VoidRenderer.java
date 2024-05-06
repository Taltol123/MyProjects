/**
 * Represents a render type.
 * @author Tal Yehezkel
 */
public class VoidRenderer implements Renderer{
    /**
     * Default Constructor.
     */
    public VoidRenderer(){}

    /**
     * Render which not show anything on the screen.
     * @param board The game board.
     */
    @Override
    public void renderBoard(Board board) {
    }
}
