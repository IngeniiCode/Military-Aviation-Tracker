package com.daviddemartini.avtracker.services.faa.model_reference.datamodel.util;

import java.util.HashMap;
import java.util.Map;

public abstract class StaticModel {

    protected static Map<String,String> dataModel = new HashMap();

    /**
     * Generlized getter method
     *
     * @param key
     * @return
     */
    public static String get(String key){
        if(dataModel.containsKey(key)){
            return dataModel.get(key).toString();
        }
        return null;
    }
    public static String get(int intKey){
        String key = ((Integer) intKey).toString();
        if(dataModel.containsKey(key)){
            return dataModel.get(key).toString();
        }
        return null;
    }

}
