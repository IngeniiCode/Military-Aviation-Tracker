package com.daviddemartini.avtracker.services.util;

import java.util.Arrays;

/**
 * Simple String[] array utility
 *
 */
public class StringStuffer {

    /**
     * String[] stuffer help utility for unit testing
     *
     */
    public static <T> T[] fill(T[] array, T value) {
        Arrays.fill(array, value);
        return array;
    }

}
