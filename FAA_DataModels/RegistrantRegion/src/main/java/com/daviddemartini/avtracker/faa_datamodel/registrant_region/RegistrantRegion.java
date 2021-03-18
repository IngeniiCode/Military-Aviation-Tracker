package com.daviddemartini.avtracker.faa_datamodel.registrant_region;

import java.util.HashMap;
import java.util.Map;

public class RegistrantRegion {

    private static Map<String,String> registrantRegion = new HashMap();

    // init
    static {
        // build the public static map
        registrantRegion.put("1","Eastern");
        registrantRegion.put("2","Southwestern");
        registrantRegion.put("3","Central");
        registrantRegion.put("4","Western-Pacific");
        registrantRegion.put("5","Alaskan");
        registrantRegion.put("7","Southern");
        registrantRegion.put("8","European");
        registrantRegion.put("C","Great Lakes");
        registrantRegion.put("E","New England");
        registrantRegion.put("S","Northwest Mountain");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(registrantRegion.containsKey(key)){
            return registrantRegion.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(registrantRegion.containsKey(key)){
            return registrantRegion.get(key).toString();
        }
        return null;
    }
}

