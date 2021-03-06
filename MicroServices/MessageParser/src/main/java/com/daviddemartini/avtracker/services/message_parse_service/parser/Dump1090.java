package com.daviddemartini.avtracker.services.message_parse_service.parser;

import java.util.Arrays;
import java.util.List;

/**
 * Package to parse various dump1090 data formats into a list, or other object or list of objects
 */
public class Dump1090 {

    /**
     * Parse a SBS (Basestation) csv string into a list of strings
     *
     * @param csvMessageString - basestation SBS csv formatted string
     * @return
     */
    public List<String> parse1090csv(String csvMessageString) {
        return Arrays.asList(csvMessageString.split(","));
    }
}
