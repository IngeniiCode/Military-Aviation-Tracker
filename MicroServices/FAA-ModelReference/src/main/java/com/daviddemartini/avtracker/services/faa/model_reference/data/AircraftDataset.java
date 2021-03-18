package com.daviddemartini.avtracker.services.faa.model_reference.data;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.AircraftModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AircraftDataset {

    private static Map<String, AircraftModel> DATASET = new HashMap();
    private static final String UNKOWN = "unknown,unknown,--,,,,,,,,,";

    /**
     * Constructor
     */
    public AircraftDataset(String inputFile){
        // check for data file, read if found, exception if not
        DATASET.put("",new AircraftModel(UNKOWN));

        try {
            File fileAPI = new File(inputFile);
            InputStream inputStream = new FileInputStream(fileAPI);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // toss off the header
            bufferedReader.readLine();
            // process remainder of file
            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                AircraftModel model = new AircraftModel(line);
                DATASET.put(model.getFaaModelId(),model);
            }

            // close
            bufferedReader.close();
            System.out.printf(" %d Model Records Read",DATASET.size());

        } catch (Exception e) {
            // Major issue.. unable to read file.
            System.err.printf("ERROR: Unexpected fatal error : %s\n",e.getMessage());
            System.exit(9);
        }
    }

    // Append to model dataset
    public void setAircraft(String modelId,AircraftModel aircraftModel){
        DATASET.put(modelId,aircraftModel);
    }

    /**
     * Get the complete AircraftModel record
     * @param modelId
     * @return
     */
    public AircraftModel getAircraft(String modelId){
        if(DATASET.containsKey(modelId)){
            return (AircraftModel) DATASET.get(modelId);
        }
        return DATASET.get("");
    }

    /**
     * Map the aircraft ID key to a manufacture string
     *
     * @param modelId
     * @return
     */
    public String getAircraftManufacture(int modelId){
        if(DATASET.containsKey(modelId)){
            return DATASET.get(modelId).getManufacture();
        }
        return null;
    }

    /**
     * Map the aircraft ID key to a model ID string
     *
     * @param modelId
     * @return
     */
    public String getAircraftModel(int modelId){
        if(DATASET.containsKey(modelId)){
            return DATASET.get(modelId).getModel();
        }
        return null;
    }

}
