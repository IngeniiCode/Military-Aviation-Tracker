package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.util.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {

    @DisplayName("Test Basic Setting Init")
    @Test
    void test_SettingsBasic() throws Exception {
        System.out.println("\tBasic Settings() test ");

        Settings settings = new Settings();

        assertEquals("localhost", settings.getPublisherHostname());
        assertEquals(9201, settings.getPublisherPort());
        assertEquals("./data/ACFTREF.txt", settings.getFaaDataFile());

    }

}
