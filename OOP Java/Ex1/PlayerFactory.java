import java.util.Locale;

/**
 * The class represent a factory for creating a player instance.
 *
 * @author Tal Yehezkel
 */
public class PlayerFactory {

    /**
     * Default Constructor.
     */
    public PlayerFactory() {
    }

    /**
     * Create instance of the wanted player.
     *
     * @param type The type of the wanted player.
     * @return The instance of the wanted player.
     */
    public Player buildPlayer(String type) {
        type = type.toLowerCase(Locale.ROOT);
        switch (type) {
            case "whatever":
                return new WhateverPlayer();
            case "human":
                return new HumanPlayer();
            case "genius":
                return new GeniusPlayer();
            case "clever":
                return new CleverPlayer();
            default:
                return null;
        }
    }

}
