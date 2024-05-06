package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import exceptions.IncorrectFormatException;
import exceptions.InvalidCommandException;
import image.Image;
import image.ImageUtils;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Responsible for taking input from the clients and operate as requested.
 * author Tal Yehezkel
 */
public class Shell {
    private Image[][] prevSubImages;
    private double[][] prevSubImageBrightness;
    private String prevImagePath;
    private final SubImgCharMatcher subImgCharMatcher;
    private int resolution;
    private AsciiOutput asciiOutput;
    private AsciiOutput htmlOutput;
    private AsciiOutput consoleOutput;
    private Image image;

    /**
     * Construct a shell instance.
     */
    public Shell() {
        this.resolution = ShellConstants.INITIAL_RESOLUTION;
        this.subImgCharMatcher = new SubImgCharMatcher(ShellConstants.INITIAL_CHARS);
        this.prevImagePath = ShellConstants.DEFAULT_IMAGE_NAME;
        updateSubImagesBrightness();
        initializeOutputs();
    }

    /**
     * Initialize the outputs options and the default one.
     */
    private void initializeOutputs() {
        htmlOutput = new HtmlAsciiOutput(ShellConstants.HTML_OUTPUT_FILE_NAME, ShellConstants.HTML_FONT_TYPE);
        consoleOutput = new ConsoleAsciiOutput();
        if (ShellConstants.DEFAULT_OUTPUT_TYPE.equals(ShellConstants.CONSOLE_OUTPUT_TYPE)) {
            asciiOutput = consoleOutput;
        } else {
            asciiOutput = htmlOutput;
        }
    }


    /**
     * Start the running of the program.
     */
    public void run() {
        do {
            System.out.print(ShellConstants.INITIAL_MESSAGE);
            String input = KeyboardInput.readLine();
            String[] inputWords = input.split(ShellConstants.INPUT_SEPARATOR_REGEX);
            String firstInputWord = inputWords[0];

            switch (firstInputWord) {
                case ShellConstants.EXIT_COMMAND:
                    return;
                case ShellConstants.ASCII_ART_COMMAND:
                    handleAsciiArtOperation();
                    break;
                case ShellConstants.REMOVE_COMMAND:
                    handleRemoveOperation(inputWords);
                    break;
                case ShellConstants.ADD_COMMAND:
                    handleAddOperation(inputWords);
                    break;
                case ShellConstants.OUTPUT_COMMAND:
                    handleOutputOperation(inputWords);
                    break;
                case ShellConstants.IMAGE_COMMAND:
                    handlingImageOperation(inputWords);
                    break;
                case ShellConstants.RES_COMMAND:
                    handleResOperation(inputWords);
                    break;
                case ShellConstants.WATCH_CHARS_COMMAND:
                    handlingWatchingOptionalCharacters();
                    break;
                default:
                    handleInvalidCommand();
                    break;
            }

        } while (true);
    }

