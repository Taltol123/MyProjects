import java.util.Scanner;

/**
 * Represents a player kind.
 *
 * @author Tal Yehezkel
 */
public class HumanPlayer implements Player {

    /**
     * Default Constructor.
     */
    public HumanPlayer() {
    }

    /*
     * Check if the given squared exists.
     * parameters: The row where the squared is,
     * the col where the squared is.
     * return true if the given square exists and false otherwise.
     */
    private boolean isSquareInBoard(int row, int col, int boardSize) {
        return !((row > boardSize - 1 || col > boardSize - 1 || row < 0 || col < 0));
    }

    /*
     * Check if the given squared is empty.
     * parameters: The row where the squared is,
     * the col where the squared is.
     * return true if the given square is empty and false otherwise.
     */
    private boolean isEmptySquare(int row, int col, Board board) {
        return board.getMark(row, col) == Mark.BLANK;
    }

    private void failedMoveHandle(int row, int col, Board board) {
        int boardSize = board.getSize();
        if (!isSquareInBoard(row, col, boardSize)) {
            System.out.println(Constants.INVALID_COORDINATE);
        }
        if (!isEmptySquare(row, col, board)) {
            System.out.println(Constants.OCCUPIED_COORDINATE);
        }
    }

    /**
     * Responsible for handling the player turn.
     *
     * @param board The play's board.
     * @param mark  The player mark.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        boolean isSuccessfulMove;
        int input, row, col;
        System.out.println(Constants.playerRequestInputString(mark.name()));
        do {
            input = KeyboardInput.readInt();
            row = input / 10;
            col = input % 10;
            isSuccessfulMove = board.putMark(mark, row, col);
            if (!isSuccessfulMove) {
                failedMoveHandle(row, col, board);
            }
        }
        while (!isSuccessfulMove);
    }
}
