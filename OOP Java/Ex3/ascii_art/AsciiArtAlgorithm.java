package ascii_art;
import image.Image;
import image_char_matching.SubImgCharMatcher;



/**
 * Responsible for running the AsciiArt algorithm.
 * author Tal Yehezkel
 */
public class AsciiArtAlgorithm {
    private final Image image;
    private final SubImgCharMatcher subImageCharMatcher;
    private final Image[][] subImages;
    private final double[][] subImagesBrightness;
    private final int resolution;

    /**
     * Construct instance ofAsciiArtAlgorithm.
     *
     * @param image               the image to run the algorithm on.
     * @param resolution          the resolution og the image.
     * @param subImgCharMatcher   instance of subImgMatcher.
     * @param subImagesBrightness the sub images of the image and their brightness.
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher subImgCharMatcher,
                             Image[][] subImages, double[][] subImagesBrightness) {
        this.subImagesBrightness = subImagesBrightness;
        this.subImages = subImages;
        this.resolution = resolution;
        this.subImageCharMatcher = subImgCharMatcher;
        this.image = image;
    }

    /**
     * Running the algorithm foe match chars to the picture with the given resolusion.
     *
     * @return the asciiChars.
     */
    public char[][] run() {
        int numColsInSubImage = image.getWidth() / resolution;
        int numOfSybImagesInaCol = image.getHeight() / numColsInSubImage;

        char[][] asciiChars = new char[resolution][numOfSybImagesInaCol];
        double subImageBrightness;

        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[0].length; j++) {
                subImageBrightness = subImagesBrightness[i][j];
                var matchingChar = subImageCharMatcher.getCharByImageBrightness(subImageBrightness);
                asciiChars[j][i] = matchingChar;
            }
        }
        return asciiChars;
    }
}