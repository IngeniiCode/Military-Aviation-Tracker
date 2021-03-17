package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.util.Args;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsTest {

    private static final String FAA_INPUT_FILE = "./data/ACFTREF-2.txt";
    private static final String TEST_HOST = "localhost";
    private static final int TEST_PORT = 9201;

    @BeforeAll
    static void firstOperations() {
        // pre test setup here, if required
    }

    @DisplayName("Test Argument Parser - Bad Arguments")
    @Test
    void testArgs_BadArguments() {

        String[] BAD_ARGS = new String[1];
        BAD_ARGS[0] = "--foo=bar";

        Assertions.assertThrows(UnrecognizedOptionException.class, () -> {
            Args clArgs = new Args(BAD_ARGS);
            clArgs.getFaaDataFile();
        });

    }

    @DisplayName("Test Argument Parser - Minimum Arguments")
    @Test
    void testArgs_MinimumArguments() throws Exception {
        System.out.println("\tArgument Parser - Minimum Arguments");
        // setup
        String[] GOOD_ARGS = new String[3];
        GOOD_ARGS[0] = "--svc-host=" + TEST_HOST;
        GOOD_ARGS[1] = "--svc-port=" + TEST_PORT;
        GOOD_ARGS[2] = "--faa-models=" + FAA_INPUT_FILE;

        // evaluate
        Args clArgs = new Args(GOOD_ARGS);
        assertEquals(FAA_INPUT_FILE, clArgs.getFaaDataFile());
        assertEquals(TEST_HOST, clArgs.getPublisherHostname());
        assertEquals(TEST_PORT, clArgs.getPublisherPort());


    }

}

