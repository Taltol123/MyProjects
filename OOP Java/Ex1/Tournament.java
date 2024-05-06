/**
 * Manage the tournament.
 *
 * @author Tal Yehezkel
 */
public class Tournament {
    private static final String END_TOURNAMENT_MSG = "######### Results #########\n" +
            "Player 1,"
            + " %s won: %s rounds\n" +
            "Player 2, %s won: %s rounds\n" + "Ties: %s\n";

    private int rounds;
    private Renderer renderer;
    private Player[] players;

    /**
     * Constructor.
     * Initialize the tournament details.
     *
     * @param rounds   Num rounds to play.
     * @param renderer The render type.
     * @param player1  First player.
     * @param player2  Second player.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = new Player[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    /**
     * Handle the tournament.
     *
     * @param size        The board size.
     * @param winStreak   The win streak of a fame.
     * @param playerName1 First player's name.
     * @param playerName2 Second player's name.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        int numTies = 0;
        int[] playersPoints = new int[2];
        for (int i = 0; i < this.rounds; i++) {
            Game game = new Game(this.players[i % 2], this.players[1 - (i % 2)], size,
                    winStreak, this.renderer);
            Mark winningMark = game.run();
            if (winningMark == Mark.X) {
                playersPoints[i % 2] += 1;
            } else if (winningMark == Mark.O) {
                playersPoints[1 - (i % 2)] += 1;
            }
            else{
                numTies += 1;
            }
        }
        System.out.printf(END_TOURNAMENT_MSG, playerName1, playersPoints[0],
                playerName2, playersPoints[1], numTies);
    }


    private static boolean checkWrongInput(Object b, String error_message) {
        if (b == null) {
            System.out.println(error_message);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int rounds = Integer.parseInt(args[0]), size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        String rendererType = args[3];
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(rendererType, size);
        if (!checkWrongInput(renderer, Constants.UNKNOWN_RENDERER_NAME)) {return;}
        String firstPlayerType = args[4], secondPlayerType = args[5];
        PlayerFactory playerFactory = new PlayerFactory();
        Player firstPlayer = playerFactory.buildPlayer(firstPlayerType);
        if (!checkWrongInput(firstPlayer, Constants.UNKNOWN_PLAYER_NAME)) {return;}
        Player secondPlayer = playerFactory.buildPlayer(secondPlayerType);
        if (!checkWrongInput(secondPlayer, Constants.UNKNOWN_PLAYER_NAME)) {return;}
        Tournament tournament = new Tournament(rounds, renderer, firstPlayer, secondPlayer);
        tournament.playTournament(size, winStreak, firstPlayerType, secondPlayerType);
    }
}
