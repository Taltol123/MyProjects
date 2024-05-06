package ascii_art;

import java.util.Arrays;
import java.util.List;

/**
 * Contains all the constants that the Shell class need.
 * author Tal Yehezkel
 */
public class ShellConstants {
    /**
     * Initial message.
     */
    public static final String INITIAL_MESSAGE = ">>> ";
    /**
     * Default image name.
     */
    public static final String DEFAULT_IMAGE_NAME = "cat.jpeg";
    /**
     * Initial resolution.
     */
    public static final int INITIAL_RESOLUTION = 128;
    /**
     * Initial chars.
     */
    public static final char[] INITIAL_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * Resolution increasing parameter.
     */
    public static final int RESOLUTION_INCREASING_PARAMETER = 2;
    /**
     * Resolution decreasing parameter.
     */
    public static final int RESOLUTION_DECREASING_PARAMETER = 2;
    /**
     * Resolution changes message.
     */
    public static final String RESOLUTION_CHANGES_MSG = "Resolution set to %d.\n";
    /**
     * The html output name.
     */
    public static final String HTML_OUTPUT_FILE_NAME = "out.html";
    /**
     * Html font name.
     */
    public static final String HTML_FONT_TYPE = "Courier New";
    /**
     * Image command.
     */
    public static final String IMAGE_COMMAND = "image";
    /**
     * Output command.
     */
    public static final String OUTPUT_COMMAND = "output";
    /**
     * All characters command.
     */
    public static final String ALL_CHARACTERS_COMMAND = "all";
    /**
     * Add command.
     */
    public static final String ADD_COMMAND = "add";
    /**
     * Default output type.
     */
    public static final String DEFAULT_OUTPUT_TYPE = "console";
    /**
     * Remove command.
     */
    public static final String REMOVE_COMMAND = "remove";
    /**
     * Resolution command.
     */
    public static final String RES_COMMAND = "res";
    /**
     * Exit command.
     */
    public static final String EXIT_COMMAND = "exit";
    /**
     * Watch chars command.
     */
    public static final String WATCH_CHARS_COMMAND = "chars";
    /**
     * AsciiArt command.
     */
    public static final String ASCII_ART_COMMAND = "asciiArt";
    /**
     * Add error message.
     */
    public static final String ADD_ERROR_MSG = "Did not add due to incorrect format.";
    /**
     * Remove error message.
     */
    public static final String REMOVE_ERROR_MSG = "Did not remove due to incorrect format.";
    /**
     * Change resoultion error message.
     */
    public static final String CHANGE_RESOLUTION_FAILED_MSG = "Did not change resolution due to exceeding" +
            " boundaries.";
    /**
     * Incorrect resolution command message.
     */
    public static final String INCORRECT_RESOLUTION_COMMAND_MSG = "Did not change resolution due to " +
            "incorrect format.";
    /**
     * Image error message.
     */
    public static final String IMAGE_ERROR_MSG = "Did not execute due to problem with image file.";
    /**
     * Output command failed message.
     */
    public static final String OUTPUT_COMMAND_FAILED_MSG = "Did not change output method due to incorrect " +
            "format.";
    /**
     * Empty optional characters message.
     */
    public static final String EMPTY_CHARSET_MSG = "Did not execute. Charset is empty.";
    /**
     * Not expected command message.
     */
    public static final String NOT_EXPECTED_COMMAND_MSG = "Did not execute due to incorrect command.";
    /**
     * Input separator regex.
     */
    public static final String INPUT_SEPARATOR_REGEX = "\\s";
    /**
     * Output expected command length.
     */
    public static final int OUTPUT_EXPECTED_COMMAND_LENGTH = 2;
    /**
     * Resolution expected command length.
     */
    public static final int RES_EXPECTED_COMMAND_LENGTH = 2;
    /**
     * Minimum optional characters.
     */
    public static final int MINIMUM_OPTIONAL_CHARACTERS = 0;
    /**
     * Remove expected command length.
     */
    public static final int REMOVE_EXPECTED_COMMAND_LENGTH = 2;
    /**
     * Res up command.
     */
    public static final String RES_UP_COMMAND = "up";
    /**
     * Res down command.
     */
    public static final String RES_DOWN_COMMAND = "down";
    /**
     * Res command second word options.
     */
    public static final List<String> RES_COMMAND_SECOND_WORD_OPTIONS = Arrays.asList(RES_UP_COMMAND,
            RES_DOWN_COMMAND);
    /**
     * Console output type.
     */
    public static final String CONSOLE_OUTPUT_TYPE = "console";
    /**
     * Html output type.
     */
    public static final String HTML_OUTPUT_TYPE = "html";
    /**
     * Output command second word options.
     */
    public static final List<String> OUTPUT_COMMAND_SECOND_WORD_OPTIONS = Arrays.asList(HTML_OUTPUT_TYPE,
            CONSOLE_OUTPUT_TYPE);
    /**
     * Space command.
     */
    public static final String SPACE_COMMAND = "space";
    /**
     * Range mark.
     */
    public static final String RANGE_MARK = "-";
    /**
     * Number of characters in range.
     */
    public static final int NUM_CHARACTERS_IN_RANGE = 3;
    /**
     * Number of ascii characters.
     */
    public static final int NUM_OF_ASCII_CHARACTERS = 128;
    /**
     * The minimal character in all chars.
     */
    public static final char MINIMAL_CHAR_IN_ALL_CHARS = ' ';
    /**
     * The maximal character in all chars.
     */
    public static final char MAXIMAL_CHAR_IN_ALL_CHARS = '~';
    /**
     * Space character.
     */
    public static final char SPACE_CHARACTER = ' ';
    /**
     * All command.
     */
    public static String All_COMMAND = "all";
}
