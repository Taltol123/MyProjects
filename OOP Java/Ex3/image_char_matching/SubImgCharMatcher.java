
package image_char_matching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Responsible for adjust ASCII character to sub-image with given brightness.
 * author Tal Yehezkel
 */
public class SubImgCharMatcher {
    private Set<Character> charSet;
    private Map<Character, Double> charactersBrightness;
    private double maxBrightness;
    private double minBrightness;

    /**
     * Construct instance of SubImgCharMatcher.
     *
     * @param charset the initialize characters to be in the charSet.
     */
    public SubImgCharMatcher(char[] charset) {
        initializeCharactersBrightness(charset);
        setMaxAndMinBrightness();
    }

    /**
     * Compute and save the characters and their brightness.
     *
     * @param characters the characters to add and compute brightness on.
     */
    private void initializeCharactersBrightness(char[] characters) {
        charSet = new HashSet<>();
        charactersBrightness = new HashMap<>();
        for (char c : characters) {
            charSet.add(c);
            double brightness = CharacterBrightnessUtils.getSingleCharBrightnessLevel(c);
            charactersBrightness.put(c, brightness);
        }
    }

    /**
     * Set the maximum and minimum of the brightnesses of all the charset.
     */
    private void setMaxAndMinBrightness() {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;

        for (char c : charSet) {
            double brightness = charactersBrightness.get(c);
            if (brightness > max) {
                max = brightness;
            }
            if (brightness < min) {
                min = brightness;
            }
        }
        maxBrightness = max;
        minBrightness = min;
    }

    /**
     * @return the optional characters - the current charset.
     */
    public Set<Character> getOptionalCharacters() {
        return charSet;
    }

    /**
     * @return the number of optional characters.
     */
    public int getNumOfOptionalChars() {
        return charSet.size();
    }

    /**
     * Adding given character to the charset.
     *
     * @param c- the char to add to the charset/
     */
    public void addChar(char c) {
        charSet.add(c);

        if (!charactersBrightness.containsKey(c)) {
            double brightness = CharacterBrightnessUtils.getSingleCharBrightnessLevel(c);
            charactersBrightness.put(c, brightness);
            updateMinMaxBrightnessIfNeeded(brightness);
        }
    }

    /**
     * Update the min and max brightness values with the given brightness if needed
     *
     * @param brightness the given brightness.
     */
    private void updateMinMaxBrightnessIfNeeded(double brightness) {
        updateMinBrightness(brightness);
        updateMaxBrightness(brightness);
    }

    /**
     * Update the min brightness value with the given brightness if needed.
     *
     * @param brightness the given brightness.
     * @return true if the min brightness has changed and false otherwise.
     */
    private boolean updateMinBrightness(double brightness) {
        if (brightness < minBrightness) {
            minBrightness = brightness;
            return true;
        }
        return false;
    }

    /**
     * Update the max brightness value with the given brightness if needed.
     *
     * @param brightness the given brightness.
     * @return true if the max brightness has changed and false otherwise.
     */
    private boolean updateMaxBrightness(double brightness) {
        if (brightness > maxBrightness) {
            maxBrightness = brightness;
            return true;
        }
        return false;
    }

    /**
     * Remove the requested character from the charset.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c) {
        if (charSet.contains(c)) {
            charSet.remove(c);
            double brightness = charactersBrightness.get(c);
            if (brightness == maxBrightness || brightness == minBrightness) {
                setMaxAndMinBrightness();
            }
        }
    }

    /**
     * Get the character with the closet brightness to the given brightness.
     *
     * @param brightness the image brightness.
     * @return the character with the closet brightness to the given brightness.
     */
    public char getCharByImageBrightness(double brightness) {
        double minDiff = Double.POSITIVE_INFINITY;
        char wantedChar = ' ';

        for (char character : charSet) {
            double charBrightness = charactersBrightness.get(character);
            double normalizedCharBrightness = CharacterBrightnessUtils.getNormalizedBrightness(
                    charBrightness, minBrightness, maxBrightness);
            double diff = Math.abs(normalizedCharBrightness - brightness);
            if (diff < minDiff) {
                minDiff = diff;
                wantedChar = character;
            } else if (diff == minDiff) {
                if (character < wantedChar) {
                    wantedChar = character;
                }
            }
        }
        return wantedChar;
    }
}
