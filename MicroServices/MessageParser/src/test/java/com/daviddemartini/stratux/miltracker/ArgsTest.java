package com.daviddemartini.avtracker.services;

import com.daviddemartini.avtracker.services.util.Args;
import com.daviddemartini.avtracker.services.util.StringStuffer;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {

    private static final String TEST_HOST = "localhost";
    private static final int TEST_PORT = 30003;
    private static final double TEST_LAT = 29.91401;
    private static final double TEST_LON = -98.20301;
    private static final String[] BAD_ARGS = StringStuffer.fill(new String[1], "--foobar=x");

    @BeforeAll
    static void firstOperations(){
        // pre test setup here, if required
    }

    @DisplayName("Test Argument Parser - Bad Arguments")
    @Test
    void testArgs_BadArguments() {

        Assertions.assertThrows(UnrecognizedOptionException.class, () -> {
            Args clArgs = new Args(BAD_ARGS);
            clArgs.getDump1090Hostname();
        });

    }

    @DisplayName("Test Argument Parser - Minimum Arguments")
    @Test
    void testArgs_MinimumArguments() throws Exception {
        System.out.println("\tArgument Parser - Minimum Arguments");
        // setup
        String[] GOOD_WEBSOCKET_ARGS = new String[2];
        GOOD_WEBSOCKET_ARGS[0] = "--host=" + TEST_HOST;
        GOOD_WEBSOCKET_ARGS[1] = "--port=" + TEST_PORT;
        // evaluate
        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        assertEquals(TEST_HOST, clArgs.getDump1090Hostname());
        assertEquals(TEST_PORT, clArgs.getDump1090Port());

    }

    @DisplayName("Test Argument Parser - All Arguments")
    @Test
    void testArgs_AllArguments() throws Exception {
        System.out.println("\tArgument Parser - Maximum Arguments");
        // setup
        String[] GOOD_WEBSOCKET_ARGS = new String[4];
        GOOD_WEBSOCKET_ARGS[0] = "--host=" + TEST_HOST;
        GOOD_WEBSOCKET_ARGS[1] = "--port=" + TEST_PORT;
        GOOD_WEBSOCKET_ARGS[2] = "--lat=" + TEST_LAT;
        GOOD_WEBSOCKET_ARGS[3] = "--lon=" + TEST_LON;

        // evaluate
        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        assertEquals(TEST_HOST, clArgs.getDump1090Hostname());
        assertEquals(TEST_PORT, clArgs.getDump1090Port());
        assertEquals(TEST_LAT, clArgs.getFixedPositionLatitude());
        assertEquals(TEST_LON, clArgs.getFixedPositionLongitude());
        assertFalse(clArgs.hasMilOnly());
        assertFalse(clArgs.hasQuiet());

    }

    @DisplayName("Test Argument Parser - Test Flags")
    @Test
    void testArgs_MilOnlyArgs() throws Exception {
        System.out.println("\tAArgument Parser - Test Flags");
        // setup
        String[] FLAGS_ONLY_ARGS = new String[2];
        FLAGS_ONLY_ARGS[0] = "--milonly";
        FLAGS_ONLY_ARGS[1] = "--quiet";

        // evaluate
        Args clArgs = new Args(FLAGS_ONLY_ARGS);
        assertTrue(clArgs.hasMilOnly());
        assertTrue(clArgs.hasQuiet());

    }



}
