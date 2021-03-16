package com.daviddemartini.avtracker.services.message_parse_service.util;

/**
 * Console log Text Formatter...
 *
 */
public class TextFormatter {

    public static final String BOLD = "\\033[1m";
    public static final String END = "\\033[0m";

    public static String bold(String stringToBold){
        return BOLD + stringToBold + END;
    }

}
