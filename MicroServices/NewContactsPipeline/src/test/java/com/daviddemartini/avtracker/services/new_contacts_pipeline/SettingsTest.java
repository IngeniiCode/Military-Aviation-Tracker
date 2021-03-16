package com.daviddemartini.avtracker.services.new_contacts_pipeline;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.util.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SettingsTest {

    @DisplayName("Test Basic Setting Init")
    @Test
    void test_SettingsBasic() throws Exception {
        System.out.println("\tBasic Settings() test ");

        Settings settings = new Settings();

        assertEquals("localhost", settings.getDump1090Hostname());
        assertEquals(30003, settings.getDump1090Port());
        assertEquals("localhost", settings.getSolrHostname());
        assertEquals(8983, settings.getSolrPort());
        assertEquals("aviation_contacts", settings.getSolrCollectionName());

    }

}
