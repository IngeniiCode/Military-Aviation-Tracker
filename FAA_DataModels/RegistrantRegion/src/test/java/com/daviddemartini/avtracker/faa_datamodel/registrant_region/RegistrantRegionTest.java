package com.daviddemartini.avtracker.faa_datamodel.engine_type;

import com.daviddemartini.avtracker.faa_datamodel.registrant_region.RegistrantRegion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrantRegionTest {

    @DisplayName("Test Registrant Region Static Data Map - String Test")
    @Test
    void test_Engine_Static_Data_Map_String_Test() {

        // test mapping model

        assertEquals(RegistrantRegion.get("1"),"Eastern");
        assertEquals(RegistrantRegion.get("2"),"Southwestern");
        assertEquals(RegistrantRegion.get("3"),"Central");
        assertEquals(RegistrantRegion.get("4"),"Western-Pacific");
        assertEquals(RegistrantRegion.get("5"),"Alaskan");
        assertEquals(RegistrantRegion.get("7"),"Southern");
        assertEquals(RegistrantRegion.get("8"),"European");
        assertEquals(RegistrantRegion.get("C"),"Great Lakes");
        assertEquals(RegistrantRegion.get("E"),"New England");
        assertEquals(RegistrantRegion.get("S"),"Northwest Mountain");

    }

    @DisplayName("Test Registrant Region Static Data Map - Integer Test")
    @Test
    void test_Engine_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(RegistrantRegion.get(1),"Eastern");
        assertEquals(RegistrantRegion.get(2),"Southwestern");
        assertEquals(RegistrantRegion.get(3),"Central");
        assertEquals(RegistrantRegion.get(4),"Western-Pacific");
        assertEquals(RegistrantRegion.get(5),"Alaskan");
        assertEquals(RegistrantRegion.get(7),"Southern");
        assertEquals(RegistrantRegion.get(8),"European");


    }

}

