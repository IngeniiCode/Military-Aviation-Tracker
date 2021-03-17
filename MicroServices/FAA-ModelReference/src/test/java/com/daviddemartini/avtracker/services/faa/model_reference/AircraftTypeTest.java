package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.AircraftType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AircraftTypeTest {

    @DisplayName("Test Aircraft Type Static Data Map - String Test")
    @Test
    void test_Aircraft_Static_Data_Map_String_Test() {

        // test mapping model
        assertEquals(AircraftType.get("1"),"Glider");
        assertEquals(AircraftType.get("2"),"Balloon");
        assertEquals(AircraftType.get("3"),"Blimp/Dirigible");
        assertEquals(AircraftType.get("4"),"Fixed wing single engine");
        assertEquals(AircraftType.get("5"),"Fixed wing multi engine");
        assertEquals(AircraftType.get("6"),"Rotorcraft");
        assertEquals(AircraftType.get("7"),"Weight-shift-control");
        assertEquals(AircraftType.get("8"),"Powered Parachute");
        assertEquals(AircraftType.get("9"),"Gyroplane");
        assertEquals(AircraftType.get("H"),"Hybrid Lift");
        assertEquals(AircraftType.get("O"), "Other");

    }

    @DisplayName("Test Aircraft Type Static Data Map - Integer Test")
    @Test
    void test_Aircraft_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(AircraftType.get(1),"Glider");
        assertEquals(AircraftType.get(2),"Balloon");
        assertEquals(AircraftType.get(3),"Blimp/Dirigible");
        assertEquals(AircraftType.get(4),"Fixed wing single engine");
        assertEquals(AircraftType.get(5),"Fixed wing multi engine");
        assertEquals(AircraftType.get(6),"Rotorcraft");
        assertEquals(AircraftType.get(7),"Weight-shift-control");
        assertEquals(AircraftType.get(8),"Powered Parachute");
        assertEquals(AircraftType.get(9),"Gyroplane");


    }

}
