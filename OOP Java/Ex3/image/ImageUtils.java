package image;

import java.awt.*;

/**
 * Contains utils methods for image operation.
 * author Tal Yehezkel
 */
public class ImageUtils {
    /**
     * Compute brightness for the given image.
     *
     * @param image the image to compute its brightness.
     * @return the image brightness.
     */
    public static double getImageBrightness(Image image) {
        double sumOfAllGreyPixels = 0;
        int sumOfPixels = 0;
        Color pixel;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixel = image.getPixel(i, j);
                double greyPixel =
                        pixel.getRed() * ImageConstants.RED_FRACTION_FOR_GRAY_PIXEL_CALCULATION +
                                pixel.getGreen() * ImageConstants.GREEN_FRACTION_FOR_GRAY_PIXEL_CALCULATION +
                                pixel.getBlue() * ImageConstants.BLUE_FRACTION_FOR_GRAY_PIXEL_CALCULATION;
                sumOfAllGreyPixels += greyPixel;
                sumOfPixels += 1;
            }
        }
        return sumOfAllGreyPixels / (sumOfPixels * ImageConstants.MAX_RGB);
    }

    /**
     * Compute brightness for the given images.
     *
     * @param images the images to compute their brightness.
     * @return the image's brightness.
     */
    public static double[][] getImagesBrightness(Image[][] images) {
        double[][] subImagesBrightness = new double[images.length][images[0].length];
        for (int row = 0; row < images.length; row++) {
            for (int col = 0; col < images[0].length; col++) {
                subImagesBrightness[row][col] = getImageBrightness(images[row][col]);
            }
        }
        return subImagesBrightness;
    }

    /**
     * Get sub images for the given image and resolution.
     *
     * @param image      the image.
     * @param resolution the resolution.
     * @return the sub images.
     */
    public static Image[][] getSubImages(Image image, int resolution) {
        Image pddedImage = paddingImage(image);
        int paddedImageWidth = pddedImage.getWidth();
        int paddedImageHeight = pddedImage.getHeight();
        int subImageWidthAndHeight = paddedImageWidth / resolution;
        int subImagesPerCol = paddedImageHeight / subImageWidthAndHeight;

        Image[][] subImages = new Image[subImagesPerCol][resolution];
        Color[][] subImagePixels;

        for (int row = 0; row < subImages.length; row++) {
            for (int col = 0; col < subImages[row].length; col++) {
                subImagePixels = getColorsForSubImage(row, col, subImageWidthAndHeight, pddedImage);
                subImages[row][col] = new Image(subImagePixels, subImageWidthAndHeight,
                        subImageWidthAndHeight);
            }
        }
        return subImages;
    }

    /**
     * Get colors for sub-image.
     *
     * @param row                    the row where it appeared.
     * @param col                    the col where it appeared.
     * @param subImageWidthAndHeight the width and height of the sub image.
     * @param image                  the image.
     * @return the colors of the subImage.
     */
    private static Color[][] getColorsForSubImage(int row, int col, int subImageWidthAndHeight, Image image) {
        Color[][] subImagePixels = new Color[subImageWidthAndHeight][subImageWidthAndHeight];

        for (int i = 0; i < subImageWidthAndHeight; i++) {
            for (int j = 0; j < subImageWidthAndHeight; j++) {
                int currentPixelRow = (row * subImageWidthAndHeight) + i;
                int currentPixelCol = (col * subImageWidthAndHeight) + j;

                subImagePixels[i][j] = image.getPixel(currentPixelCol, currentPixelRow);
            }
        }
        return subImagePixels;
    }

    /**
     * Padding the given image.
     *
     * @param image the image to pad.
     * @return the padded image,
     */
    public static Image paddingImage(Image image) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        int newWidth = getPowerOfTwoGreaterThan(originalWidth);
        int newHeight = getPowerOfTwoGreaterThan(originalHeight);
        if (!(originalWidth == newWidth) || !(originalHeight == newHeight)) {
            Color[][] newPixelArray = createPaddingPixelArray(image, newWidth, newHeight, originalWidth,
                    originalHeight);
            return new Image(newPixelArray, newWidth, newHeight);
        }
        return image;
    }


    private static Color[][] createPaddingPixelArray(Image originalImage, int width, int height,
                                                     int originalWidth, int originalHeight) {
        Color[][] pixelArray = new Color[width][height];
        int colMargin = (int) ((width - originalWidth) * ImageConstants.PART_OF_THE_FRAME);
        int rowMargin = (int) ((height - originalHeight) * ImageConstants.PART_OF_THE_FRAME);

        for (int row = 0; row < pixelArray.length; row++) {
            for (int col = 0; col < pixelArray[row].length; col++) {
                if (isPlacementNotInMargin(row, col, rowMargin, colMargin, width, height)) {
                    pixelArray[row][col] = originalImage.getPixel(row - rowMargin, col - colMargin);
                } else {
                    pixelArray[row][col] = ImageConstants.DEFAULT_COLOR;
                }
            }
        }
        return pixelArray;
    }

    /**
     * Compute the power of two greater or equal than the given number.
     *
     * @param num the number to operate on.
     * @return the power of two greater or equal than the given number.
     */
    private static int getPowerOfTwoGreaterThan(int num) {
        return (int) Math.pow(2, Math.ceil(Math.log(num) / Math.log(2)));
    }

    /**
     * Check whether the placement is not in the margin.
     *
     * @param row       the row place.
     * @param col       the col place.
     * @param rowMargin the row margin.
     * @param colMargin the col margin.
     * @param width     the width.
     * @param height    the height.
     * @return true if the placement is not in the margin and false otherwise.
     */
    private static boolean isPlacementNotInMargin(int row, int col, int rowMargin, int colMargin,
                                                 int width, int height) {
        boolean notInLeftMargin = (col >= colMargin);
        boolean notInTopMargin = (row >= rowMargin);
        boolean notInDownMargin = (row < (height - rowMargin));
        boolean notInRightMargin = (col < (width - colMargin));
        return notInLeftMargin && notInDownMargin && notInTopMargin && notInRightMargin;
    }


    /**
     * Compute the minimum sub images can be in a row.
     *
     * @returnthe minimum number.
     */
    public static int getMinSubImagesCanBeInRow(Image image) {
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        return Math.max(1, imageWidth / imageHeight);
    }
}

