package image_char_matching;

/**
 * Contains utils functions for characters brightness.
 * author Tal Yehezkel
 */
public class CharacterBrightnessUtils {

    /**
     * Compute the brightness for given character.
     *
     * @param singleChar the character to compute his brightness.
     * @return the char brightness.
     */
    public static double getSingleCharBrightnessLevel(char singleChar) {
        boolean[][] squareBlackAndWitheImg = CharConverter.convertToBoolArray(singleChar);
        double numOfWhiteCell = getNumOfWhiteCells(squareBlackAndWitheImg);
        double pixelsCount = squareBlackAndWitheImg.length * squareBlackAndWitheImg[0].length;
        return numOfWhiteCell / pixelsCount;
    }

    /**
     * Compute the number of white cells in the given array.
     *
     * @param squareBlackAndWitheImg the given array.
     * @return the number of white cells in the given array.
     */
    private static int getNumOfWhiteCells(boolean[][] squareBlackAndWitheImg) {
        int sumWhiteCells = 0;
        for (int i = 0; i < squareBlackAndWitheImg.length; i++) {
            for (int j = 0; j < squareBlackAndWitheImg[0].length; j++) {
                if (squareBlackAndWitheImg[i][j]) {
                    sumWhiteCells += 1;
                }
            }
        }
        return sumWhiteCells;
    }


    /**
     * Compute the normalized brightness for given brightness.
     *
     * @param brightness    the given brightness.
     * @param minBrightness the minimum brightness from the charset.
     * @param maxBrightness the maximum brightness from the charset.
     * @return the normalized brightness.
     */
    public static double getNormalizedBrightness(double brightness, double minBrightness,
                                                 double maxBrightness) {
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }
}
