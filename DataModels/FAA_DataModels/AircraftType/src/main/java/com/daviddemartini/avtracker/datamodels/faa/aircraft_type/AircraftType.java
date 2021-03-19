package com.daviddemartini.avtracker.datamodels.faa.aircraft_type;

import java.util.HashMap;
import java.util.Map;

public class AircraftType {

    private static Map<String,String> aircraftType = new HashMap();

    // init
    static {
        // build the public static map
        aircraftType.put("1","Glider");
        aircraftType.put("2","Balloon");
        aircraftType.put("3","Blimp/Dirigible");
        aircraftType.put("4","Fixed wing single engine");
        aircraftType.put("5","Fixed wing multi engine");
        aircraftType.put("6","Rotorcraft");
        aircraftType.put("7","Weight-shift-control");
        aircraftType.put("8","Powered Parachute");
        aircraftType.put("9","Gyroplane");
        aircraftType.put("H","Hybrid Lift");
        aircraftType.put("O", "Other");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(aircraftType.containsKey(key)){
           return aircraftType.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(aircraftType.containsKey(key)){
            return aircraftType.get(key).toString();
        }
        return null;
    }
}
