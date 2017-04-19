package com.uk.mycompany.shared.utils;

/**
 * Created by mahutton on 11/04/2017.
 */
public final class StringUtils {

    public static String formatStringToCamelCase(final String word) {
        return word.toString().substring(0, 1) + word.toString().substring(1, word.toString().length()).toLowerCase();
    }

    public static String createFullname(final String firstName, final String surname) {
        final StringBuilder name = new StringBuilder();
        name.append(firstName);
        name.append(" ");
        name.append(surname);
        return name.toString();
    }
}
