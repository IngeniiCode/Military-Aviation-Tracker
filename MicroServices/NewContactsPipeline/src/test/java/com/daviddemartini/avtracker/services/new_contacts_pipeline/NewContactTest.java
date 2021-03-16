package com.daviddemartini.avtracker.services.new_contacts_pipeline;

import com.daviddemartini.avtracker.services.new_contacts_pipeline.datamodel.NewContact;
import com.daviddemartini.avtracker.services.new_contacts_pipeline.util.StringToJson;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class NewContactTest {

    private static final String AE1E9E_MSG_1 = "MSG,1,496,256,AE1E9E,11267,2008/11/28,23:48:18.611,2008/11/28,23:53:19.161,RJA1118,,,,,,,,,,,";
    private static final String AE1E9E_MSG_2 = "MSG,2,496,603,AE1E9E,13168,2008/10/13,12:24:32.414,2008/10/13,12:28:52.074,,3576,157.6,258.3,54.05735,-4.38826,,,,,,,0";
    private static final String AE1E9E_MSG_3 = "MSG,3,496,211,AE1E9E,10057,2008/11/28,14:53:50.594,2008/11/28,14:58:51.153,,37000,,,51.45735,-1.02826,,,0,0,0,0";
    private static final String AE1E9E_MSG_4 = "MSG,4,496,469,AE1E9E,27854,2010/02/19,17:58:13.039,2010/02/19,17:58:13.368,,,288.6,103.2,,,-832,,,,,";
    private static final String AE1E9E_MSG_5 = "MSG,5,496,329,AE1E9E,27868,2010/02/19,17:58:12.644,2010/02/19,17:58:13.368,,10000,,,,,,,0,,1,0";
    private static final String AE1E9E_MSG_6 = "MSG,6,496,237,AE1E9E,27864,2010/02/19,17:58:12.846,2010/02/19,17:58:13.368,,33325,,,,,,0271,1,1,1,1";
    private static final String AE1E9E_MSG_7 = "MSG,7,496,742,AE1E9E,27929,2011/03/06,07:57:36.523,2011/03/06,07:57:37.054,,3775,,,,,,,,,,0";
    private static final String AE1E9E_MSG_8 = "MSG,8,496,194,AE1E9E,27884,2010/02/19,17:58:13.244,2010/02/19,17:58:13.368,,,,,,,,,,,,0";

    private static final String ADFF4B_MSG_1 = "MSG,1,496,256,ADFF4B,11267,2008/11/28,23:48:18.611,2008/11/28,23:53:19.161,BREW46 ,,,,,,,,,,,";
    private static final String ADFF4B_MSG_2 = "MSG,2,496,603,ADFF4B,13168,2008/10/13,12:24:32.414,2008/10/13,12:28:52.074,,3576,157.6,258.3,54.05735,-4.38826,,,,,,,0";
    private static final String ADFF4B_MSG_3 = "MSG,3,496,211,ADFF4B,10057,2008/11/28,14:53:50.594,2008/11/28,14:58:51.153,,37000,,,51.45735,-1.02826,,,0,0,0,0";
    private static final String ADFF4B_MSG_4 = "MSG,4,496,469,ADFF4B,27854,2010/02/19,17:58:13.039,2010/02/19,17:58:13.368,,,543.6,103.2,,,-832,,,,,";


    @DisplayName("Sparce Test AircraftSBS Object Init")
    @Test
    void test_sparse_AircraftSBS() throws IOException {
        System.out.println("\tSparce Test AircraftSBS Object Init");

        NewContact sparseSBS = new NewContact();
        sparseSBS.testInit();
        String jsonString = sparseSBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("TESTY05",jsonNode.get("callsign").asText());
        assertEquals("A00000",jsonNode.get("icao").asText());

    }

    @DisplayName("AE1E9E - Message 1 Parse Test ")
    @Test
    void test_AE1E9E_MSG1_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 1 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_1);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
        // message specific values
        assertEquals("RJA1118",jsonNode.get("callsign").asText());

    }

    @DisplayName("AE1E9E - Message 2 Parse Test ")
    @Test
    void test_AE1E9E_MSG2_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 2 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_2);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
    }

    @DisplayName("AE1E9E - Message 3 Parse Test ")
    @Test
    void test_AE1E9E_MSG3_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 3 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_3);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
        // message specific values
    }

    @DisplayName("AE1E9E - Message 4 Parse Test ")
    @Test
    void test_AE1E9E_MSG4_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 4 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_4);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());

    }

    @DisplayName("AE1E9E - Message 5 Parse Test ")
    @Test
    void test_AE1E9E_MSG5_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 5 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_5);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
    }

    @DisplayName("AE1E9E - Message 6 Parse Test ")
    @Test
    void test_AE1E9E_MSG6_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 6 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_6);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());

    }

    @DisplayName("AE1E9E - Message 7 Parse Test ")
    @Test
    void test_AE1E9E_MSG7_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 7 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_7);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
    }

    @DisplayName("AE1E9E - Message 8 Parse Test ")
    @Test
    void test_AE1E9E_MSG8_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message 8 Parse Test ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_8);
        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);
        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
    }

    @DisplayName("AE1E9E - Message Merge Test - Civilian")
    @Test
    void test_AE1E9E_MSG_Merge_Civilian_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message Merge Test - Civilian ");

        NewContact AE1E9E_SBS = new NewContact(AE1E9E_MSG_1);
        AE1E9E_SBS.merge(AE1E9E_MSG_2);
        AE1E9E_SBS.merge(AE1E9E_MSG_3);
        AE1E9E_SBS.merge(AE1E9E_MSG_4);
        AE1E9E_SBS.merge(AE1E9E_MSG_5);
        AE1E9E_SBS.merge(AE1E9E_MSG_6);
        AE1E9E_SBS.merge(AE1E9E_MSG_7);
        AE1E9E_SBS.merge(AE1E9E_MSG_8);

        String jsonString = AE1E9E_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        // assert!
        assertEquals("AE1E9E",jsonNode.get("icao").asText());
        // message specific values
        assertEquals("RJA1118",jsonNode.get("callsign").asText());

    }

    @DisplayName("AE1E9E - Message Merge Test - Military")
    @Test
    void test_AE1E9E_MSG_Merge_Military_AircraftSBS() throws IOException {
        System.out.println("\tAE1E9E - Message Merge Test - Military ");

        NewContact ADFF4B_SBS = new NewContact(ADFF4B_MSG_1);
        ADFF4B_SBS.merge(ADFF4B_MSG_2);
        ADFF4B_SBS.merge(ADFF4B_MSG_3);
        ADFF4B_SBS.merge(ADFF4B_MSG_4);

        String jsonString = ADFF4B_SBS.toJSON();
        JsonNode jsonNode = StringToJson.parse(jsonString);

        assertEquals("ADFF4B",jsonNode.get("icao").asText());
        // message specific values
        assertEquals("BREW46",jsonNode.get("callsign").asText());
        assertTrue(ADFF4B_SBS.milContactTrue());

    }


}
