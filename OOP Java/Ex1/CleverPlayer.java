import java.security.KeyPair;
import java.util.Random;

/**
 * Represents a player kind.
 *
 * @author Tal Yehezkel
 */
public class CleverPlayer implements Player {
    /**
     * Default Constructor.

     */
    public CleverPlayer() {
    }

    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.putMark(mark, i, j)) {
                    return;
                }
            }
        }
    }
}

