package com.daviddemartini.stratux.miltracker.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MilCallsignStringParse {

    private static final Pattern callsignPattern = Pattern.compile("\"Tail\":\"([A-Z]{3,6}[0-9]{1,4})\\s{1,3}");

    public static String Parse (String input){
        // parse string based on a computed regex
        Matcher callsign = callsignPattern.matcher(input);
        if (callsign.find( )) {
            return input;
        }
        return null;
    }
}
