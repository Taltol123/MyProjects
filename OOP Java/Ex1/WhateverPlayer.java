import java.util.Random;

/**
 * Represents a player kind.
 *
 * @author Tal Yehezkel
 */
public class WhateverPlayer implements Player {
    /**
     * Default Constructor.
     */
    public WhateverPlayer() {
    }

    @Override
    public void playTurn(Board board, Mark mark) {
        Random rand = new Random();
        int randRow = rand.nextInt(board.getSize());
        int randCol = rand.nextInt(board.getSize());
        while (!board.putMark(mark, randRow, randCol)) {
            randRow = rand.nextInt(board.getSize());
            randCol = rand.nextInt(board.getSize());
        }
    }
}
