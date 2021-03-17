package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.WeightClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WeightClassTest {

    @DisplayName("Test Weight Class Static Data Map - String Test")
    @Test
    void test_Weight_Class_Data_Map_String_Test() {

        // test mapping model
        assertEquals("Up to 12,499",WeightClass.get("1"));
        assertEquals("12,500 - 19,999",WeightClass.get("2"));
        assertEquals("20,000 and over.",WeightClass.get("3"));
        assertEquals("UAV up to 55",WeightClass.get("4"));
    }

    @DisplayName("Test Weight Class Static Data Map - Integer Test")
    @Test
    void test_Weight_Class_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals("Up to 12,499",WeightClass.get(1));
        assertEquals("12,500 - 19,999",WeightClass.get(2));
        assertEquals("20,000 and over.",WeightClass.get(3));
        assertEquals("UAV up to 55",WeightClass.get(4));

    }

}
