package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.AircraftModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AircraftModelTest {

    private static final String B7878 = "05628NI,BOEING                        ,787-8               ,5,5 ,1,1,02,260,CLASS 3,0000,               ,                                                  ,";
    private static final String B78783Q = "05629VS,BOEING                        ,787-83Q             ,5,5 ,1,1,02,260,CLASS 3,0000,               ,                                                  ,";

    @DisplayName("Test Aircraft Model B7878 Test")
    @Test
    void test_Aircraft_B7878_Test() {

        AircraftModel model1 = new AircraftModel(B7878);

        // test mapping model
        assertEquals("05628NI",model1.getFaaModelId());
        assertEquals("BOEING",model1.getManufacture());
        assertEquals("787-8",model1.getModel());
        assertEquals("Fixed wing multi engine",model1.getType());
        assertEquals("Turbo-fan",model1.getEngine());
        assertEquals("Land",model1.getCategory());

    }

    @DisplayName("Test Aircraft Model B78783Q Test")
    @Test
    void test_Aircraft_B78783Q_Test() {

        AircraftModel model1 = new AircraftModel(B78783Q);

        // test mapping model
        assertEquals("05629VS",model1.getFaaModelId());
        assertEquals("BOEING",model1.getManufacture());
        assertEquals("787-83Q",model1.getModel());
        assertEquals("Fixed wing multi engine",model1.getType());
        assertEquals("Turbo-fan",model1.getEngine());
        assertEquals("Land",model1.getCategory());

    }

}
