package com.daviddemartini.avtracker.services.message_parse_service;

import com.daviddemartini.avtracker.services.message_parse_service.util.Settings;

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
