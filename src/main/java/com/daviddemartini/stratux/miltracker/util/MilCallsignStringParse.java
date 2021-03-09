package com.daviddemartini.stratux.miltracker.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MilCallsignStringParse {

    private static final Pattern msgCallsignPattern = Pattern.compile("\"Tail\":\"([A-Z]{4,6}[0-9]{1,4})\\s{1,3}");
    private static final Pattern callsignMilPatternVerbose = Pattern.compile("([A-Z]{4,6}[0-9]{1,4})\\s{0,3}");
    private static final Pattern callsignMilPatternTail = Pattern.compile("([A-Z]{1,2}[0-9]{5,6})\\s{0,4}");
    private static final Pattern callsignMilPatternHull = Pattern.compile("(_[0-9]{2}-[0-9]{4,5})\\s{0,3}");

    /**
     * Parse the callsign string to see if it matches an expected military callsign.
     * @param input
     * @return
     */
    public static String parseMessageCSV(String input){
        // parse string based on a computed regex
        Matcher callsign = msgCallsignPattern.matcher(input);
        if (callsign.find( )) {
            return input;
        }
        return null;
    }

    /**
     * Perform a basic callsign pattern test for possible Mil Aircraft
     * @param callsign
     * @return
     */
    public static boolean isCallsignMil(String callsign){
        if(callsignMilPatternVerbose.matcher(callsign).find()){
            return true;
        }
        if(callsignMilPatternTail.matcher(callsign).find()){
            return true;
        }
        if(callsignMilPatternHull.matcher(callsign).find()){
            return true;
        }
        return false;

    }
}
