package io.github.psgs.jane.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * Strings that don't add deterministic value to sentence
     */
    public static final String[] nonDeterministicWords = {
            " in ",
            " at ",
            " there ",
            " here ",
            " to ",
            " now ",
            " then ",
            " who ",
            " what ",
            " when ",
            " where ",
            " why "
    };

    /**
     * Removes a URL from a string
     *
     * @param rawString The string to remove a URL from
     * @return The original string that doesn't contain any URLs
     */
    public static String removeUrl(String rawString) {
        String output = rawString;
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(output);
        int i = 0;
        while (matcher.find()) {
            output = output.replaceAll(matcher.group(i), "").trim();
            i++;
        }
        return output;
    }

    /**
     * Attaches a suffix to a number, for example, "second" would equal "2nd", when "2" is submitted
     *
     * @param number The number to attach a suffix to
     * @return A string that includes the number and the attached suffix
     */
    public static String attachSuffix(int number) {
        char[] chars = String.valueOf(number).toCharArray();
        int lastNumber = Integer.parseInt(String.valueOf(chars[chars.length - 1]));

        switch (lastNumber) {
            case 1:
                return number + "st";
            case 2:
                return number + "nd";
            case 3:
                return number + "rd";
            default:
                return number + "th";
        }
    }

    /**
     * Removes a set of strings, defined in an array, from a string
     *
     * @param input   The string to remove values from
     * @param strings The strings be removed from the input
     * @return A string that doesn't contain any string defined in the array
     */
    public static String stripStringFromArray(String input, String[] strings) {
        String returned = input;
        for (String string : strings) returned.replace(string, "");
        returned.toLowerCase();
        return returned;
    }
}
