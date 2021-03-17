package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.EngineType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EngineTypeTest {

    @DisplayName("Test Engine Type Static Data Map - String Test")
    @Test
    void test_Engine_Static_Data_Map_String_Test() {

        // test mapping model
        assertEquals(EngineType.get("0"),"None");
        assertEquals(EngineType.get("1"),"Reciprocating");
        assertEquals(EngineType.get("2"),"Turbo-prop");
        assertEquals(EngineType.get("3"),"Turbo-shaft");
        assertEquals(EngineType.get("4"),"Turbo-jet");
        assertEquals(EngineType.get("5"),"Turbo-fan");
        assertEquals(EngineType.get("6"),"Ramjet");
        assertEquals(EngineType.get("7"),"2 Cycle");
        assertEquals(EngineType.get("8"),"4 Cycle");
        assertEquals(EngineType.get("9"),"Unknown");
        assertEquals(EngineType.get("10"),"Electric");
        assertEquals(EngineType.get("11"), "Rotary");

    }

    @DisplayName("Test Engine Type Static Data Map - Integer Test")
    @Test
    void test_Engine_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(EngineType.get(0),"None");
        assertEquals(EngineType.get(1),"Reciprocating");
        assertEquals(EngineType.get(2),"Turbo-prop");
        assertEquals(EngineType.get(3),"Turbo-shaft");
        assertEquals(EngineType.get(4),"Turbo-jet");
        assertEquals(EngineType.get(5),"Turbo-fan");
        assertEquals(EngineType.get(6),"Ramjet");
        assertEquals(EngineType.get(7),"2 Cycle");
        assertEquals(EngineType.get(8),"4 Cycle");
        assertEquals(EngineType.get(9),"Unknown");
        assertEquals(EngineType.get(10),"Electric");
        assertEquals(EngineType.get(11), "Rotary");


    }

}

