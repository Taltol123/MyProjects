/**
 * This class represents aa single game.
 * Responsible on the result information.
 *
 * @author Tal Yehezkel
 */
public class Game {
    private Player playerX;
    private Player playerO;
    private Renderer renderer;
    private Board board;
    private int winStreak;
    private static final int BOARD_DEFAULT_SIZE = 4;
    private static final int DEFAULT_WIN_STREAK = 3;

    /**
     * Constructs and initialize a new Game with default size and winstreak.
     *
     * @param playerX  - The player with mark O.
     * @param playerO  - The player with mark X.
     * @param renderer - The renderer.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.board = new Board(BOARD_DEFAULT_SIZE);
        this.winStreak = DEFAULT_WIN_STREAK;
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     * Constructs and initialize a new Game with default size and winstreak.
     *
     * @param playerX   - The player with mark O.
     * @param playerO   - The player with mark X.
     * @param size      - The board size.
     * @param winStreak - The win streak.
     * @param renderer  - The renderer.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new Board(size);
        if (isValidWinStreak(winStreak)) {
            this.winStreak = winStreak;
        } else {
            this.winStreak = size;
        }
        this.renderer = renderer;
    }

    /*
     * Check if the win streak bigger than the minimum and smaller than the board size.
     */
    private boolean isValidWinStreak(int winStreak) {
        return winStreak >= 2 && winStreak <= board.getSize();
    }

    /**
     * @return The current win streak.
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /*
     * Check if there are any blank squares in the board.
     */
    private boolean areExistBlankSquares(Board board) {
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getMark(i, j) == Mark.BLANK) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Check if there is a vertical(up-down) sequence with the given mark of the given size.
     */
    private boolean isVerticalWinStreak(int row, int col, Mark mark, Board board) {
        int count = -1;
        count += getSequenceSizeUnderSquare(row, col, mark, board);
        count += getSequenceSizeAboveSquare(row, col, mark, board);
        return count >= this.winStreak;
    }

