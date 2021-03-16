package com.daviddemartini.avtracker.services.new_contacts_pipeline;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.util.Args;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsTest {

    private static final String TEST_MSG_HOST = "localhost";
    private static final int TEST_MSG_PORT = 30003;
    private static final String TEST_SOLR_HOST = "loalhost";
    private static final int TEST_SOLR_PORT = 8983;
    private static final String TEST_SOLR_CORE = "aviation_contacts";

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
            clArgs.getDump1090Hostname();
        });

    }

    @DisplayName("Test Argument Parser - Minimum Arguments")
    @Test
    void testArgs_MinimumArguments() throws Exception {
        System.out.println("\tArgument Parser - Minimum Arguments");
        // setup
        String[] GOOD_WEBSOCKET_ARGS = new String[2];
        GOOD_WEBSOCKET_ARGS[0] = "--msg-host=" + TEST_MSG_HOST;
        GOOD_WEBSOCKET_ARGS[1] = "--msg-port=" + TEST_MSG_PORT;
        // evaluate
        Args clArgs = new Args(GOOD_WEBSOCKET_ARGS);
        assertEquals(TEST_MSG_HOST, clArgs.getDump1090Hostname());
        assertEquals(TEST_MSG_PORT, clArgs.getDump1090Port());

    }

}
