package com.daviddemartini.avtracker.faa_datamodel.registrant_type;

import java.util.HashMap;
import java.util.Map;

public class RegistrantType {

    private static Map<String,String> registrantType = new HashMap();

    // init
    static {
        // build the public static map
	registrantType.put("1","Individual");
	registrantType.put("2","Partnership");
	registrantType.put("3","Corporation");
	registrantType.put("4","Co-Owned");
	registrantType.put("5","Government");
	registrantType.put("7","LLC");
	registrantType.put("8","Non Citizen Corporation"); 
	registrantType.put("9","Non Citizen Co-Owned");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(registrantType.containsKey(key)){
            return registrantType.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(registrantType.containsKey(key)){
            return registrantType.get(key).toString();
        }
        return null;
    }
}

