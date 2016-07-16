package cc.chaoticweg.mc.LeaveAMessage;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

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
        for (int index = 1; index < words.length - 1; index++)
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
        List<String> resultList = Lists.newArrayList();
        resultList.addAll(Arrays.asList(original).subList(firstIndex, lastIndex + 1));
        return (String[]) resultList.toArray();
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

}
