package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.util.Args;
import com.daviddemartini.stratux.miltracker.util.Settings;
import com.daviddemartini.stratux.miltracker.util.StringStuffer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SettingsTest {

    @DisplayName("Test Basic Setting Init")
    @Test
    void test_SettingsBasic() throws Exception {
        System.out.println("\tBasic Settings() test ");

        Settings settings = new Settings();

        assertEquals("localhost",settings.getPublisherHostname());
        assertEquals("localhost", settings.getDump1090Hostname());
        assertEquals(30003, settings.getDump1090Port());
        assertEquals(200.0, settings.getMaxDetectionRange());

    }

}
