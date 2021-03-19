package com.daviddemartini.avtracker.datamodels.faa.type_certification_code;


import com.daviddemartini.avtracker.datamodels.faa.type_certification_code.TypeCertificationCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeCertificationCodeTest {

    @DisplayName("Test Type Certification Code Static Data Map - String Test")
    @Test
    void test_Engine_Static_Data_Map_String_Test() {

        // test mapping model
        assertEquals(TypeCertificationCode.get("0"),"Type Certificated");
        assertEquals(TypeCertificationCode.get("1"),"Not Type Certificated");
        assertEquals(TypeCertificationCode.get("2"),"Light Sport");
    }

    @DisplayName("Test Type Certification Code Static Data Map - Integer Test")
    @Test
    void test_Engine_Static_Data_Map_Integer_Test() {

        // test mapping model
        assertEquals(TypeCertificationCode.get(0),"Type Certificated");
        assertEquals(TypeCertificationCode.get(1),"Not Type Certificated");
        assertEquals(TypeCertificationCode.get(2),"Light Sport");
    }

}
