package com.daviddemartini.avtracker.datamodels.faa.engine_type;

import com.daviddemartini.avtracker.datamodels.faa.registrant_type.RegistrantType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrantTypeTest {

    @DisplayName("Test Engine Type Static Data Map - String Test")
    @Test
    void test_Engine_Static_Data_Map_String_Test() {

        // test mapping model
        assertEquals(RegistrantType.get("1"),"Individual");
        assertEquals(RegistrantType.get("2"),"Partnership");
        assertEquals(RegistrantType.get("3"),"Corporation");
        assertEquals(RegistrantType.get("4"),"Co-Owned");
        assertEquals(RegistrantType.get("5"),"Government");
        assertEquals(RegistrantType.get("7"),"LLC");
        assertEquals(RegistrantType.get("8"),"Non Citizen Corporation");
        assertEquals(RegistrantType.get("9"),"Non Citizen Co-Owned");

    }

    @DisplayName("Test Engine Type Static Data Map - Integer Test")
    @Test
    void test_Engine_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(RegistrantType.get(1),"Individual");
        assertEquals(RegistrantType.get(2),"Partnership");
        assertEquals(RegistrantType.get(3),"Corporation");
        assertEquals(RegistrantType.get(4),"Co-Owned");
        assertEquals(RegistrantType.get(5),"Government");
        assertEquals(RegistrantType.get(7),"LLC");
        assertEquals(RegistrantType.get(8),"Non Citizen Corporation");
        assertEquals(RegistrantType.get(9),"Non Citizen Co-Owned");


    }

}

