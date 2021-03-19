package com.daviddemartini.avtracker.datamodels.faa.engine_type;

import java.util.HashMap;
import java.util.Map;

public class EngineType {

    private static Map<String,String> engineType = new HashMap();

    // init
    static {
        // build the public static map
        engineType.put("0","None");
        engineType.put("1","Reciprocating");
        engineType.put("2","Turbo-prop");
        engineType.put("3","Turbo-shaft");
        engineType.put("4","Turbo-jet");
        engineType.put("5","Turbo-fan");
        engineType.put("6","Ramjet");
        engineType.put("7","2 Cycle");
        engineType.put("8","4 Cycle");
        engineType.put("9","Unknown");
        engineType.put("10","Electric");
        engineType.put("11","Rotary");
    }

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(engineType.containsKey(key)){
            return engineType.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(engineType.containsKey(key)){
            return engineType.get(key).toString();
        }
        return null;
    }
}

