package cc.chaoticweg.mc.LeaveAMessage;

import java.util.Date;

/**
 * Various utilities.
 */
public class LAMUtils {

    // pls no instantiate
    private LAMUtils() {}

    /**
     * Join an array of {@code String}s, separated by the {@code separator}.
     *
     * @param words     The {@code String}s to join
     * @param separator The {@code String} to separate each joined string
     * @return A single {@code String} containing each word separated by the separator
     */
    public static String join(String[] words, String separator) {
        // bail out if there are no words
        if (words.length < 1)
            return "";

        // bail out if there is only one word
        if (words.length == 1)
            return words[0];


        // start with the first word
        StringBuilder sb = new StringBuilder(words[0]);

        // add the separator, then the next word
        for (int index = 1; index < words.length; index++)
            sb.append(separator).append(words[index]);

        return sb.toString().trim();
    }

    /**
     * {@link LAMUtils#join(String[], String)}, with the default separator (one space).
     *
     * @param words The {@code String}s to join
     * @return A {@code String}: the array, joined by spaces
     */
    public static String join(String[] words) {
        return LAMUtils.join(words, " ");
    }

    /**
     * Copy elements between {@code firstIndex} and {@code endIndex} (inclusively) into a new array.
     *
     * @param original The original array
     * @param firstIndex The index of the first element to copy
     * @param lastIndex The index of the last element to copy
     * @return The elements from firstIndex to lastIndex (inclusively)
     */
    public static String[] splice(String[] original, int firstIndex, int lastIndex) {
        int howManyElements = (lastIndex + 1) - firstIndex;
        String[] result = new String[howManyElements];

        System.arraycopy(original, firstIndex, result, 0, howManyElements);

        return result;
    }

    /**
     * Splice from the first element to the last.
     *
     * @param original
     * @param firstIndex
     * @return
     */
    public static String[] splice(String[] original, int firstIndex) {
        return LAMUtils.splice(original, firstIndex, (original.length - 1));
    }

    public static String formatDate(long millis) {
        int secSinceDate = Math.round(((new Date()).getTime() - millis) / 1000);
        if (secSinceDate < 60) return "just now";

        int minSinceDateRaw = Math.round((float)secSinceDate / 60);
        if (minSinceDateRaw < 60) return String.format("%dm ago", minSinceDateRaw);


        int hrsSinceDateRaw = Math.round((float)minSinceDateRaw / 60);
        if (hrsSinceDateRaw < 24) return String.format("%dh ago", hrsSinceDateRaw);

        int daysSinceDateRaw = Math.round((float)hrsSinceDateRaw / 24);
        if ((daysSinceDateRaw / 7) < 4) return String.format("%dd ago", daysSinceDateRaw);

        return "more than a month ago";
    }

    public static String formatDate(Date date) { return formatDate(date.getTime()); }

}
