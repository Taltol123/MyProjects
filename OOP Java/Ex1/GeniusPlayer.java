
/**
 * Represents a player kind.
 *
 * @author Tal Yehezkel
 */
public class GeniusPlayer implements Player {

    /**
     * Default Constructor.
     */
    public GeniusPlayer() {
    }

    @Override
    public void playTurn(Board board, Mark mark) {
        for (int j = 1; j < board.getSize(); j++) {
            for (int i = 0; i < board.getSize(); i++) {
                if (board.putMark(mark, i, j)) {
                    return;
                }
            }
        }

        for (int j = 0; j < board.getSize(); j++) {
            if (board.putMark(mark, j, 0)) {
                return;
            }
        }
    }

}