    /**
     * handle AsciiArt operation.
     */
    private void handleAsciiArtOperation() {
        try {
            doAsciiArtOperation();
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle remove operation.
     *
     * @param inputWords input words.
     */
    private void handleRemoveOperation(String[] inputWords) {
        try {
            doRemoveOperation(inputWords);
        } catch (IncorrectFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle add operation.
     *
     * @param inputWords input words.
     */
    private void handleAddOperation(String[] inputWords) {
        try {
            doAddOperation(inputWords);
        } catch (IncorrectFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle output operation.
     *
     * @param inputWords input words.
     */
    private void handleOutputOperation(String[] inputWords) {
        try {
            doOutputOperation(inputWords);
        } catch (IncorrectFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle resolution operation.
     *
     * @param inputWords input words.
     */
    private void handleResOperation(String[] inputWords) {
        try {
            doResOperation(inputWords);
        } catch (IncorrectFormatException | InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handle invalid command.
     */
    private void handleInvalidCommand() {
        try {
            throw new InvalidCommandException(ShellConstants.NOT_EXPECTED_COMMAND_MSG);
        } catch (InvalidCommandException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @return true if there are optional characters and false otherwise.
     */
    private boolean isExistsOptionalCharacters() {
        if (subImgCharMatcher.getNumOfOptionalChars() > ShellConstants.MINIMUM_OPTIONAL_CHARACTERS) {
            return true;
        }
        return false;
    }

    /**
     * Handle the operation for output command - update the output kind.
     *
     * @param inputWords the command input separated to words.
     */
    private void doOutputOperation(String[] inputWords) throws IncorrectFormatException {
        if (inputWords.length != ShellConstants.OUTPUT_EXPECTED_COMMAND_LENGTH ||
                !ShellConstants.OUTPUT_COMMAND_SECOND_WORD_OPTIONS.contains(inputWords[1])) {
            throw new IncorrectFormatException(ShellConstants.OUTPUT_COMMAND_FAILED_MSG);
        }
        String secondWord = inputWords[1];
        if (secondWord.equals(ShellConstants.HTML_OUTPUT_TYPE)) {
            asciiOutput = htmlOutput;
        } else {
            asciiOutput = consoleOutput;
        }
    }


    /**
     * Handle the operation for asciiArt command - running the asciiArt algorithm.
     */
    private void doAsciiArtOperation() throws InvalidCommandException {
        if (!isExistsOptionalCharacters()) {
            throw new InvalidCommandException(ShellConstants.EMPTY_CHARSET_MSG);
        } else {
            char[][] chars = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher,
                    prevSubImages, prevSubImageBrightness).run();
            asciiOutput.out(chars);
        }
    }


    /**
     * Handle the operation for image command - changing the image.
     *
     * @param inputWords the command input separated to words.
     */
    private void handlingImageOperation(String[] inputWords) {
        String newImagePath = inputWords[1];
        if (!newImagePath.equals(prevImagePath)) {
            prevImagePath = newImagePath;
            updateSubImagesBrightness();
        }
    }

    /**
     * Update the sub images and their brightness.
     */
    private void updateSubImagesBrightness() {
        try {
            Image originImage = new Image(prevImagePath);
            this.image = ImageUtils.paddingImage(originImage);
            Image[][] subImages = ImageUtils.getSubImages(image, resolution);
            this.prevSubImageBrightness = ImageUtils.getImagesBrightness(subImages);
            prevSubImages = subImages;
        } catch (IOException e) {
            System.out.println(ShellConstants.IMAGE_ERROR_MSG);
        }
    }

    /**
     * @param inputWords the command input separated to words.
     */
    private void doResOperation(String[] inputWords) throws IncorrectFormatException,
            InvalidCommandException {
        if (inputWords.length != ShellConstants.RES_EXPECTED_COMMAND_LENGTH ||
                !ShellConstants.RES_COMMAND_SECOND_WORD_OPTIONS.contains(inputWords[1])) {
            throw new IncorrectFormatException(ShellConstants.INCORRECT_RESOLUTION_COMMAND_MSG);
        } else {
            String secondWord = inputWords[1];
            int newResolution;
            boolean isChangeAllowed = false;
            if (secondWord.equals(ShellConstants.RES_UP_COMMAND)) {
                newResolution = resolution * ShellConstants.RESOLUTION_INCREASING_PARAMETER;
                if (newResolution <= image.getWidth()) {
                    isChangeAllowed = true;
                }
            } else {
                newResolution = resolution / ShellConstants.RESOLUTION_DECREASING_PARAMETER;
                int minSubImagesInRow = ImageUtils.getMinSubImagesCanBeInRow(image);
                if (newResolution >= minSubImagesInRow) {
                    isChangeAllowed = true;
                }
            }
            handlingChangesDueToResCommand(isChangeAllowed, newResolution);
        }
    }

    /**
     * Update resolution and the image and sub images brightness according to the new resolution if the
     * resolution can be changes.
     *
     * @param isChangeAllowed true if the resolution can be changed false otherwise.
     * @param newResolution   the new resolution tobe changed to if possibloe.
     */
    private void handlingChangesDueToResCommand(boolean isChangeAllowed, int newResolution)
            throws InvalidCommandException {
        if (isChangeAllowed) {
            resolution = newResolution;
            System.out.printf(ShellConstants.RESOLUTION_CHANGES_MSG, resolution);
            updateSubImagesBrightness();
        } else {
            throw new InvalidCommandException(ShellConstants.CHANGE_RESOLUTION_FAILED_MSG);
        }
    }

    /**
     * Handling removes operations according to the command.
     *
     * @param inputWords the command input separated to words.
     */
    private void doRemoveOperation(String[] inputWords) throws IncorrectFormatException {
        if (inputWords[1].length() == 1) {
            char wantedChar = inputWords[1].charAt(0);
            removeToOptionalCharacters(wantedChar);
        } else if (inputWords[1].equals(ShellConstants.All_COMMAND)) {
            removeAllToOptionalCharacters();
        } else if (inputWords[1].equals(ShellConstants.SPACE_COMMAND)) {
            removeSpaceToOptionalCharacters();
        } else if (inputWords[1].contains(ShellConstants.RANGE_MARK) &
                inputWords[1].length() == ShellConstants.NUM_CHARACTERS_IN_RANGE
                & inputWords[1].charAt(1) == ShellConstants.RANGE_MARK.charAt(0)) {
            String[] characters = inputWords[1].split(ShellConstants.RANGE_MARK);
            removeToOptionalCharactersCharactersBetween(characters[0].charAt(0), characters[1].charAt(0));
        } else {
            throw new IncorrectFormatException(ShellConstants.REMOVE_ERROR_MSG);
        }
    }

    /**
     * Handling removes operations according to the command.
     *
     * @param inputWords the command input separated to words.
     */
    private void doAddOperation(String[] inputWords) throws IncorrectFormatException {
        if (inputWords[1].length() == 1) {
            char wantedChar = inputWords[1].charAt(0);
            addToOptionalCharacters(wantedChar);
        } else if (inputWords[1].equals(ShellConstants.ALL_CHARACTERS_COMMAND)) {
            addAllToOptionalCharacters();
        } else if (inputWords[1].equals(ShellConstants.SPACE_COMMAND)) {
            addSpaceToOptionalCharacters();
        } else if (inputWords[1].contains(ShellConstants.RANGE_MARK) & inputWords[1].length() ==
                ShellConstants.NUM_CHARACTERS_IN_RANGE & inputWords[1].charAt(1) ==
                ShellConstants.RANGE_MARK.charAt(0)) {
            String[] characters = inputWords[1].split(ShellConstants.RANGE_MARK);
            addToOptionalCharactersCharactersBetween(characters[0].charAt(0), characters[1].charAt(0));
        } else {
            throw new IncorrectFormatException(ShellConstants.ADD_ERROR_MSG);
        }
    }


    /**
     * Handling watching all the characters command.
     */
    private void handlingWatchingOptionalCharacters() {
        Set<Character> optionalCharacters = subImgCharMatcher.getOptionalCharacters();
        for (int i = 0; i <= ShellConstants.NUM_OF_ASCII_CHARACTERS; i++) {
            if (optionalCharacters.contains((char) i)) {
                System.out.print((char) i + " ");
            }
        }
        System.out.println();
    }

    /**
     * Add required character.
     *
     * @param charToAdd requires character.
     */
    private void addToOptionalCharacters(char charToAdd) {
        subImgCharMatcher.addChar(charToAdd);
    }


    /**
     * Adding all characters tothe optional characters.
     */
    private void addAllToOptionalCharacters() {
        for (char c = ShellConstants.MINIMAL_CHAR_IN_ALL_CHARS;
             c <= ShellConstants.MAXIMAL_CHAR_IN_ALL_CHARS; c++) {
            subImgCharMatcher.addChar(c);
        }
    }

    /**
     * Adding the character space to the optional characters.
     */
    private void addSpaceToOptionalCharacters() {
        subImgCharMatcher.addChar(ShellConstants.SPACE_CHARACTER);
    }

    /**
     * Add in to the optional characters all the characters in the given range.
     *
     * @param firstChar  first character in the requested range.
     * @param secondChar last characters in the requested range.
     */
    private void addToOptionalCharactersCharactersBetween(char firstChar, char secondChar) {
        if (secondChar < firstChar) {
            char temp = firstChar;
            firstChar = secondChar;
            secondChar = temp;
        }
        for (char c = firstChar; c <= secondChar; c++) {
            subImgCharMatcher.addChar(c);
        }
    }

    /**
     * Remove feom optional characters the given char.
     *
     * @param charToRemove - the char which required to remove.
     */
    private void removeToOptionalCharacters(char charToRemove) {
        subImgCharMatcher.removeChar(charToRemove);
    }

    /**
     * Remove all the characters from the optional characters.
     */
    private void removeAllToOptionalCharacters() {
        for (char c = ShellConstants.MINIMAL_CHAR_IN_ALL_CHARS; c <= ShellConstants.MAXIMAL_CHAR_IN_ALL_CHARS;
             c++) {
            subImgCharMatcher.removeChar(c);
        }
    }

    /**
     * Remove the space characters from the optional characters.
     */
    private void removeSpaceToOptionalCharacters() {
        subImgCharMatcher.removeChar(ShellConstants.SPACE_CHARACTER);
    }

    /**
     * Remove from the optional characters all the characters between the given numbers.
     *
     * @param firstChar  the first character in the range to remove.
     * @param secondChar the last character in the range to remove.
     */
    private void removeToOptionalCharactersCharactersBetween(char firstChar, char secondChar) {
        if (secondChar < firstChar) {
            char temp = firstChar;
            firstChar = secondChar;
            secondChar = temp;
        }
        for (char c = firstChar; c <= secondChar; c++) {
            subImgCharMatcher.removeChar(c);
        }
    }

    /**
     * Main program.
     *
     * @param args the program arguments.
     */
    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run();
    }
}