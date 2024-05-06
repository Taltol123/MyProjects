/**
 * Represents the board in the game.
 * Responsible on the size and square's mark.
 *
 * @author Tal Yehezkel
 */
public class Board {
    private static final int BOARD_DEFAULT_SIZE = 4;
    private Mark[][] board;
    private int size;

    /**
     * Constructor.
     * Initialize the board with default size/
     */
    public Board() {
        this.size = BOARD_DEFAULT_SIZE;
        initializeBoard();
    }

    /**
     * Constructs and initalize a new Board.
     *
     * @param size The wanted board size.
     */
    public Board(int size) {
        this.size = size;
        initializeBoard();
    }

    /*
     * Initialize the board with the mark blank to each square.
     */
    private void initializeBoard() {
        this.board = new Mark[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }


    /**
     * @return Board size.
     */
    public int getSize() {
        return this.size;
    }


    /*
     * Check if the given squared exist and empty.
     * parameters: The row where the squared is,
     * the col where the squared is.
     * return true if the given square exist and empty and false otherwise.
     */
    private boolean isValidSquare(int row, int col) {
        boolean isSquareInBoard = isSquareInBoard(row, col);
        if (!isSquareInBoard) {
            return false;
        }
        return isEmptySquare(row, col);
    }

    /*
     * Check if the given squared is empty.
     * parameters: The row where the squared is,
     * the col where the squared is.
     * return true if the given square is empty and false otherwise.
     */
    private boolean isEmptySquare(int row, int col) {
        return this.board[row][col] == Mark.BLANK;
    }

    /*
     * Check if the given squared exists.
     * parameters: The row where the squared is,
     * the col where the squared is.
     * return true if the given square exists and false otherwise.
     */
    private boolean isSquareInBoard(int row, int col) {
        return !(row > this.size - 1 || col > this.size - 1 || row < 0 || col < 0);
    }


    /**
     * Put mark in the wanted square.
     *
     * @param mark The mark we want to put in the wanted square.
     * @param row  The row where the squared you want to put mark on be.
     * @param col  The col where the squared you want to put mark on be.
     * @return If the update succeed.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (!isValidSquare(row, col)) {
            return false;
        }
        this.board[row][col] = mark;
        return true;
    }

    /**
     * Get the mark of the squared in this row and col.
     *
     * @param row The row which the squared in.
     * @param col The col which the squared in.
     * @return The mark of the squared in this row and col.
     */
    public Mark getMark(int row, int col) {
        if (isSquareInBoard(row, col)) {
            return this.board[row][col];
        }
        return Mark.BLANK;
    }
}
