package com.daviddemartini.avtracker.datamodels.faa.category_code;

import com.daviddemartini.avtracker.datamodels.faa.category_code.CategoryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryCodeTest {


    @DisplayName("Test Categrory Code Static Data Map - String Test")
    @Test
    void test_Engine_Static_Data_Map_String_Test() {

        // test mapping model
        assertEquals(CategoryCode.get("1"),"Land");
        assertEquals(CategoryCode.get("2"),"Sea");
        assertEquals(CategoryCode.get("3"),"Amphibian");
    }

    @DisplayName("Test Categrory Code Static Data Map - Integer Test")
    @Test
    void test_Engine_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(CategoryCode.get(1),"Land");
        assertEquals(CategoryCode.get(2),"Sea");
        assertEquals(CategoryCode.get(3),"Amphibian");
    }

}
