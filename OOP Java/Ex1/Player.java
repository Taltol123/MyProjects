public interface Player {
    /**
     * Executes the player's strategy.
     * @param board The play's board.
     * @param mark The player's mark
     */
    void playTurn(Board board, Mark mark);
}