    /*
     * Check the size of the sequence that under the given square with the given mark.
     */
    private int getSequenceSizeUnderSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int rowUp = row;
        while (rowUp < board.getSize()) {
            if (board.getMark(rowUp, col) != mark) {
                return count;
            }
            count += 1;
            rowUp += 1;
        }
        return count;
    }

    /*
     * Check the size of the sequence that above the given square with the given mark.
     */
    private int getSequenceSizeAboveSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int rowDown = row;
        while (rowDown >= 0) {
            if (board.getMark(rowDown, col) != mark) {
                return count;
            }
            count += 1;
            rowDown -= 1;
        }
        return count;
    }

    /*
     * Check if there is a horizontal(right-left) sequence with the given mark of the given size.
     */
    private boolean isHorizontalStreak(int row, int col, Mark mark, Board board) {
        int count = -1;
        count += getSequenceSizeOfTheRightSideFromSquare(row, col, mark, board);
        count += getSequenceSizeOfTheLeftSideFromSquare(row, col, mark, board);
        return count >= this.winStreak;
    }

    /*
     * Check the size of the sequence of the right side from the given square with the given mark.
     */
    private int getSequenceSizeOfTheRightSideFromSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int colRight = col;
        while (colRight < board.getSize()) {
            if (board.getMark(row, colRight) != mark) {
                return count;
            }
            count += 1;
            colRight += 1;
        }
        return count;
    }

    /*
     * Check the size of the sequence of the left side from the given square with the given mark.
     */
    private int getSequenceSizeOfTheLeftSideFromSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int colLeft = col;
        while (colLeft >= 0) {
            if (board.getMark(row, colLeft) != mark) {
                return count;
            }
            count += 1;
            colLeft -= 1;
        }
        return count;
    }

    /*
     * Check if there is a diagonal with the given mark of the given size.
     */
    private boolean isUpDiagonalWinStreak(int row, int col, Mark mark, Board board) {
        int count = -1;
        count += getSequenceSizeOfTheRightUpDiagonalSideFromSquare(row, col, mark, board);
        count += getSequenceSizeOfTheLeftUpDiagonalSideFromSquare(row, col, mark, board);
        return count >= this.winStreak;
    }

    /*
     * Check the size of the sequence of the right side diagonal from the given square with the given mark.
     */
    private int getSequenceSizeOfTheRightUpDiagonalSideFromSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int rowUp = row;
        int colUp = col;
        int boardSize = board.getSize();
        while (rowUp < boardSize & colUp < boardSize) {
            if (board.getMark(rowUp, colUp) != mark) {
                return count;
            }
            count += 1;
            rowUp += 1;
            colUp += 1;
        }
        return count;
    }

    /*
     * Check the size of the sequence of the left side diagonal from the given square with the given mark.
     */
    private int getSequenceSizeOfTheLeftUpDiagonalSideFromSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int rowDown = row;
        int colDown = col;
        while (rowDown >= 0 & colDown >= 0) {
            if (board.getMark(rowDown, colDown) != mark) {
                return count;
            }
            count += 1;
            rowDown -= 1;
            colDown -= 1;
        }
        return count;
    }


    /*
     * Check if there is a diagonal with the given mark of the given size.
     */
    private boolean isDownDiagonalWinStreak(int row, int col, Mark mark, Board board) {
        int count = -1;
        count += getSequenceSizeOfTheLeftDownDiagonalSideFromSquare(row, col, mark, board);
        count += getSequenceSizeOfTheRightDownDiagonalSideFromSquare(row, col, mark, board);
        return count >= this.winStreak;
    }

    /*
     * Check the size of the sequence of the left side diagonal from the given square with the given mark.
     */
    private int getSequenceSizeOfTheLeftDownDiagonalSideFromSquare(int row, int col, Mark mark, Board board) {
        int count = 0;
        int rowUp = row;
        int colDown = col;
        while (rowUp < board.getSize() & colDown >= 0) {
            if (board.getMark(rowUp, colDown) != mark) {
                return count;
            }
            count += 1;
            rowUp += 1;
            colDown -= 1;
        }
        return count;
    }

    /*
     * Check the size of the sequence of the right side diagonal from the given square with the given mark.
     */
    private int getSequenceSizeOfTheRightDownDiagonalSideFromSquare(int row, int col, Mark mark,
                                                                    Board board) {
        int count = 0;
        int rowDown = row;
        int colUp = col;
        while (rowDown >= 0 & colUp < board.getSize()) {
            if (board.getMark(rowDown, colUp) != mark) {
                return count;
            }
            count += 1;
            rowDown -= 1;
            colUp += 1;
        }
        return count;
    }

    /*
     * Check if there is a sequence with the given mark of the given size.
     */
    private boolean checkWinStreak(int row, int col, Mark mark, Board board) {
        if (isDownDiagonalWinStreak(row, col, mark, board) || isHorizontalStreak(row, col, mark,
                board) || isVerticalWinStreak(row, col, mark, board) ||
                isUpDiagonalWinStreak(row, col, mark, board)) {
            return true;
        }
        return false;
    }

    /*
     * Check if the given player win the game.
     */
    private boolean isPlayerWin(Mark playerMark, Board board) {
        int boardSize = board.getSize();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board.getMark(i, j) == playerMark & checkWinStreak(i, j, playerMark, board)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Run a single game between two players.
     *
     * @return The mark of the winning player or blank if it is a tie.
     */
    public Mark run() {
        while (areExistBlankSquares(board)) {
            playerX.playTurn(board, Mark.X);
            this.renderer.renderBoard(board);
            if (isPlayerWin(Mark.X, board)) {
                return Mark.X;
            }
            if (!areExistBlankSquares(board)) {
                return Mark.BLANK;
            }
            playerO.playTurn(board, Mark.O);
            this.renderer.renderBoard(board);
            if (isPlayerWin(Mark.O, board)) {
                return Mark.O;
            }
        }

        return Mark.BLANK;
    }

    /**
     * @return The board's size.
     */
    public int getBoardSize() {
        return board.getSize();
    }

}
